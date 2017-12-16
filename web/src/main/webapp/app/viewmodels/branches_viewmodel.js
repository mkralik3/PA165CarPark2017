Web.ViewModels.BranchesViewModel = function () {
    this.id = null;
    this.name = null;

    this.convertToEvent = function () {
        result = {};
        result.id = this.id;
        result.color = "#007bff";
        result.notSelectedColor = result.color;
        result.title = this.regionalBranch.name;
        result.source = this;
        return result;
    };
};