/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import java.util.Collection;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tomas Pavuk
 */
@Repository
public class RegionalBranchDAOImpl implements RegionalBranchDAO {

    @Override
    public void createRegionalBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionalBranch updateRegionalBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRegionalBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionalBranch findRegionalBranchById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<RegionalBranch> findAllRegionalBranches() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<RegionalBranch> findAllChildrenBranches(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionalBranch findParentBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Car> findAllCarsForBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Car> findAllAvaliableCarsForBranch(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
