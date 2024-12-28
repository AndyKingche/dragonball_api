@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.service.impl

import com.app.dragonball.model.CharacterModel
import com.app.dragonball.repository.CharacterRepository
import com.app.dragonball.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterServiceImpl
    @Autowired
    constructor(
        private val characterRepository: CharacterRepository,
    ) : CharacterService {
        override fun listCharacters(): List<CharacterModel> = characterRepository.findAll()

        override fun getCharacterById(id: Long): Optional<CharacterModel> = characterRepository.findById(id)

        override fun createCharacter(newCharacter: CharacterModel): CharacterModel = characterRepository.save(newCharacter)

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

        override fun deleteCharacter(id: Long): Boolean =
            if (characterRepository.existsById(id)) {
                characterRepository.deleteById(id)
                true
            } else {
                false
            }
    }
