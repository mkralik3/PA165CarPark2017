Web.Controllers.BranchesController = function ($rootScope, $scope, $http, $mdDialog, notificationsService, contractConverter, settingsProvider, carService, branchesService) {
    var initList = function () {
        var request = new Web.Data.GetBranchesRequest();
        
        var urlBase = "http://localhost:8080/car-park-web/rest";
        var urlCar = urlBase.concat("/car");
        $http.get(urlCar)
            .then(function(response){ 
                $scope.details = response.data; 
        });
        /*branchesService.getAllCars(
            function (response) {
                $scope.laboratories = response.data;
        },
        $rootScope.unsuccessfulResponse);*/
        
        branchesService.getBranches(request, function (httpResponse) {
            var response = httpResponse.data;
            if (response !== null) {
                var data = response.data;
                if (response.isSuccess && data !== null) {
                    $scope.viewModel.branches = [];
                    for (var i = 0; i < data.length; i++) {
                        var toAdd = contractConverter.convertBranchToViewModel(data[i]);
                        $scope.viewModel.branches.push(toAdd);
                    }
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
        var requestUsers = new Web.Data.GetUsersRequest();
        branchesService.getAllUsers(requestUsers, function (httpResponse) {
            var response = httpResponse.data;
            if (response !== null) {
                var data = response.data;
                if (response.isSuccess && data !== null) {
                    $scope.viewModel.users = [];
                    for (var i = 0; i < data.length; i++) {
                        var toAdd = contractConverter.convertUserToViewModel(data[i]);
                        $scope.viewModel.users.push(toAdd);
                    }
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
        var request = new Web.Data.GetCarsRequest();
            carService.getCars(request, function (httpResponse) {
                var response = httpResponse.data;
                if (response != null) {
                    var data = response.data;
                    if (response.isSuccess && data != null) {
                        $scope.viewModel.cars = [];
                        var cars = []
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
                $scope.$digest();
            }, function (httpResponse) {
                notificationsService.showSimple("RESERVATIONS.UNKNOWN_SERVER_ERROR");
        });
    }

    var updateEventColor = function (calEvent, color) {
        calEvent.color = color;
        //$scope.calendarElement.fullCalendar('updateEvent', calEvent);
    }

    $scope.actions = new Object();
    $scope.actions.deleteSelectedRegionalBranch = function () {
        if ($scope.viewModel.selectedItem != null) {
            var selectedEvent = $scope.viewModel.selectedItem;
            var selectedHashKey = $scope.viewModel.selectedItem.$$hashKey;
            for(var i = 0; i < $scope.viewModel.branches.length; i++) {
                if ($scope.viewModel.branches[i].$$hashKey === selectedHashKey) {
                    $scope.viewModel.branches.splice(i, 1);
                }
            }
            $scope.viewModel.selectedItem = null;
        }
    };
    
    $scope.actions.showAddBranchDialog = function (ev) {
        $scope.viewModel.addBranch = new Web.ViewModels.BranchesViewModel();
        $mdDialog.show({
            contentElement: '#addBranchDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
    };
    
    $scope.actions.addBranch = function () {
        $scope.selectedCars = [];
        angular.forEach($scope.viewModel.cars, function(car){
            if (!!car.selected) $scope.selectedCars.push(car.name);
        })
        $scope.viewModel.branches.push($scope.viewModel.addBranch);
        $scope.viewModel.addBranch = null;
        $scope.viewModel.manager = null;
        $mdDialog.cancel();
    }
    
    $scope.actions.editSelectedBranch = function (ev) {
        //$scope.viewModel.manager = $scope.viewModel.selectedItem.manager;
        //$scope.viewModel.cars = $scope.viewModel.selectedItem.cars;
        $scope.viewModel.addBranch = $scope.viewModel.selectedItem;
        $mdDialog.show({
            contentElement: '#addBranchDialog',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true
        });
        
        
        /*d$scope.selectedCars = [];
        angular.forEach($scope.viewModel.cars, function(car){
            if (!!car.selected) $scope.selectedCars.push(car.name);
        })
        $scope.viewModel.branches.push($scope.viewModel.addBranch);
        $scope.viewModel.addBranch = null;
        $scope.viewModel.manager = null;
        $mdDialog.cancel();*/
    }
    
    $scope.actions.cancelAddBranch = function () {
        $mdDialog.cancel();
    }
    
    $scope.setSelected = function(item) {
        $scope.viewModel.selectedItem = item;
    }
    
    $rootScope.pageSubtitle = "BRANCHES.PAGE_SUBTITLE";
    $scope.branchesList = $('#branches_list');
    $scope.viewModel = new Web.ViewModels.BranchesViewModel();//new Object();
    $scope.selectedCars = [];
    $scope.viewModel.branches = [];
    $scope.viewModel.users = [];
    $scope.viewModel.cars = [];
    $scope.viewModel.manager = null;
    $scope.viewModel.selectedItem = null;
    $scope.viewModel.addBranch = null;
    initList();
}

angular.module('CarParSystemWebApp').controller('BranchesController', ['$rootScope', '$scope', '$http', '$mdDialog', 'notificationsService', 'contractConverter', 'settingsProvider', 'carsService', 'branchesService', Web.Controllers.BranchesController]);