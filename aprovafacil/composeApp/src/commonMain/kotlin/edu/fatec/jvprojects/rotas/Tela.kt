package edu.fatec.jvprojects.rotas

sealed class Tela(val rota: String, val showBottomBar: Boolean = true) {
    object Home : Tela("home", false)
    object Formulario : Tela("formulario")
    object Consulta : Tela("consulta")
    object Detalhes : Tela("detalhes", false)
    object Editar : Tela("editar")
    object EditarDocumentos : Tela("editar_documentos")
    object Excluir : Tela("excluir")
    object Documentos : Tela("documentos")
    object Interno : Tela("interno", false)
    object Login : Tela("login", false)
    object Dashboard : Tela("dashboard", false)
    object Cliente : Tela("cliente", false)
    object Documento : Tela("documento/{link}", false)
    object Devolutiva : Tela("devolutiva", false)

    companion object {
        fun fromRoute(rota: String?): Tela? = when (rota) {
            Home.rota -> Home
            Formulario.rota -> Formulario
            Consulta.rota -> Consulta
            Detalhes.rota -> Detalhes
            Editar.rota -> Editar
            Documentos.rota -> Documentos
            EditarDocumentos.rota -> EditarDocumentos
            Excluir.rota -> Excluir
            Login.rota -> Login
            Dashboard.rota -> Dashboard
            Cliente.rota -> Cliente
            Documento.rota -> Documento
            Devolutiva.rota -> Devolutiva
            else -> null
        }
    }
}
