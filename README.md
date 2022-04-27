# Getting Started

RESTful API with an endpoint for transaction commission calculation. 

## How to run locally the application

Please build application with command:
````
mvn clean install
````

and then in order to run the application please run following command:
```
java -jar transaction_commission-0.0.1-SNAPSHOT.jar   
```

###Available resources

```aidl
curl --location --request POST 'localhost:8080/v1/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2022-04-25",
    "amount": "400",
    "currency": "EUR",
    "client_id": 42
}'
```