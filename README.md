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
"dataHora":"11-02-2023 20:00:00"
}
```
### 2. Alocar horas trabalhadas
Alocar horas trabalhadas, de um dia de trabalho, em um projeto
#### URL
`POST localhost:8088/v1/alocacoes`

#### Payload de Exemplo
```yml
{
"dia":"12-02-2023",
"tempo":"8",
"nomeProjeto":"ilia"
}
```

### 3. Relatório mensal
Geração de relatório mensal de usuário
#### URL
`GET localhost:8080/v1/folhas-de-ponto/{mes}`
#### Parâmetro de Exemplo
mes: 02-2023																								