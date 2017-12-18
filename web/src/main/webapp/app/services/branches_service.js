Web.Services.BranchesService = function ($http) {
    this.getBranches = function (request, onSuccess, onError) {    
    	var urlBranch = urlBase.concat("/branch");     
        var response = {};
    	$http.get(urlBranch)
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
    
    this.getAllUsers = function (request, onSuccess, onError) {
    	var urlUsers = urlBase.concat("/user");     
        var response = {};
    	$http.get(urlUsers)
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
    
    this.deleteBranch = function (request, onSuccess, onError) {    
        var urlBranch = urlBase.concat("/branch/");
        var urlDelete = urlBranch.concat(request.id);
    	var response = {};
    	var req = {
                    method: 'DELETE',
                    url: urlBranch,
                    headers: {
                      'Content-Type': 'application/json'
                    },
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
    
    this.createBranch = function (request, onSuccess, onError) {    
        var urlBranch = urlBase.concat("/branch");     
    	var response = {};
    	var name = request.name;
        var cars = request.cars;
    	var req = {
                    method: 'POST',
                    url: urlBranch,
                    headers: {
                      'Content-Type': 'application/json'
                    },
                    data: {name, cars}
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
}
Web.App.service('branchesService', ['$http', Web.Services.BranchesService]);