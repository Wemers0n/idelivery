
# iDelivery


## Tecnologias Utilizadas

[![My Skills](https://skillicons.dev/icons?i=java,spring)](https://skillicons.dev)

## Rotas

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
	"distanceFee": 0.00,
	"courierPayout": 0.00,
	"totalCost": 0.00,
	"totalItems": 0.00,
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
`POST localhost:9999/api/v1/deliveries/UUID/pickUp`
``` json
{
    "courierId": "981d0657-deed-4ed3-8440-8a40f4b6bd34"
}
```
#### Retorno
`200 OK`

### Rota POST: Registra a finalização de uma entrega

`POST localhost:9999/api/v1/deliveries/UUID/completion`

#### Retorno
`200 OK`