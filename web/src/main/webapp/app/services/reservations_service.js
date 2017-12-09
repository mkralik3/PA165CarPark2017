Web.Services.ReservationsService = function ($http) {
    this.getReservations = function (request, onSuccess, onError) {
        var response = {}
        response.data = {}
        response.data.isSuccess = true;

        var cars = [];
        var reservations = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Car();
            toAdd.id = i + 1;
            toAdd.name = "Car " + (i + 1);
            cars.push(toAdd);
        }
        for (var i = 0; i < 9; i++) {
            var toAdd = new Web.Data.Reservation();
            toAdd.id = i + 1;
            toAdd.startDate = new Date(request.dateFrom);
            toAdd.startDate.setHours(toAdd.startDate.getHours() + i + (new Date().getSeconds() % 3));
            toAdd.endDate = toAdd.startDate;
            toAdd.endDate.setHours(toAdd.endDate.getHours() + 1);
            toAdd.user = new Web.Data.User();
            toAdd.user.id = 1;
            toAdd.user.name = "John Doe";
            toAdd.car = cars[i % 5];
            reservations.push(toAdd);
        }

        response.data.data = reservations;
        setTimeout(function () { // simulation of async api call
            onSuccess(response);
        }, 0);
    }
}
Web.App.service('reservationsService', ['$http', Web.Services.ReservationsService]);