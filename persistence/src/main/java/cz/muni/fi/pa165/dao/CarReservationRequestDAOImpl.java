/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.DateTimeProvider;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.CarReservationRequest;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.CarReservationRequestState;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tomas Pavuk
 */
@Repository
public class CarReservationRequestDAOImpl implements CarReservationRequestDAO {

    private final DateTimeProvider dateTimeProvider;
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    public CarReservationRequestDAOImpl(DateTimeProvider dateTimeProvider){
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void createReservationRequest(CarReservationRequest reservation) {
        if (reservation.getCreationDate() == null) reservation.setCreationDate(dateTimeProvider.provideNow());
        if (reservation.getModificationDate() == null) reservation.setModificationDate(reservation.getCreationDate());
        em.persist(reservation);
    }

    @Override
    public CarReservationRequest updateReservationRequest(CarReservationRequest reservation) {
        if (reservation.getModificationDate() == null) reservation.setModificationDate(dateTimeProvider.provideNow());
        return em.merge(reservation);
    }

    @Override
    public void deleteReservationRequest(CarReservationRequest reservation) {
        em.remove(reservation);
    }

    @Override
    public CarReservationRequest findReservationByID(Long id) {
        return em.find(CarReservationRequest.class, id);
    }

    @Override
    public Collection<CarReservationRequest> findAllReservations() {
        return em.createQuery("SELECT r FROM CarReservationRequest r", CarReservationRequest.class)
                .getResultList();
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsByState(CarReservationRequestState state) {
        return em.createQuery("SELECT r FROM CarReservationRequest r WHERE r.state = :state", CarReservationRequest.class)
                .setParameter("state", state)
                .getResultList();
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsForCar(Car car) {
        return em.createQuery("SELECT r FROM CarReservationRequest r WHERE r.car = :car", CarReservationRequest.class)
                .setParameter("car", car)
                .getResultList();
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsForUser(User user) {
        return em.createQuery("SELECT r FROM CarReservationRequest r WHERE r.user = :user", CarReservationRequest.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsWhichStartBetween(LocalDateTime startDateFrom, LocalDateTime startDateTo) {
        return em.createQuery("SELECT r FROM CarReservationRequest r WHERE r.reservationStartDate BETWEEN :startDateFrom AND :startDateTo", CarReservationRequest.class)
                .setParameter("startDateFrom", startDateFrom)
                .setParameter("startDateTo", startDateTo)
                .getResultList();
    }

    @Override
    public Collection<CarReservationRequest> findAllReservationsWhichEndBetween(LocalDateTime endDateFrom, LocalDateTime endDateTo) {
        return em.createQuery("SELECT r FROM CarReservationRequest r WHERE r.reservationEndDate BETWEEN :endDateFrom AND :endDateTo", CarReservationRequest.class)
                .setParameter("endDateFrom", endDateFrom)
                .setParameter("endDateTo", endDateTo)
                .getResultList();
    }

}
