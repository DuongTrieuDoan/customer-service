# customer-service

## - RUN THE SERVICES WITH DOCKER SETUP - ##
Create docker network bridge:
    docker network create -d bridge my_bridge
Run rabbitmq:
    docker run -it --rm -d --net=my_bridge --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
customerservice:
    docket build -t target/customer-service-0.0.1.jar .
    docker run -d --net=my_bridge --name customerservice -p 9090:9090 target/customer-service-0.0.1.jar
Run orderservice:
    docker build -t target/order-service-0.0.1.jar .
    docker run -d --net=my_bridge -p 9191:9191 --name orderservice target/order-service-0.0.1.jar
#----------------------#
 
## - RUN THE SERVICE ON LOCAL MACHINE WITH RABBITMQ DOCKER ONLY - ## 
change all hosts to "localhost"
RabbitMQ docker command: docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
java -jar -Dserver.port=9090 /Users/tranthituongvi/.m2/repository/com/example/microservice/customer-service/0.0.1/customer-service-0.0.1.jar
java -jar -Dserver.port=9191 /Users/tranthituongvi/.m2/repository/com/example/microservice/order-service/0.0.1/order-service-0.0.1.jar

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