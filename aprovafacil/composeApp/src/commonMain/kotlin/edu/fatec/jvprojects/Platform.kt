package edu.fatec.jvprojects

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform