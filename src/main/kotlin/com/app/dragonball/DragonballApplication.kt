package com.app.dragonball

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DragonballApplication

fun main(args: Array<String>) {
	runApplication<DragonballApplication>(*args)
}
