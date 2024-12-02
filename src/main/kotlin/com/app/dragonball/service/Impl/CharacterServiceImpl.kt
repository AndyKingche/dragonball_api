package com.app.dragonball.service.Impl

import com.app.dragonball.model.CharacterModel
import com.app.dragonball.repository.CharacterRepository
import com.app.dragonball.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CharacterServiceImpl @Autowired constructor (private val characterRepository: CharacterRepository) : CharacterService {

    override fun listCharacters(): List<CharacterModel> {
        return characterRepository.findAll()
    }

    override fun getCharacterById(id: Long): Optional<CharacterModel> {
       return characterRepository.findById(id)
    }

    override fun createCharacter(newCharacter: CharacterModel): CharacterModel {
        return characterRepository.save(newCharacter)
    }

    override fun updateCharacter(id: Long, updatedCharacter: CharacterModel): Optional<CharacterModel> {
        return characterRepository.findById(id).map { existingCharacter ->
            existingCharacter.apply {
                name = updatedCharacter.name
                age = updatedCharacter.age
                desc = updatedCharacter.desc
                image = updatedCharacter.image
                powerLevel = updatedCharacter.powerLevel
            }
            characterRepository.save(existingCharacter)
        }
    }

    override fun deleteCharacter(id: Long): Boolean {
        return if (characterRepository.existsById(id)) {
            characterRepository.deleteById(id)
            true
        } else {
            false
        }
    }


}