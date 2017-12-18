Web.Controllers.CarsController = function ($rootScope, $scope, $http, $mdDialog, notificationsService, contractConverter, settingsProvider, carsService) {
    var initList = function () {
        var request = new Web.Data.GetCarsRequest();

        carsService.getCars(request, function (httpResponse) {
            var response = httpResponse.data;
            if (response != null) {
                var data = response.data;
                if (response.isSuccess && data != null) {
                    $scope.viewModel.cars = [];
                    for (var i = 0; i < data.length; i++) {
                        var toAdd = contractConverter.convertCarToViewModel(data[i]);
                        $scope.viewModel.cars.push(toAdd);
                    }
                    //callback(cars);
                } else {
                    notificationsService.showSimple("RESERVATIONS.UNKNOWN_ERROR");
                }
            } else {
                notificationsService.showSimple("RESERVATIONS.UNKNOWN_ERROR");
            }
            $scope.viewModel.selectedEvent = null;
            $scope.$digest();
        }, function (httpResponse) {
            $scope.viewModel.selectedEvent = null;
            notificationsService.showSimple("RESERVATIONS.UNKNOWN_SERVER_ERROR");
        });
    }

    $scope.actions = new Object();
    $scope.actions.deleteSelectedCar = function () {
        if ($scope.viewModel.selectedItem != null) {
            var selectedEvent = $scope.viewModel.selectedItem;
            var selectedHashKey = $scope.viewModel.selectedItem.$$hashKey;
            for(var i = 0; i < $scope.viewModel.cars.length; i++) {
                if ($scope.viewModel.cars[i].$$hashKey === selectedHashKey) {
                    $scope.viewModel.cars.splice(i, 1);
                }
            }
            $scope.viewModel.selectedItem = null;
        }
    };

    $scope.actions.showAddCarDialog = function (ev) {
        $scope.viewModel.addCar = new Web.ViewModels.CarViewModel();
        $mdDialog.show({
            contentElement: '#addCarDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    };

    $scope.actions.addCar = function () {
        $scope.viewModel.cars.push($scope.viewModel.addCar);
        $scope.viewModel.addCar = null;
        $mdDialog.cancel();
    }

    $scope.actions.editSelectedCar = function (ev) {
        $scope.viewModel.addCar = $scope.viewModel.selectedItem;
        $mdDialog.show({
            contentElement: '#addCarDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    }

    $scope.actions.cancelAddCar = function () {
        $mdDialog.cancel();
    }

    $scope.setSelected = function(item) {
        $scope.viewModel.selectedItem = item;
    }

    $rootScope.pageSubtitle = "CARS.PAGE_SUBTITLE";
    $scope.carsList = $('#cars_list');
    $scope.viewModel = new Web.ViewModels.CarViewModel();//new Object();
    $scope.viewModel.cars = [];
    $scope.viewModel.selectedItem = null;
    $scope.viewModel.addCar = null;
    initList();
}

angular.module('CarParSystemWebApp').controller('CarsController', ['$rootScope', '$scope', '$http', '$mdDialog', 'notificationsService', 'contractConverter', 'settingsProvider', 'carsService', Web.Controllers.CarsController]);