Web.Controllers.CarsController = function ($rootScope, $scope) {
    $rootScope.pageSubtitle = "CARS.PAGE_SUBTITLE";
}

angular.module('CarParSystemWebApp').controller('CarsController', ['$rootScope', '$scope', Web.Controllers.CarsController]);