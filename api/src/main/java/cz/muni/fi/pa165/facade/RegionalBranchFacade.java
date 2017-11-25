package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.*;

import java.util.List;

public interface RegionalBranchFacade {

    RegionalBranchOperationResult createRegionalBranch(RegionalBranchDTO branch);

    RegionalBranchOperationResult updateRegionalBranch(RegionalBranchDTO branch);

    SimpleResult deleteRegionalBranch(long regionalBranchId);

    /*
    If user is already assigned into some branch, it will be reasigned.
    */
    SimpleResult assignUser(long regionalBranchId, UserDTO user);

    /*
    If car is already assigned into some branch, it will be reasigned.
    */
    SimpleResult assignCar(long regionalBranchId, CarDTO car);

    List<RegionalBranchDTO> findAll();
}
