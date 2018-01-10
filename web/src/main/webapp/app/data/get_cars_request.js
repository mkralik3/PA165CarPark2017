Web.Data.GetCarsRequest = function (sessionManager) {
    this.branchId = sessionManager.currentSession.branchId;
    this.getAllCars = false;
}