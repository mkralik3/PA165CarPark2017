/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.RegionalBranch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @Override
    public RegionalBranch findParentBranch(RegionalBranch regionalBranch) {
        /** em.createQuery("SELECT b FROM RegionalBranch b WHERE b.parent = :regionalBranch", RegionalBranch.class)
         .setParameter("regionalBranch", regionalBranch)
         .getSingleResult(); **/
        return regionalBranch.getParent();
    }

    /**
     @Override public Collection<Car> findAllCarsForBranch(RegionalBranch regionalBranch) {
     return regionalBranch.getCars();
     }

     @Override public Collection<Car> findAllAvaliableCarsForBranch(RegionalBranch regionalBranch) {
     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
     **/
}
