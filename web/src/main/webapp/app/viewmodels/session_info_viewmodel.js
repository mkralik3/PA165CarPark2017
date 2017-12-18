Web.ViewModels.SessionInfoViewModel = function() {
    this.userId = 0;
    this.username = null;
    this.userType = 0;
    this.branchName = "";
    this.currentCulture = "en-GB";
    this.currentLanguage = "en";
    this.isValid = false;

    this.validate = function () {
        this.isValid = this.username != null && this.userId > 0;
        return this.isValid;
    }

    this.initialize = function (session) {
        if (session != null) {
            this.userId = session.userId;
            this.username = session.username;
            this.userType = session.userType;
            this.branchName = session.branchName;
            this.currentCulture = session.currentCulture;
            this.currentLanguage = session.currentLanguage;
            this.validate();
        }
    }
}