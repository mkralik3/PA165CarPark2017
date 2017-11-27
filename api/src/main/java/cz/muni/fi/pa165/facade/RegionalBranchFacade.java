package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.*;

import java.time.LocalDateTime;
import java.util.List;

public interface RegionalBranchFacade {

	/**
	 * Create branch
	 * @param branch
	 * @return - created branch
	 */
    RegionalBranchOperationResult createRegionalBranch(RegionalBranchDTO branch);

    /**
     * Update branch
     * @param branch
     * @return - updated branch
     */
    RegionalBranchOperationResult updateRegionalBranch(RegionalBranchDTO branch);

    /**
	 * Delete regional branch. If branch contains children, for each children set new parent.
     * @param regionalBranchId
     * @return
     */
    SimpleResult deleteRegionalBranch(long regionalBranchId);

    List<RegionalBranchDTO> findAll();

    RegionalBranchDTO findOne(long id);

    /**
     * Assign user to the particular branch. If user is already in another branch, it will be reassigned
     * @param regionalBranchId
     * @param user
     * @return 
     */
    RegionalBranchOperationResult assignUser(long regionalBranchId, UserDTO user);

    /*
    If car is already assigned into some branch, it will be reasigned.
    */
    /**
     * Assign car to the particular branch. If car is already in another branch, it will be reassigned
     * @param regionalBranchId
     * @param car
     * @return
     */
    RegionalBranchOperationResult assignCar(long regionalBranchId, CarDTO car);

    /**
     * Find all available cars for particular day for particular branch
     * @param branch
     * @param day
     * @return - list of available cars
     */
    List<CarDTO> findAllAvailableCarsForBranch(RegionalBranchDTO branch, LocalDateTime day);
}
