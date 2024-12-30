@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.service.impl

import com.app.dragonball.model.CharacterModel
import com.app.dragonball.repository.CharacterRepository
import com.app.dragonball.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * Implementación del servicio para la gestión de personajes.
 *
 * Esta clase proporciona la implementación de los métodos definidos en la interfaz [CharacterService]
 * para interactuar con el repositorio de personajes [CharacterRepository]. Utiliza operaciones CRUD
 * para gestionar los personajes en la base de datos.
 */
@Service
class CharacterServiceImpl
    @Autowired
    constructor(
        private val characterRepository: CharacterRepository,
    ) : CharacterService {
        /**
         * Obtiene una lista de todos los personajes.
         *
         * @return Una lista de objetos [CharacterModel] que representan todos los personajes en la base de datos.
         */
        override fun listCharacters(): List<CharacterModel> = characterRepository.findAll()

        /**
         * Obtiene un personaje por su ID.
         *
         * @param id El identificador único del personaje.
         * @return Un objeto [Optional] que contiene el [CharacterModel] si el personaje se encuentra,
         *         o está vacío si no se encuentra ningún personaje con el ID dado.
         */
        override fun getCharacterById(id: Long): Optional<CharacterModel> = characterRepository.findById(id)

        /**
         * Crea un nuevo personaje en la base de datos.
         *
         * @param newCharacter El objeto [CharacterModel] que contiene los detalles del nuevo personaje.
         * @return El [CharacterModel] creado con los detalles del personaje, incluido el ID generado.
         */
        override fun createCharacter(newCharacter: CharacterModel): CharacterModel = characterRepository.save(newCharacter)

        /**
         * Actualiza un personaje existente en la base de datos.
         *
         * @param id El identificador único del personaje a actualizar.
         * @param updatedCharacter El objeto [CharacterModel] que contiene los nuevos detalles del personaje.
         * @return Un objeto [Optional] que contiene el [CharacterModel] actualizado si el personaje se encuentra,
         *         o está vacío si no se encuentra ningún personaje con el ID dado.
         */
        override fun updateCharacter(
            id: Long,
            updatedCharacter: CharacterModel,
        ): Optional<CharacterModel> =
            characterRepository.findById(id).map { existingCharacter ->
                existingCharacter.apply {
                    name = updatedCharacter.name
                    age = updatedCharacter.age
                    desc = updatedCharacter.desc
                    image = updatedCharacter.image
                    powerLevel = updatedCharacter.powerLevel
                }
                characterRepository.save(existingCharacter)
            }

        /**
         * Elimina un personaje de la base de datos.
         *
         * @param id El identificador único del personaje a eliminar.
         * @return `true` si el personaje fue eliminado correctamente, `false` si no se encontró un personaje con el ID dado.
         */
        override fun deleteCharacter(id: Long): Boolean =
            if (characterRepository.existsById(id)) {
                characterRepository.deleteById(id)
                true
            } else {
                false
            }
    }
