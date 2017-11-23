package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;

import java.util.List;

public class RegionalBranchFacadeImpl implements RegionalBranchFacade {
    @Override
    public void createRegionalBranch(RegionalBranchDTO branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RegionalBranchDTO updateRegionalBranch(RegionalBranchDTO branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteRegionalBranch(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignUser(UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void assignCar(CarDTO car) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<RegionalBranchDTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RegionalBranchDTO findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CarDTO> getAllAvailableCars(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CarDTO> getAllReservedCars(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setChild(RegionalBranchDTO child) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setParent(RegionalBranchDTO parent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
