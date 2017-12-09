Web.Services.NotificationsService = function ($mdToast, $translate) {
    this.showSimple = function (message) {
        $translate(message).then(function (msg) {
            $mdToast.show($mdToast.simple().textContent(msg).position('bottom left').hideDelay(3000));
        });
    }

}
Web.App.service('notificationsService', ['$mdToast', '$translate', Web.Services.NotificationsService]);