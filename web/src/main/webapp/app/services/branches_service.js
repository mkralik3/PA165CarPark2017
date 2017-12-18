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
}
Web.App.service('branchesService', ['$http', Web.Services.BranchesService]);