package edu.fatec.jvprojects.model.enums

enum class StatusCliente(val valor: String, val textoExibicao: String) {
    PENDENTE("PENDENTE", "Pendente"),
    AGUARDANDO_ANALISE("AGUARDANDO_ANALISE", "Aguardando Análise"),
    PENDENTE_ATENDIMENTO("PENDENTE_ATENDIMENTO", "Pendente de atendimento"),
    EM_ATENDIMENTO("EM_ATENDIMENTO", "Em Atendimento"),
    ATENDIMENTO_CONCLUIDO("ATENDIMENTO_CONCLUIDO", "Atendimento Concluído");

    companion object {
        fun fromString(statusString: String): StatusCliente? {
            return entries.find { it.valor == statusString }
        }
    }
}