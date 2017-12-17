Web.Services.BranchesService = function ($http) {
    this.getBranches = function (request, onSuccess, onError) {
        var response = {}
        response.data = {}
        response.data.isSuccess = true;

        var branches = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Branch();
            toAdd.id = i + 1;
            toAdd.name = "Branch " + (i + 1);
            branches.push(toAdd);
        }
        
        /*var response = {};
        response.data = new Web.Data.Get();
        response.data.isSuccess = true;

        var branches = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Branch();
            toAdd.id = i + 1;
            toAdd.name = "Branch " + (i + 1);
            branches.push(toAdd);
        }*/

        response.data.data = branches;
        setTimeout(function () { // simulation of async api call
            onSuccess(response);
        }, 0);
    }
    
    this.getAllUsers = function (request, onSuccess, onError) {
        var response = {};
        response.data = {};
        response.data.isSuccess = true;

        var users = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.User();
            toAdd.id = i + 1;
            toAdd.name = "User " + (i + 1);
            users.push(toAdd);
        }

        response.data.data = users;
        setTimeout(function () { 
            onSuccess(response);
        }, 0);
    }
    
    this.getAllCars = function() {
        var urlBase = "http://localhost:8080/car-park-web/rest";
        var urlCar = urlBase.concat("/car");

        var dataFactory={};
        dataFactory.getAllCars = function(success, error) {
            return $http.get(urlCar).then(success, error);
        };
        return dataFactory;
    }
}
Web.App.service('branchesService', ['$http', Web.Services.BranchesService]);