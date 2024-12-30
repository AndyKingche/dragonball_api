package com.app.dragonball.service

import com.app.dragonball.model.CharacterModel
import java.util.Optional

/**
 * Interfaz que define los servicios relacionados con la gestión de personajes.
 *
 * Esta interfaz proporciona métodos para obtener, crear, actualizar y eliminar personajes
 * desde la base de datos. Se utiliza en el servicio que maneja las operaciones CRUD de los personajes.
 */
interface CharacterService {
    /**
     * Obtiene una lista de todos los personajes.
     *
     * @return Una lista de objetos [CharacterModel] que representan todos los personajes en la base de datos.
     */
    fun listCharacters(): List<CharacterModel>

    /**
     * Obtiene un personaje por su ID.
     *
     * @param id El identificador único del personaje.
     * @return Un objeto [Optional] que contiene el [CharacterModel] si el personaje se encuentra,
     *         o está vacío si no se encuentra ningún personaje con el ID dado.
     */
    fun getCharacterById(id: Long): Optional<CharacterModel>

    /**
     * Crea un nuevo personaje en la base de datos.
     *
     * @param newCharacter El objeto [CharacterModel] que contiene los detalles del nuevo personaje.
     * @return El [CharacterModel] creado con los detalles del personaje, incluido el ID generado.
     */
    fun createCharacter(newCharacter: CharacterModel): CharacterModel

    /**
     * Actualiza un personaje existente en la base de datos.
     *
     * @param id El identificador único del personaje a actualizar.
     * @param updatedCharacter El objeto [CharacterModel] que contiene los nuevos detalles del personaje.
     * @return Un objeto [Optional] que contiene el [CharacterModel] actualizado si el personaje se encuentra,
     *         o está vacío si no se encuentra ningún personaje con el ID dado.
     */
    fun updateCharacter(
        id: Long,
        updatedCharacter: CharacterModel,
    ): Optional<CharacterModel>

    /**
     * Elimina un personaje de la base de datos.
     *
     * @param id El identificador único del personaje a eliminar.
     * @return `true` si el personaje fue eliminado correctamente, `false` si no se encontró un personaje con el ID dado.
     */
    fun deleteCharacter(id: Long): Boolean
}
