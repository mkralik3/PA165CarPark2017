/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Tomas Pavuk
 */
@Repository
public class RegionalBranchDAOImpl implements RegionalBranchDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createRegionalBranch(RegionalBranch regionalBranch) {
        if (regionalBranch == null)
            throw new NullPointerException("You can't create null branch!");
        em.persist(regionalBranch);
    }

    @Override
    public RegionalBranch updateRegionalBranch(RegionalBranch regionalBranch) {
        if (regionalBranch == null)
            throw new NullPointerException("You can't update null branch!");
        return em.merge(regionalBranch);
    }

    @Override
    public void deleteRegionalBranch(RegionalBranch regionalBranch) {
        if (regionalBranch == null)
            throw new NullPointerException("You can't delete null branch!");
        em.remove(regionalBranch);
    }

    @Override
    public RegionalBranch findRegionalBranchById(Long id) {
        return em.find(RegionalBranch.class, id);
    }

    @Override
    public Collection<RegionalBranch> findAllRegionalBranches() {
        return em.createQuery("SELECT b FROM RegionalBranch b", RegionalBranch.class)
                .getResultList();
    }

    @Override
    public Collection<RegionalBranch> findAllChildrenBranches(RegionalBranch regionalBranch) {
        return em.createQuery("SELECT b FROM RegionalBranch b WHERE b.parent.name = :name", RegionalBranch.class)
                .setParameter("name", regionalBranch.getName())
                .getResultList();
    }

     @Override public Collection<Car> findAllAvailableCarsForBranch(RegionalBranch regionalBranch) {
         return em.createQuery("SELECT c1 FROM RegionalBranch b JOIN b.cars c1 WHERE c1 " +
                         "NOT IN " + // car is not in actual reserved cars
                         "(SELECT c2 FROM CarReservationRequest cr JOIN cr.car c2 WHERE cr .reservationEndDate > :today)" + //select all car which are reserved
                         "OR c1 IN " + // or car is in actual reserved cars but reservation was denied
                         "(SELECT c3 FROM CarReservationRequest cr2 JOIN cr2.car c3 WHERE cr2.reservationEndDate > :today AND cr2.state = :deniedState) ",
                 Car.class)
                 .setParameter("today", LocalDateTime.now())
                 .setParameter("deniedState", CarReservationRequestState.DENIED)
                 .getResultList();
     }
}
