package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface for regional branch entity
 *
 * @author Matej Kralik
 */
@org.springframework.stereotype.Repository
public interface RegionalBranchDAO extends CrudRepository<RegionalBranch, Long> {

    /**
     * Find all branches
     * @return all branches
     */
    List<RegionalBranch> findAll();

    /**
     * Find all regional branches which are children for particular regional branch
     *
     * @param nameOfBranch - particular regional branch
     * @return collection of all regional branch which are children or null if none exists
     */
    @Query("SELECT b FROM RegionalBranch b WHERE b.parent.name = :name")
    List<RegionalBranch> findAllChildrenBranches(@Param("name") String nameOfBranch);

    /**
     * Find all cars which are available for reservation in the particular regional branch in the particular day
     * @param nameOfBranch - name of particular regional branch
     * @param day - day for which is car available (e.g. today)
     * @return collection of cars which are available in the particular branch or null if none exists
     */
    @Query("SELECT c1 FROM RegionalBranch b JOIN b.cars c1 WHERE b.name = :name AND c1 " +
            "NOT IN " + // car is not in actual reserved cars
            "(SELECT c2 FROM CarReservationRequest cr JOIN cr.car c2 WHERE cr.reservationEndDate > :day)" + //select all car which are reserved
            "OR c1 IN " + // or car is in actual reserved cars but reservation was denied
            "(SELECT c3 FROM CarReservationRequest cr2 JOIN cr2.car c3 WHERE cr2.reservationEndDate > :day " +
                "AND cr2.state = cz.muni.fi.pa165.enums.CarReservationRequestState.DENIED)")
    List<Car> findAllAvailableCarsForBranch(@Param("name") String nameOfBranch,
                                                  @Param("day") LocalDateTime day);

    /**
     * Find all managers for particular branch
     * @param nameOfBranch - name of particular regional branch
     * @return list of all managers for particular branch
     */
    @Query("SELECT e FROM RegionalBranch b JOIN b.employees e WHERE b.name = :name " +
            "AND e.type = cz.muni.fi.pa165.enums.UserType.BRANCH_MANAGER")
    List<User>getAllManagersInBranch(@Param("name") String nameOfBranch);
}
