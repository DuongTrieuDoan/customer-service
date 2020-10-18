# customer-service
RabbitMQ docker command: docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

path: http://localhost:9090/customers
    GET: 
        response body:
            [
                {
                    "id": "2edf5044-deae-4b5a-be57-2d47a625b924",
                    "name": "1ad21244-4241-486f-bbec-bf7e62542c6d"
                },
                {
                    "id": "ddda7e0d-7366-49c5-ab2d-8c7793100724",
                    "name": "f9d2a078-3276-49ca-aad9-cb8b830af745"
                },
                {
                    "id": "e2bda1b9-3ed6-48f0-9cea-e5a451541fe0",
                    "name": "d09dd3d0-e33e-40b0-8177-be3c7518f4fa"
                },
                {
                    "id": "be5c60b4-a8a2-40f1-84aa-053a6cd84ed2",
                    "name": "4d63bd04-d951-4123-af35-46fb9f17ce46"
                }
            ]
    POST:
        request body:
            {
            	"name": "test 2"
            }
    PUT:
        request body:
            {
                "id": "d820d51e-a700-4b8f-82a9-838a2a12aa99",
            	"name": "test 2"
            }
        response body:
            {
                "id": "be5c60b4-a8a2-40f1-84aa-053a6cd84ed2",
                "name": "4d63bd04-d951-4123-af35-46fb9f17ce46"
            }