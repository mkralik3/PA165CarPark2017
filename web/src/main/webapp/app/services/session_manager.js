Web.Services.SessionManager = function (localStorageService) {
    var SESSION_INFO_KEY = "authData.sessionInfo";
    var TOKEN_KEY = "authData.token";
    this.currentSession = new Web.ViewModels.SessionInfoViewModel();

    this.initialize = function () {
        loadedSessionInfo = localStorageService.get(SESSION_INFO_KEY);
        if (loadedSessionInfo != null) {
            this.currentSession = new Web.ViewModels.SessionInfoViewModel();
            this.currentSession.initialize(loadedSessionInfo);
        }
    }

    this.createSession = function (token, sessionInfo) {
        localStorageService.set(TOKEN_KEY, token);
        localStorageService.set(SESSION_INFO_KEY, sessionInfo);
        this.currentSession = new Web.ViewModels.SessionInfoViewModel();
        this.currentSession.initialize(sessionInfo);
    }

    this.updateSession = function (sessionInfo) {
        localStorageService.set(SESSION_INFO_KEY, sessionInfo);
        this.currentSession = new Web.ViewModels.SessionInfoViewModel();
        this.currentSession.initialize(sessionInfo);
    }

    this.getToken = function () {
        return localStorageService.get(TOKEN_KEY);
    }

    this.destroySession = function () {
        localStorageService.remove(SESSION_INFO_KEY);
        localStorageService.remove(TOKEN_KEY);
        this.currentSession = new Web.ViewModels.SessionInfoViewModel();
    }
}
Web.App.service('sessionManager', ['localStorageService', Web.Services.SessionManager]);