package com.app.dragonball.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "character")
@NamedQuery(name = "CharacterModel", query = "SELECT c FROM CharacterModel c")
class CharacterModel : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chracter_id")
    var id: Long? = 0;

    @Column(name = "character_name")
    var name: String? = ""

    @Column(name = "character_age")
    var age: Int? = 0;

    @Column(name = "character_desc")
    var desc: String? = "";

    @Column(name = "character_image")
    var image: String? = ""

    @Column(name = "character_powerlevel")
    var powerLevel: Int? = 0

}