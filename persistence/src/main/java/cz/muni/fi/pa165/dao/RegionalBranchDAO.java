package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;

import java.util.Collection;

/**
 * The interface for regional branch entity
 * @author Matej Kralik
 */
public interface RegionalBranchDAO {
    /**
     * Create regional branch in database
     * @param regionalBranch - particular regional branch
     */
    void createRegionalBranch(RegionalBranch regionalBranch);

    /**
     * Update particular regional branch in database
     * @param regionalBranch - particular regional branch
     * @return updated branch
     */
    RegionalBranch updateRegionalBranch(RegionalBranch regionalBranch);

    /**
     * Delete particular regional branch
     * @param regionalBranch - particular branch
     */
    void deleteRegionalBranch(RegionalBranch regionalBranch);

    /**
     * Find regional branch by id
     * @param id - id of regional branch
     * @return specific regional branch of null if none exists
     */
    RegionalBranch findRegionalBranchById(Long id);

    /**
     * Find all regional branches
     * @return collection of all regional branches or null if none exists
     */
    Collection<RegionalBranch> findAllRegionalBranches();

    /**
     * Find all regional branches which are children for particular regional branch
     * @param regionalBranch - particular regional branch
     * @return collection of all regional branch which are children or null if none exists
     */
    Collection<RegionalBranch> findAllChildrenBranches(RegionalBranch regionalBranch);

    /**
     * Find regional branch which is parent for particular regional branch
     * @param regionalBranch - particular regional branch
     * @return parent regional branch or null if none exists
     */
    RegionalBranch findParentBranch(RegionalBranch regionalBranch);

    /**
     * Find all cars which are in the particular regional branch
     * @param regionalBranch - particular regional branch
     * @return collection of cars which are in the particular branch or null if none exists
     */
    Collection<Car> findAllCarsForBranch(RegionalBranch regionalBranch);

    /**
     * Find all cars which are available for reservation in the particular regional branch
     * @param regionalBranch - particular regional branch
     * @return collection of cars which are available in the particular branch or null if none exists
     */
    Collection<Car> findAllAvaliableCarsForBranch(RegionalBranch regionalBranch);
}
