@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "character")
@NamedQuery(name = "CharacterModel", query = "SELECT c FROM CharacterModel c")
class CharacterModel : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    var id: Long? = null

    @Column(name = "character_name")
    var name: String? = null

    @Column(name = "character_age")
    var age: Int? = null

    @Column(name = "character_desc")
    var desc: String? = null

    @Column(name = "character_image")
    var image: String? = null

    @Column(name = "character_powerlevel")
    var powerLevel: Int? = null
}
