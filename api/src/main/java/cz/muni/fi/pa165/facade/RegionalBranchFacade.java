package cz.muni.fi.pa165.facade;


import cz.muni.fi.pa165.contracts.RegionalBranch;
import cz.muni.fi.pa165.contracts.request.*;
import cz.muni.fi.pa165.contracts.response.*;

import java.util.List;

public interface RegionalBranchFacade {
    CreateRegionalBranchResponse createRegionalBranch(CreateRegionalBranchRequest request);

    UpdateRegionalBranchResponse updateRegionalBranch(UpdateRegionalBranchRequest request);

    DeleteRegionalBranchResponse deleteRegionalBranch(DeleteRegionalBranchRequest request);

    AssignUserResponse assignUser(AssignUserRequest request);

    AssignCarResponse assignUser(AssignCarRequest request);

    List<RegionalBranch> findAll();

    RegionalBranch findByName(String name);

//    getAllAvailableCars
//    getAllReservedCars

//    setChild
//      setManager

//      setParent
}
