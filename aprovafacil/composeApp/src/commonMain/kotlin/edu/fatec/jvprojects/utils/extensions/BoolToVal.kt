package edu.fatec.jvprojects.utils.extensions

fun Boolean.boolToVal() : String {
    return if (this) {
        "Sim"
    } else {
        "Não"
    }
}