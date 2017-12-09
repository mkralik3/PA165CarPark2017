var carsControllers = angular.module('carsControllers', ['carServices']);

carsControllers.controller('carsCtrl', function ($scope, $rootScope, carFactory) {
    carFactory.getAllCars(
        function (response) {
            $scope.cars = response.data;
        },
        $rootScope.unsuccessfulResponse
    );
});