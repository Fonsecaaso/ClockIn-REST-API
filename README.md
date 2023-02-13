# Sistema de ponto

Para rodar o sistema é necessário ter instalado docker e docker compose, tendo instalado basta rodar o comando:

docker-compose up -d

## End Points:

### 1. Bater ponto
Registrar um horário da jornada diária de trabalho
#### URL
`POST localhost:8088/v1/batidas`

#### Payload de Exemplo
```yml
{
"dataHora":"2024-03-13 20:00:00"
}
```
### 2. Alocar horas trabalhadas
Alocar horas trabalhadas, de um dia de trabalho, em um projeto
#### URL
`POST localhost:8088/v1/alocacoes`

#### Payload de Exemplo
```yml
{
"dia":"2024-03-13",
"tempo":"8",
"nomeProjeto":"ilia"
}
```

### 3. Relatório mensal
Geração de relatório mensal de usuário
#### URL
`GET localhost:8088/v1/folhas-de-ponto/{mes}`
#### Parâmetro de Exemplo
mes: 2024-03																								