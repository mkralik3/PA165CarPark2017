Web.ViewModels.ReservationViewModel = function () {
    this.id = null;
    this.startDate = null;
    this.endDate = null;
    this.user = null;
    this.car = null;
    this.state = null;

    this.convertToEvent = function () {
        result = {};
        result.id = this.id;
        if(this.state === 'APPROVED'){
            result.title = this.user.name + ' \n APPROVED';
            result.color = "#02ff0e";
        }else{
            result.title = this.user.name + ' \n WAITING FOR APPROVED';
            result.color = "#007bff";
        }
        result.notSelectedColor = result.color;
        result.start = this.startDate;
        result.end = this.endDate;
        result.resourceId = this.car.id;
        result.source = this;
        result.isSecret = false;
        return result;
    }

    this.convertToSecretEvent = function () {
        result = {};
        result.id = this.id;
        result.color = "#949494";
        result.notSelectedColor = result.color;
        result.start = this.startDate;
        result.end = this.endDate;
        result.resourceId = this.car.id;
        result.title = 'Car is reserved!';
        result.source = this;
        result.isSecret = true;
        return result;
    }
}