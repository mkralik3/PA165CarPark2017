/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Matej Kralik
 */
@Repository
public class CarDAOImpl implements CarDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createCar(Car car) {
        em.persist(car);
    }

    @Override
    public Car updateCar(Car car) {
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
