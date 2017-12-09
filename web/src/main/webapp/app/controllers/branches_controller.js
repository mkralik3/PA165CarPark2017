Web.Controllers.BranchesController = function ($rootScope, $scope) {
    $rootScope.pageSubtitle = "BRANCHES.PAGE_SUBTITLE";
}

angular.module('CarParSystemWebApp').controller('BranchesController', ['$rootScope', '$scope', Web.Controllers.BranchesController]);