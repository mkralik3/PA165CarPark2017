/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import java.time.Clock;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import cz.muni.fi.pa165.DateTimeProvider;

/**
 *
 * @author Matej Kralik
 */
@Repository
public class CarDAOImpl implements CarDAO {

    private final DateTimeProvider dateTimeProvider;
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    public CarDAOImpl(DateTimeProvider dateTimeProvider){
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public void createCar(Car car) {
        car.setCreationDate(dateTimeProvider.provideNow());
        car.setModificationDate(car.getCreationDate());
        em.persist(car);
    }

    @Override
    public Car updateCar(Car car) {
        car.setModificationDate(dateTimeProvider.provideNow());
        return em.merge(car);
    }

    @Override
    public void deleteCar(Car car) {
        em.remove(car);
    }

    @Override
    public Collection<Car> findAllCars() {
        return em.createQuery("select c from Car c", Car.class)
                .getResultList();    }

    @Override
    public Car findCarById(Long id) {
        return em.find(Car.class, id);
    }
}
