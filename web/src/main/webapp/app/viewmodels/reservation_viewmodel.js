Web.ViewModels.ReservationViewModel = function () {
    this.id = null;
    this.startDate = null;
    this.endDate = null;
    this.user = null;
    this.car = null;

    this.convertToEvent = function () {
        result = {};
        result.id = this.id;
        result.color = "#007bff";
        result.notSelectedColor = result.color;
        result.start = this.startDate;
        result.end = this.endDate;
        result.resourceId = this.car.id;
        result.title = this.user.name;
        result.source = this;
        return result;
    }
}