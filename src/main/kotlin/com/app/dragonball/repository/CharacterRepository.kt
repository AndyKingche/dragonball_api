package com.app.dragonball.repository

import com.app.dragonball.model.CharacterModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository : JpaRepository<CharacterModel, Long>
