package com.app.dragonball.controller

import com.app.dragonball.DTO.MessagesDTO
import com.app.dragonball.model.CharacterModel
import com.app.dragonball.service.CharacterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/character/")
@CrossOrigin(origins = ["*"])
class CharacterController @Autowired constructor (
    private val characterService: CharacterService,
) {

    @GetMapping("list")
    fun getAllListCharacters(): ResponseEntity<List<CharacterModel>> {
        return try {
            ResponseEntity.ok(characterService.listCharacters())
        }catch (e: Exception){

            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @GetMapping("{id}")
    fun getCharacterId(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(characterService.getCharacterById(id))
        }catch (e: Exception){
            val badMessage = MessagesDTO().apply {
                message = e.message
                method = "GET"
                status = false
            }
            ResponseEntity(badMessage,HttpStatus.INTERNAL_SERVER_ERROR)

        }
    }

    @PostMapping("create-character")
    fun createCharacter(@RequestBody character: CharacterModel): ResponseEntity<Any> {
        return try {
            val createdCharacter = characterService.createCharacter(character)

            val message = MessagesDTO().apply {
                message = "Person created with ID ${createdCharacter.id}"
                method = "POST"
                status = true
            }
            ResponseEntity(message,HttpStatus.CREATED)
        }catch (e: Exception){
            val badMessage = MessagesDTO().apply {
                message = "Error: ${e.message}"
                method = "POST"
                status = false
            }
            ResponseEntity(badMessage, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("update-character/{id}")
    fun updateCharacter(@PathVariable("id") id: Long, @RequestBody character: CharacterModel): ResponseEntity<Any> {
        return try {
                val updatedCharacter = characterService.updateCharacter(id, character)

                val message = MessagesDTO().apply {
                    message = "Person updated with ID ${updatedCharacter.get().id} "
                    method = "PUT"
                    status = true
                }

                ResponseEntity(message, HttpStatus.OK)

        }catch (e: IllegalArgumentException){

            val badMessage = MessagesDTO().apply {
                message = "Invalid input: ${e.message}"
                method = "PUT"
                status = false
            }
            ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
        }
        catch (e: Exception){

            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete-character/{id}")
    fun deleteCharacter(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            val deleteCharacter = characterService.deleteCharacter(id)

            if(deleteCharacter){
                val message = MessagesDTO().apply {
                    message = "Person deleted with ID ${id} "
                    method = "DELETE"
                    status = true
                }

                ResponseEntity(message, HttpStatus.OK)
            }else{
                val badMessage = MessagesDTO().apply {
                    message = "Person didnt delete with ID ${id} , because the person does not exist."
                    method = "DELETE"
                    status = false
                }
                ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
            }


        }catch (e: IllegalArgumentException){

            val badMessage = MessagesDTO().apply {
                message = "Invalid input: ${e.message}"
                method = "DELETE"
                status = false
            }
            ResponseEntity(badMessage, HttpStatus.BAD_REQUEST)
        }
        catch (e: Exception){

            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}