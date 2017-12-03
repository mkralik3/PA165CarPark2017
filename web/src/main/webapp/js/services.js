var carServices = angular.module('carServices', []);
var urlBase = "http://localhost:8080/pa165/rest";

carServices.factory('carFactory', ['$http',
    function($http){
        var urlCar = urlBase.concat("/car");

        var dataFactory={};

        dataFactory.getAllCars =  function(success, error) {
            return $http.get(urlCar).then(success, error);
        };
        return dataFactory;
    }
]);