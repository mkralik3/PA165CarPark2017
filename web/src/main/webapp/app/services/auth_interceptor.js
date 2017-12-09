Web.Services.AuthInterceptor = function ($rootScope, sessionManager) {
    return {
        'request': function (config) {
            config.headers = config.headers || {};

            var token = sessionManager.getToken();
            if (token) {
                config.headers.Authorization = token;
            }
            return config;
        },
        'responseError': function (rejection) {
            if (rejection != null && rejection.status == '401' && sessionManager.currentSession.validate()) {
                $rootScope.actions.logout();
            }
            return rejection;
        }
    }
}
Web.App.service('authInterceptor', ['$rootScope', 'sessionManager', Web.Services.AuthInterceptor]);