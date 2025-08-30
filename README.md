
# iDelivery

O projeto iDelivery tem como objetivo aplicar e aprofundar os conceitos e práticas do Domain-Driven Design (DDD) em um cenário realista de sistema de entregas. A iniciativa busca estruturar um modelo de domínio rico e alinhado às necessidades do negócio, utilizando boas práticas de design de software.

Iniciei este projeto pensando em um ambiente de testes e aprendizado, no qual novas funcionalidades, padrões arquiteturais e estratégias de modelagem serão incorporados gradualmente, acompanhando a evolução do sistema.

## Tecnologias Utilizadas

[![My Skills](https://skillicons.dev/icons?i=java,spring,postgres,kafka)](https://skillicons.dev)

### Como Executar
Utilize o comando: `docker-compose up -d` para iniciar os conteiners

--- 

### Rotas

### Rota POST: Cria um rascunho de uma nova encomenda
`POST localhost:9999/api/v1/deliveries`

#### Exemplo de Payload:
```json
{
  "sender": {
    "zipCode": "01001-000",
    "street": "Rua da Consolação",
    "number": "123",
    "name": "João Silva",
    "phone": "11999998888",
    "complement": "Apto 10"
  },
  "recipient": {
    "zipCode": "01001-000",
    "street": "Rua da Consolação",
    "number": "123",
    "name": "João Silva",
    "phone": "11999998888",
    "complement": "Apto 10"
  },
  "items": [
    {
      "name": "Livro de Java",
      "quantity": 2
    }
  ]
}
```
#### Retorno

```json
{
	"id": "UUID",
	"courierId": "UUID",
	"status": "String",
	"placedAt": "0000-00-00T00:00:00.0000000-00:00",
	"assingnedAt": null,
	"expectedDeliveryAt": "0000-00-00T00:00:00.0000000-00:00",
	"fulfilledAt": null,
	"distanceFee": BigDecimal,
	"courierPayout": BigDecimal,
	"totalCost": BigDecimal,
	"totalItems": BigDecimal,
	"sender": {
		"zipCode": "01001-000",
		"street": "Rua da Consolação",
		"number": "123",
		"complement": "Apto 10",
		"name": "João Silva",
		"phone": "11999998888"
	},
	"recipient": {
		"zipCode": "01001-000",
		"street": "Rua da Consolação",
		"number": "123",
		"complement": "Apto 10",
		"name": "João Silva",
		"phone": "11999998888"
	},
	"items": [
		{
			"id": "UUID",
			"name": "Livro de Java",
			"quantity": 2
		}
	]
}
```
### Rota POST: Submete uma encomenda para processamento
`POST localhost:9999/api/v1/deliveries/UUID/placement`

#### Retorno
`200 OK`

### Rota POST: Registra a retirada de uma encomenda pelo entregador
`POST localhost:9999/api/v1/deliveries/UUID-Delivery/pickUp`
``` json
{
    "courierId": "UUID-Courier"
}
```
#### Retorno
`200 OK`

### Rota POST: Registra a finalização de uma entrega

`POST localhost:9999/api/v1/deliveries/UUID/completion`

#### Retorno
`200 OK`

### Rota POST: Cria um novo entregador.
`POST localhost:9999/api/v1/couriers`
``` json
{
    "courierId": "UUID-Courier"
}
```
#### Retorno

```json
{
	"id": "00eae915-8566-4575-9f08-6ab820d28945",
	"name": "Maria Entregadora",
	"phone": "11987654321",
	"fulfilledDeliveriesQuantity": 0,
	"pendingDeliveriesQuantity": 0,
	"lastFulfilledDeliveryAt": null,
	"pendingDeliveries": []
}
```

### Rota GET: Lista todos os entregadores com paginação.
`GET localhost:9999/public/couriers`
``` json
{
	"content": [
		{
			"id": "00eae915-8566-4575-9f08-6ab820d28945",
			"name": "Maria Entregadora",
			"fulfilledDeliveriesQuantity": 0,
			"lastFulfilledDeliveryAt": null
		}
	],
	"page": {
		"size": 10,
		"number": 0,
		"totalElements": 2,
		"totalPages": 1
	}
}
```

### Rota GET: Lista todas as encomendas com paginação.
`GET localhost:9999/api/v1/deliveries`
``` json
{
	"content": [
		{
			"id": "77158f51-0345-4ad6-af54-af1d57cd5726",
			"courierId": null,
			"status": "DRAFT",
			"placedAt": null,
			"assingnedAt": null,
			"expectedDeliveryAt": "2025-08-28T20:53:06.617371Z",
			"fulfilledAt": null,
			"distanceFee": 158.10,
			"courierPayout": 31.00,
			"totalCost": 189.10,
			"totalItems": 2,
			"sender": {
				"zipCode": "01001-000",
				"street": "Rua da Consolação",
				"number": "123",
				"complement": "Apto 10",
				"name": "João Silva",
				"phone": "11999998888"
			},
			"recipient": {
				"zipCode": "01001-000",
				"street": "Rua da Consolação",
				"number": "123",
				"complement": "Apto 10",
				"name": "João Silva",
				"phone": "11999998888"
			},
			"items": [
				{
					"id": "371efe80-c8c0-4d6f-86da-79adfe081905",
					"name": "Livro de Java",
					"quantity": 2
				}
			]
		},
		{
			"id": "daf858e5-8331-46da-b09a-64403e1ccc47",
			"courierId": "00eae915-8566-4575-9f08-6ab820d28945",
			"status": "DELIVERED",
			"placedAt": "2025-08-28T17:53:35.372974Z",
			"assingnedAt": "2025-08-28T17:54:38.50813Z",
			"expectedDeliveryAt": "2025-08-28T20:53:10.969109Z",
			"fulfilledAt": "2025-08-28T17:54:49.830517Z",
			"distanceFee": 158.10,
			"courierPayout": 31.00,
			"totalCost": 189.10,
			"totalItems": 2,
			"sender": {
				"zipCode": "01001-000",
				"street": "Rua da Consolação",
				"number": "123",
				"complement": "Apto 10",
				"name": "João Silva",
				"phone": "11999998888"
			},
			"recipient": {
				"zipCode": "01001-000",
				"street": "Rua da Consolação",
				"number": "123",
				"complement": "Apto 10",
				"name": "João Silva",
				"phone": "11999998888"
			},
			"items": [
				{
					"id": "516534c7-fe32-4141-b184-252b08c44377",
					"name": "Livro de Java",
					"quantity": 2
				}
			]
		}
	],
	"page": {
		"size": 10,
		"number": 0,
		"totalElements": 2,
		"totalPages": 1
	}
}
```