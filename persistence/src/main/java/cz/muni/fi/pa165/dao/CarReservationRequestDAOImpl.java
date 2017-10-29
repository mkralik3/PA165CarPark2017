/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import java.time.ZonedDateTime;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomas Pavuk
 */
@Repository
public class CarReservationRequestDAOImpl implements CarReservationRequestDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createReservationRequest(CarReservationRequest reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CarReservationRequest updateReservationRequest(CarReservationRequest reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteReservationRequest(CarReservationRequest reservation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CarReservationRequest findReservationByID(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsByState(CarReservationRequestState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsForCar(Car car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsForUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsWhichStartBetween(ZonedDateTime startDateFrom, ZonedDateTime startDateTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsWhichEndBetween(ZonedDateTime endDateFrom, ZonedDateTime endDateTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
