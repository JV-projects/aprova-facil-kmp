package edu.fatec.jvprojects.compositionLocal

import androidx.compose.runtime.staticCompositionLocalOf

interface ITokenService {
    fun saveToken(token: String)
    fun getToken() : String
}


class TokenService : ITokenService {

    private var token: String = ""

    init {
        println("TokenService inicializado")
    }

    override fun saveToken(token: String) {
        this.token = token
        println("TokenService -> Token salvo $token")
    }

    override fun getToken(): String {
        return token
    }

}

val LocalTokenService = staticCompositionLocalOf<ITokenService> {
    error("Nenhum serviço providenciado para TokenService")
}