Web.Services.SettingsProvider = function () {
    this.currentLanguage = "en";
    this.currentCulture = "en-GB";
}
Web.App.service('settingsProvider', [Web.Services.SettingsProvider]);