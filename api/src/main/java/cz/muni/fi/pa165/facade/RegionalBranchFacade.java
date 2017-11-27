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
	 * @param branch branch which will be created
	 * @return result of operation with data
     * @throws IllegalArgumentException if branch is null
	 */
    RegionalBranchOperationResult createRegionalBranch(RegionalBranchDTO branch);

    /**
     * Update branch
     * @param branch ranch which will be updated
     * @return result of operation with data
     * @throws IllegalArgumentException if branch is null or it is not exist
     */
    RegionalBranchOperationResult updateRegionalBranch(RegionalBranchDTO branch);

    /**
	 * Delete regional branch. If branch contains children, for each children set new parent.
     * @param regionalBranchId id of branch
     * @return simple result (isSuccess = true if found and deleted, otherwise false)
     */
    SimpleResult deleteRegionalBranch(long regionalBranchId);

    /**
     * Find all branches
     * @return all branches
     */
    List<RegionalBranchDTO> findAll();

    /**
     * Find particular branch
     * @param id - id of particular branch
     * @return particular branch or null
     */
    RegionalBranchDTO findOne(long id);

    /**
     * Assign user to the particular branch. If user is already in
     * another branch, it will be reassigned to the new branch
     * @param regionalBranchId id of particular branch where user will be assigned
     * @param user particular user
     * @return result of operation with data
     * @throws IllegalArgumentException if user is null or branch is not exist
     * @throws IllegalStateException if user has set branch but same time it is not in this branch
     */
    RegionalBranchOperationResult assignUser(long regionalBranchId, UserDTO user);

    /**
     * Assign car to the particular branch. If car is already in
     * another branch, it will be reassigned
     * @param regionalBranchId id of particular branch where car will be assigned
     * @param car particular user
     * @throws IllegalArgumentException if car is null or branch is not exist
     * @throws IllegalStateException if car has set branch but same time it is not in this branch
     */
    RegionalBranchOperationResult assignCar(long regionalBranchId, CarDTO car);

    /**
     * Find all available cars for particular day for particular branch
     * @param branch particular branch
     * @param day particular day
     * @return list of available cars for that day
     */
    List<CarDTO> findAllAvailableCarsForBranch(RegionalBranchDTO branch, LocalDateTime day);
}
