package com.app.dragonball.service

import com.app.dragonball.model.CharacterModel
import java.util.Optional

interface CharacterService {
    fun listCharacters(): List<CharacterModel>

    fun getCharacterById(id: Long): Optional<CharacterModel>

    fun createCharacter(newCharacter: CharacterModel): CharacterModel

    fun updateCharacter(
        id: Long,
        updatedCharacter: CharacterModel,
    ): Optional<CharacterModel>

    fun deleteCharacter(id: Long): Boolean
}
