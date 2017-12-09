Web.Services.CarsService = function ($http) {
    this.getCars = function (request, onSuccess, onError) {
        var response = {};
        response.data = new Web.Data.GetCarsResponse();
        response.data.isSuccess = true;

        var cars = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Car();
            toAdd.id = i + 1;
            toAdd.name = "Car " + (i + 1);
            cars.push(toAdd);
        }

        response.data.data = cars;
        setTimeout(function () { // simulation of async api call
            onSuccess(response);
        }, 0);
    }
}
Web.App.service('carsService', ['$http', Web.Services.CarsService]);