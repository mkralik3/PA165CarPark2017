package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.RegionalBranchService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionalBranchFacadeImpl implements RegionalBranchFacade {
    @Inject
    private RegionalBranchService branchService;

    @Inject
    private BeanMappingService beanMappingService;
    @Override
    public RegionalBranchOperationResult createRegionalBranch(RegionalBranchDTO branch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionalBranchOperationResult updateRegionalBranch(RegionalBranchDTO branch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SimpleResult deleteRegionalBranch(long regionalBranchId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SimpleResult assignUser(long regionalBranchId, UserDTO user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SimpleResult assignCar(long regionalBranchId, CarDTO car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RegionalBranchDTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
