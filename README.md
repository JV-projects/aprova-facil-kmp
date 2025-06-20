# Aprova Fácil (frontend)

![badge-android](http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat)
![badge-desktop](http://img.shields.io/badge/platform-desktop-DB413D.svg?style=flat)
![badge-web](https://img.shields.io/badge/platform-web-59B6EC.svg?style=flat)


O Aprova Fácil é uma aplicação desenvolvida para simplificar o envio de dados do cliente à imobiliária para análise de crédito. Seu objetivo é centralizar as informações em uma única plataforma, oferecer uma visualização organizada para o administrador, exibir o status de atendimento dos clientes e facilitar que o corretor registre a devolutiva do atendimento.

Aprova Fácil API (backend) <a href="https://github.com/JV-projects/aprova-facil-api" target="_blank">Link do repositório backend</a>

#### Nosso Cliente

Nosso cliente é a <a href="https://lastrearimoveis.com.br/" target="_blank">Lastrear Imobiliária</a>, uma empresa especializada na venda e locação de imóveis. A equipe nos apresentou a necessidade de uma solução para centralizar e organizar as informações dos clientes, relatando dificuldades no gerenciamento dos dados antes de enviá-los para a análise de crédito e atendimento com corretor.

#### Requisitos Funcionais do Sistema

| Módulo                 | ID   | Nome                                                         | Descrição                                                                                                                                               |
|------------------------|------|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Cliente                | RF01 | Preencher formulário                                         | Fornecer um formulário para que o cliente possa preencher seus dados e enviá-los para análise de crédito imobiliário.                                  |
| Cliente                | RF02 | Upload de documentos                                         | O usuário consegue fazer upload dos seus documentos no formulário                                                                                       |
| Cliente                | RF03 | Consultar dados para edição                                  | O cliente consegue consultar seus dados por meio do CPF e email para fazer alterações                                                                   |
| Cliente                | RF04 | Exclusão de dados                                            | O cliente consegue excluir seus dados do sistema                                                                                                        |
| Cliente                | RF05 | Vincular outro participante                                  | Se o cliente desejar, ele consegue vincular um outro participante no seu atendimento informando um CPF. Este participante deve estar previamente cadastrado no sistema. |
| Administrador          | RF06 | Visualizar cliente                                           | O administrador deve conseguir visualizar as informações do cliente.                                                                                     |
| Administrador          | RF07 | Visualizar clientes pendentes                                | Em um dashboard, o sistema deve exibir os clientes pendentes de atendimento, que sua análise de crédito ainda não foi feita                             |
| Administrador          | RF08 | Visualizar clientes aguardando retorno da análise de crédito | Em um dashboard, o sistema deve exibir os cliente que aguardam um retorno da análise de crédito                                                         |
| Administrador          | RF09 | Descrever o retorno da análise de crédito                    | No item de cada cliente listado, deve ser possível inserir o retorno da análise de crédito, para que posteriormente, o corretor consiga acessar essa informação. Note que essa ação 'promove' o cliente para 'pendente de atendimento'. |
| Administrador          | RF10 | Visualizar clientes pendentes de distribuição                | Em um dashboard, o sistema deve exibir os clientes que estão pendentes de serem distribuídos para o atendimento com um corretor.                        |
| Administrador          | RF11 | Distribuir clientes para os corretores                       | Deve ser possível enviar um email pro corretor, informando o cliente destinado a ele bem como o código para realizar a devolutiva.                      |
| Administrador          | RF12 | Visualizar clientes em atendimento                           | Em um dashboard, o sistema deve exibir os clientes que estão em atendimento.                                                                            |
| Administrador          | RF13 | Visualizar clientes com atendimento concluído                | Em um dashboard, o sistema deve exibir os clientes que já foram atendidos, bem como sua devolutiva fornecida pelo corretor.                            |
| Corretor               | RF14 | Realizar devolutiva                                          | O corretor consegue realizar a devolutiva do cliente com o código recebido por email. Ao realizar a devolutiva, o atendimento daquele cliente é concluído. |
| Autenticação | RF15 | Autenticação com username e senha.                           | O administrador consegue se autenticar na plataforma para gerenciar os clientes                                                                         |
### Tecnologias 

- Kotlin Multiplatform
- Compose Multplatform
- Ktor
- Kotlin
- JWT token
- Spring
  - Spring Boot
  - Spring Web
  - Spring Security
  - Spring JPA
  - JavaMail Sender
  - Validation
  - Thymeleaf
- PostgreSQL

### Interfaces

<details>
  <summary>Desktop</summary>
  
### Módulo Cliente

---

#### Menu de operações do cliente

![image](https://github.com/user-attachments/assets/bb6863b7-e54e-4988-beb3-5c55291bd5e3)

#### Formulário de envio de análise de crédito

![image](https://github.com/user-attachments/assets/c906f5a0-cc49-42fd-9c09-0abf7edce9e2)

#### Envio de documentos

![image](https://github.com/user-attachments/assets/e16f0e09-0e11-4056-bb54-9f89fc4ff14e)

#### Consultando dados

![image](https://github.com/user-attachments/assets/27f1ac7c-282d-41f6-8a98-3dfb3f4b5bbb)

#### Detalhes do cliente

![image](https://github.com/user-attachments/assets/b4109a14-87a6-46b7-8784-65fe22f7f8c2)

### Módulo Administrador

---

#### Tela de acesso interno

![image](https://github.com/user-attachments/assets/714c47ac-2e83-4c8c-8540-c91b5d314dc2)

#### Login do administrador

![image](https://github.com/user-attachments/assets/37256c42-70da-4e2d-bda8-b6095002eb2d)

#### Dashboard do administrador

![image](https://github.com/user-attachments/assets/bcf61a2f-ca62-4077-b4fd-0fc6aba8453d)

#### Detalhes e registro de atendimento

![image](https://github.com/user-attachments/assets/37034da2-722e-4e40-8786-54ea7781df56)

### Módulo Corretor

---

#### Inserção de código da devolutiva

![image](https://github.com/user-attachments/assets/b351b467-0bc8-44fe-9b66-a4de0ab16f5e)

#### Inserindo devolutiva de atendimento

![image](https://github.com/user-attachments/assets/ff23351d-d6ef-4afd-9c3a-3e073f587eed)

---

</details>

<details>
  <summary>Web</summary>
  
### Módulo Cliente

---

#### Menu de operações do cliente

![image](https://github.com/user-attachments/assets/6df3b52c-3859-4d17-ac4e-79a9ef016ca6)

#### Formulário de envio de análise de crédito

![image](https://github.com/user-attachments/assets/fb9d5fe1-088d-4657-abf9-430c7e086321)

#### Envio de documentos

![image](https://github.com/user-attachments/assets/f38bc469-78c0-4dfc-a72c-fcbc17389d79)

#### Consultando dados

![image](https://github.com/user-attachments/assets/de365a0e-9409-4809-a1db-42b9f556e8d9)

#### Detalhes do cliente

![image](https://github.com/user-attachments/assets/d5a01814-5266-4cde-a2b2-cec6cc947725)

### Módulo Administrador

---

#### Tela de acesso interno

![image](https://github.com/user-attachments/assets/28d8d529-72ae-4f84-849f-feadcc3a6e8d)

#### Login do administrador

![image](https://github.com/user-attachments/assets/9a4c2153-975f-40dd-ad6f-2a45395b1d93)

#### Dashboard do administrador

![image](https://github.com/user-attachments/assets/3123398e-e315-4405-ac73-fe2244bf874c)

#### Detalhes e registro de atendimento

![image](https://github.com/user-attachments/assets/7bd2aac9-6965-4627-a583-da5cabbcf5ff)

### Módulo Corretor

---

#### Inserção de código da devolutiva

![image](https://github.com/user-attachments/assets/f93233a5-63dc-4390-b2ee-8e4d05fe4120)

#### Inserindo devolutiva de atendimento

![image](https://github.com/user-attachments/assets/3e02ce2c-2cdc-43da-ad87-bfc5499680f1)

</details>

<details>
  <summary>Android</summary>
  
### Módulo Cliente

---

#### Menu de operações do cliente

<img src="https://github.com/user-attachments/assets/4da898dc-c026-4c2f-9790-bb81b7f71219" width="40%" >

#### Formulário de envio de análise de crédito

<img src="https://github.com/user-attachments/assets/7ce0a76b-5914-468f-93e5-8d4abcce0a4c" width="40%" >

#### Envio de documentos

<img src="https://github.com/user-attachments/assets/bd0f3e22-3661-4c24-a16b-97641fa4fc48" width="40%" >

#### Consultando dados

<img src="https://github.com/user-attachments/assets/436ef0e3-c99d-4b78-ac78-351a09d41161" width="40%" >

#### Detalhes do cliente

<img src="https://github.com/user-attachments/assets/076e5018-25d1-4375-a4b4-8f78c70b5a2e" width="40%" >

### Módulo Administrador

---

#### Tela de acesso interno

<img src="https://github.com/user-attachments/assets/df294773-35c1-4f61-966d-383e745465f7" width="40%" >

#### Login do administrador

<img src="https://github.com/user-attachments/assets/8dd281a1-1a6c-4fda-af76-b56bbbbe10dd" width="40%" >

#### Dashboard do administrador

<img src="https://github.com/user-attachments/assets/57fe6a59-217b-4447-a4cb-a80deab74d2d" width="40%" >

#### Detalhes e registro de atendimento

<img src="https://github.com/user-attachments/assets/15fc2f44-4548-4a4d-aa5c-26f129fa054e" width="40%" >

### Módulo Corretor

---

#### Inserção de código da devolutiva

<img src="https://github.com/user-attachments/assets/6f1428f3-6060-4f12-806d-2b92f5b9c1dd" width="40%" >

#### Inserindo devolutiva de atendimento

<img src="https://github.com/user-attachments/assets/b93a2af1-d4e8-46ff-a53b-8a51aa2285b1" width="40%" >

</details>
