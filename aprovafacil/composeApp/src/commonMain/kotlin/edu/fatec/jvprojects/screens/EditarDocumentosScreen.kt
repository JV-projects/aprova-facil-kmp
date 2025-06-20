package edu.fatec.jvprojects.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.fatec.jvprojects.viewModel.ClienteViewModel
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name

@Composable
fun EditarDocumentosScreen(
    clienteViewModel: ClienteViewModel
) {
    val uiState = clienteViewModel.uiState.collectAsState()


    val rgLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onRgChange(file)
    }
    val cpfLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onCpfDocumentChanged(file)
    }
    val residenciaLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onComprovanteResidenciaChange(file)
    }
    val pisCartaoLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onPisCartaoCidadaoChange(file)
    }
    val certidaoLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onCertidaoChange(file)
    }
    val holeriteLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onHoleriteChange(file)
    }
    val extratoLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onExtratoFgtsChange(file)
    }
    val irLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onDeclaracaoIrChange(file)
    }
    val carteiraLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(extension = "pdf")
    ) { file ->
        clienteViewModel.onCarteiraTrabalhoChange(file)
    }

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8F)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Faça upload dos documentos necessários em formato PDF")
                Text("O tamanho de cada PDF não pode ultrapassar 5MB")

                Column {
                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("RG")
                                Text(uiState.value.docRg?.name ?: uiState.value.pathRg.toString())
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { rgLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)
                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("CPF")
                                Text(uiState.value.docCpf?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { cpfLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Comprovante de Residência")
                                Text(uiState.value.comprovanteResidencia?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { residenciaLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("PIS ou Cartão do Cidadão")
                                Text(uiState.value.pisCartaoCidadao?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { pisCartaoLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Certidão de Nascimento/Casamento")
                                Text(uiState.value.certidao?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { certidaoLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Holerite (últimos 3 meses)")
                                Text(uiState.value.holerite?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { holeriteLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Extrato FGTS")
                                Text(uiState.value.extratoFgts?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { extratoLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Declaração de Imposto de Renda")
                                Text(uiState.value.declaracaoIr?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { irLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)

                    ListItem(
                        headlineContent = {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Carteira de Trabalho")
                                Text(uiState.value.carteiraTrabalho?.name ?: "")
                            }
                        },
                        trailingContent = {
                            Button(
                                onClick = { carteiraLauncher.launch() }
                            ) {
                                Text("Escolher arquivo")
                            }
                        }
                    )
                    HorizontalDivider(thickness = 2.dp)
                }

            }
        }
    }

}