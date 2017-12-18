Web.Services.ContractConverter = function () {
    this.convertAuthError = function (error) {
        if (error != null) {
            errorToUse = error.toUpperCase();
            switch (errorToUse) {
                case 'NONAUTHENTICATED':
                case 'LOCKED':
                case 'DISABLED':
                    return 'LOGIN.LOGIN_FAILED_' + errorToUse;
                    break;
            }
        }
        return 'LOGIN.LOGIN_FAILED_UNKNOWN';
    }

    this.convertReservationToViewModel = function (source) {
        var result = new Web.ViewModels.ReservationViewModel();
        if (source != null) {
            result.id = source.id;
            result.startDate = source.startDate;
            result.endDate = source.endDate;
            result.user = this.convertUserToViewModel(source.user);
            result.car = this.convertCarToViewModel(source.car);
        }
        return result;
    }

    this.convertCarToViewModel = function (source) {
        var result = new Web.ViewModels.CarViewModel();
        if (source != null) {
            result.id = source.id;
            result.name = source.name;
        }
        return result;
    }

    this.convertUserToViewModel = function (source) {
        var result = new Web.ViewModels.UserViewModel();
        if (source != null) {
            result.id = source.id;
            result.username = source.userName;
            result.name = source.userName;
            result.userType = source.type;
            result.branchName = source.branchName;
        }
        return result;
    }
    
    this.convertBranchToViewModel = function (source) {
        var result = new Web.ViewModels.BranchesViewModel();
        if (source != null) {
            result.id = source.id;
            result.name = source.name;
            
            result.employees = [];
            for (var i = 0; i < source.employees.length; i++) {
                var toAdd = this.convertUserToViewModel(source.employees[i]);
                result.employees.push(toAdd);
            }           	
            result.cars = [];
            for (var i = 0; i < source.cars.length; i++) {
                var toAdd = this.convertCarToViewModel(source.cars[i]);
                result.cars.push(toAdd);
            } 	
        }
        return result;
    }
}
Web.App.service('contractConverter', [Web.Services.ContractConverter]);