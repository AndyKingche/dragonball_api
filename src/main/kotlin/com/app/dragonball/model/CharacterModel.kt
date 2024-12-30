@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.app.dragonball.model

import jakarta.persistence.*
import java.io.Serializable

/**
 * Representa un personaje en la base de datos.
 *
 * Esta clase se utiliza para mapear los datos de un personaje de la tabla `character` en la base de datos.
 * Contiene información sobre el nombre, edad, descripción, imagen y nivel de poder de un personaje.
 *
 * @property id El identificador único del personaje (clave primaria). Se genera automáticamente.
 * @property name El nombre del personaje.
 * @property age La edad del personaje.
 * @property desc Una breve descripción del personaje.
 * @property image La URL de la imagen del personaje.
 * @property powerLevel El nivel de poder del personaje.
 */
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
