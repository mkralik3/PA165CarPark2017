# REST API [WIP]

Rest api is availabe on: http://localhost:8080/pa165/rest

##COMMANDS

#Car
Get all Cars: curl -X GET http://localhost:8080/pa165/rest/car

Find car by ID: curl -X GET http://localhost:8080/pa165/rest/car/1

Create new car: curl -X POST -i -H "Content-Type: application/json" --data ' http://localhost:8080/pa165/rest/car
data: {name: "Audi A3"}

Update car: curl -X PUT -i -H "Content-Type: application/json" --data ' http://localhost:8080/pa165/rest/car
data: {id: 1, name: "Audi A3"}

#Reservation

Create new reservation: curl -X POST -i -H "Content-Type: application/json" --data ' http://localhost:8080/pa165/rest/reservation
data: {
             "name": "reservation",
             "car" : {
             	"id": 1,
             	"name": "Audi A3"
             },
             "user" : {
             	"id": 1,
                 "userName": "Mato",
                 "type": "BRANCH_MANAGER"
             },
             "reservationStartDate" : "2017-12-17T21:25:47.277",
             "reservationEndDate" : "2016-11-17T21:25:47.277",
             "state" : "CREATED"
         }

Update reservation: curl -X PUT -i -H "Content-Type: application/json" --data ' http://localhost:8080/pa165/rest/reservation
data: {
      	"id": 5,
          "name": "reservation2",
           "car" : {
            "id": 1,
            "name": "Audi A3"
           },
           "user" : {
            "id": 1,
               "userName": "Mato",
               "type": "BRANCH_MANAGER"
           },
           "reservationStartDate" : "2017-12-17T21:25:47.277",
           "reservationEndDate" : "2016-11-17T21:25:47.277",
          "state" : "APPROVED"
      }

Find all reservation for particular branch (id is as parameter, time period is in the json): 
curl -X PUT -i -H "Content-Type: application/json" --data ' http://localhost:8080/pa165/rest/reservation/1

data: 
{
    "start" : "2018-12-17T21:25:47.277",
    "end" : "2018-11-17T21:25:47.277"
}

#Branch

Get all branches: curl -X GET http://localhost:8080/pa165/rest/branch

Find branch by ID: curl -X GET http://localhost:8080/pa165/rest/branch/1

[WIP]

#User

[WIP]