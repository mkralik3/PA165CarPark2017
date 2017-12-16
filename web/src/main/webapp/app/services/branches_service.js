Web.Services.BranchesService = function ($http) {
    this.getBranches = function (request, onSuccess, onError) {
        var response = {}
        response.data = {}
        response.data.isSuccess = true;

        var branches = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Branch();
            toAdd.id = i + 1;
            toAdd.name = "Branch " + (i + 1);
            branches.push(toAdd);
        }
        
        /*var response = {};
        response.data = new Web.Data.Get();
        response.data.isSuccess = true;

        var branches = [];
        for (var i = 0; i < 5; i++) {
            var toAdd = new Web.Data.Branch();
            toAdd.id = i + 1;
            toAdd.name = "Branch " + (i + 1);
            branches.push(toAdd);
        }*/

        response.data.data = branches;
        setTimeout(function () { // simulation of async api call
            onSuccess(response);
        }, 0);
    }
}
Web.App.service('branchesService', ['$http', Web.Services.BranchesService]);