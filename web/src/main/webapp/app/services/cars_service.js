Web.Services.CarsService = function ($http) {
    this.getCars = function (request, onSuccess, onError) {
    	var urlCars = urlBase.concat("/car");     
        var response = {};
    	$http.get(urlCars)
    		.then(function(httpResponse) {
                response.data = httpResponse;
                response.data.isSuccess = true;
                onSuccess(response);
            }, function(httpResponse) {
                response.data = httpResponse;
                response.data.isSuccess = false;
                error(response);
		    });
    }
}
Web.App.service('carsService', ['$http', Web.Services.CarsService]);