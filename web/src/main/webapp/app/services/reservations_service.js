Web.Services.ReservationsService = function ($http) {
    this.getReservations = function (request, onSuccess, onError) {
    	var urlReservations = urlBase.concat("/reservation/1"); 
    	var response = {};
    	request.dateTo.setMilliseconds(192); //workaround, time is not importatn
    	var start = request.dateFrom.toJSON();
    	var end = request.dateTo.toJSON();
    	var req = {
			 method: 'POST',
			 url: urlReservations,
			 headers: {
			   'Content-Type': 'application/json'
			 },
			 data: {start, end}
		}
    	
    	$http(req)
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
    
    this.deleteReservation = function(id, onSuccess, onError){
        $http.delete(urlBase.concat("/reservation/" + id)).then(function(httpResponse){
            if (httpResponse.status === 200){
                onSuccess();
            } else {
                onError();
            }
        }, function(httpResponse){
            onError();
        });
    }
    
    this.getAllReservations = function (request, onSuccess, onError) {
    	var urlAllReservations = urlBase.concat("/reservation");     
        var response = {};
    	$http.get(urlAllReservations)
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
Web.App.service('reservationsService', ['$http', Web.Services.ReservationsService]);