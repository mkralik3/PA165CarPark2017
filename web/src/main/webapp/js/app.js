var module = angular.module('carPark', ['ngRoute', 'carsControllers']);

module.config(function ($routeProvider) {

    $routeProvider
        .when('/', {
            templateUrl: 'parts/home.html'
        })
        .when('/car', {
            templateUrl: 'parts/cars.html',
            controller: 'carsCtrl',
            component: 'cars'
        })
        .otherwise({ redirectTo: '/' });
});

module.run(function($rootScope, $location, $window) {

    $rootScope.unsuccessfulResponse = function(response) {
        if (response.status == 403) {
            $rootScope.page = $location.path();
            $location.path("/forbidden");
        } else if (response.status == 401) {
            $window.location.href = "login.html"
        }
    };
});