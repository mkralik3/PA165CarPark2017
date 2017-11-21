package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;

import java.util.List;

public interface RegionalBranchFacade {

    void createRegionalBranch(RegionalBranchDTO branch);

    RegionalBranchDTO updateRegionalBranch(RegionalBranchDTO branch);

    void deleteRegionalBranch(long id);

    void assignUser(UserDTO user);

    void assignCar(CarDTO car);

    List<RegionalBranchDTO> findAll();

    RegionalBranchDTO findByName(String name);

    List<CarDTO> getAllAvailableCars(long id);

    List<CarDTO> getAllReservedCars(long id);

    void setChild(RegionalBranchDTO child);

    void setParent(RegionalBranchDTO parent);
}
