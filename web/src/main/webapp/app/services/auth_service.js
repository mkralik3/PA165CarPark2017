Web.Services.AuthService = function ($http) {
    this.login = function (request, onSuccess, onError) {
        var response = {}
        response.data = new Web.Data.AuthResponse();
        response.data.isSuccess = true;
        response.data.token = "testToken";
        response.data.data = new Web.Data.User();
        if (request.username === "admin"){
            response.data.data.id = 1;
            response.data.data.userType = 2; //0=user, 1=branchmanager, 2=admin
        } else if (request.username === "manager"){
            response.data.data.id = 2;
            response.data.data.userType = 1;
        } else {
            response.data.data.id = 3;
            response.data.data.userType = 0;
        }
        
        response.data.data.branchName = "Brno";
        onSuccess(response);
        /*
        var req = {
            method: 'POST',
            url: 'api/session/login',
            data: request
        }
        $http(req).then(function (response) {
            if (response.status == 200) {
                onSuccess(response);
            } else {
                onError(response);
            }
        }, function (response) {
            onError(response);
        });*/
    }

    this.logout = function (request, onSuccess, onError) {
        onSuccess(null);
        /*
        var req = {
            method: 'POST',
            url: 'api/session/logout',
            data: request
        }
        $http(req).then(function (response) {
            if (response.status == 200) {
                onSuccess(response);
            } else {
                onError(response);
            }
        }, function (response) {
            onError(response);
        });*/
    }
}
Web.App.service('authService', ['$http', Web.Services.AuthService]);