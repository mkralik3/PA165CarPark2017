var Web = Web || {};

var urlBase = "http://localhost:8080/pa165/rest";

Web.createNamespace = function (namespace) {
    var parts = namespace.split(".");
    var parent = Web;

    if (parts[0] === "Web") {
        parts = parts.slice(1);
    }

    for (var i = 0; i < parts.length; i++) {
        var partname = parts[i];
        if (typeof parent[partname] === "undefined") {
            parent[partname] = {};
        }
        parent = parent[partname];
    }
    return parent;
};
Web.createNamespace("Web.Constants");
Web.createNamespace("Web.Controllers");
Web.createNamespace("Web.Data");
Web.createNamespace("Web.Services");
Web.createNamespace("Web.ViewModels");

Web.App = angular.module('CarParSystemWebApp', ['ui.router', 'ngCookies', 'pascalprecht.translate', 'LocalStorageModule', 'ngMaterial']);

Web.App.config(['$stateProvider', '$urlRouterProvider', '$translateProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $translateProvider, $httpProvider) {
    $urlRouterProvider.otherwise(Web.Constants.StateNames.IMPLICIT);
    $stateProvider.state(Web.Constants.StateNames.BRANCHES, {
        url: "/branches",
        controller: "BranchesController",
        templateUrl: "app/views/branches.html",
    });
    $stateProvider.state(Web.Constants.StateNames.CARS, {
        url: "/cars",
        controller: "CarsController",
        templateUrl: "app/views/cars.html",
    });
    $stateProvider.state(Web.Constants.StateNames.LOGIN, {
        url: "/login",
        controller: "LoginController",
        templateUrl: "app/views/login.html",
    });
    $stateProvider.state(Web.Constants.StateNames.RESERVATIONS, {
        url: "/reservations",
        controller: "ReservationsController",
        templateUrl: "app/views/reservations.html",
    });

    $translateProvider.useStaticFilesLoader({
        prefix: 'app/content/lang/locale-',
        suffix: '.json'
    });
    $translateProvider.useSanitizeValueStrategy('escape');
    $translateProvider.preferredLanguage('en');

    $httpProvider.interceptors.push('authInterceptor');
}]);

Web.App.run(['$rootScope', '$state', 'sessionManager', 'settingsProvider', 'contractConverter', 'authService', '$translate', '$transitions', function ($rootScope, $state, sessionManager, settingsProvider, contractConverter, authService, $translate, $transitions) {
    $rootScope.services = new Object();
    $rootScope.services.sessionManager = sessionManager;
    $rootScope.services.sessionManager.initialize();
    $rootScope.services.settingsProvider = settingsProvider;
    $rootScope.services.contractConverter = contractConverter;

    $rootScope.actions = new Object();
    $rootScope.actions.logout = function () {
        authService.logout(null, function (response) {
            $rootScope.services.sessionManager.destroySession();
            $state.go(Web.Constants.StateNames.LOGIN);
        }, function (response) {
            $rootScope.services.sessionManager.destroySession();
            $state.go(Web.Constants.StateNames.LOGIN);
        });
    }
    $rootScope.actions.changeCulture = function (language, culture) {
        settingsProvider.currentLanguage = language;
        settingsProvider.currentCulture = culture;
        $translate.use(language);
        var session = new Web.ViewModels.SessionInfoViewModel();
        session.initialize(sessionManager.currentSession)
        session.currentLanguage = language;
        session.currentCulture = culture;
        sessionManager.updateSession(session);
    }

    settingsProvider.currentCulture = sessionManager.currentSession.currentCulture;
    settingsProvider.currentLanguage = sessionManager.currentSession.currentLanguage;
    $translate.use(sessionManager.currentSession.currentLanguage);
    $translate.preferredLanguage(sessionManager.currentSession.currentLanguage);
    $transitions.onStart({ to: '*' }, function (trans) {
        if (trans.to().name == Web.Constants.StateNames.LOGIN) {
            if ($rootScope.services.sessionManager.currentSession.validate()) {
                return false;
            }
        }
        else {
            if (!$rootScope.services.sessionManager.currentSession.validate()) {
                return trans.router.stateService.target(Web.Constants.StateNames.LOGIN);
            }
        }
    });
}]);
