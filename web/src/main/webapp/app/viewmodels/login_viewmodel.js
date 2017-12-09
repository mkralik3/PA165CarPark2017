Web.ViewModels.LoginViewModel = function() {
    this.username = null;
    this.password = null;
    this.infoMessage = null;
    this.isValid = false;

    this.validate = function () {
        this.isValid = this.username != null && this.username.length >= 3 && this.password != null && this.password.length >= 3;
        return this.isValid;
    }
}