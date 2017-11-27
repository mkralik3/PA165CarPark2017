package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.*;
import cz.muni.fi.pa165.dto.enums.RegionalBranchOperationErrorCode;
import cz.muni.fi.pa165.dto.results.*;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.RegionalBranchService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import cz.muni.fi.pa165.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionalBranchFacadeImpl implements RegionalBranchFacade {

    private final Logger log = LoggerFactory.getLogger(RegionalBranchFacadeImpl.class);

    @Inject
    private RegionalBranchService branchService;

    @Inject
    private BeanMappingService beanMappingService;
   
    @Override
    public RegionalBranchOperationResult createRegionalBranch(RegionalBranchDTO branch) {
    	RegionalBranchOperationResult result = new RegionalBranchOperationResult();
        try {
            RegionalBranch branchToCreate = beanMappingService.mapTo(branch, RegionalBranch.class);
            Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
            branchService.create(branchToCreate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, RegionalBranchOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(branchToCreate, RegionalBranchDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, RegionalBranchOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(RegionalBranchOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public RegionalBranchOperationResult updateRegionalBranch(RegionalBranchDTO branch) {
    	RegionalBranchOperationResult result = new RegionalBranchOperationResult();
        try {
            RegionalBranch branchToUpdate = beanMappingService.mapTo(branch, RegionalBranch.class);
            Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
            branchService.update(branchToUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, RegionalBranchOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
                result.setData(beanMappingService.mapTo(branchToUpdate, RegionalBranchDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, RegionalBranchOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(RegionalBranchOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public SimpleResult deleteRegionalBranch(long regionalBranchId) {
        SimpleResult result = new SimpleResult();
        try {
            RegionalBranch deletedBranch = branchService.delete(regionalBranchId);
            result.setIsSuccess(deletedBranch != null);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public RegionalBranchOperationResult assignUser(long regionalBranchId, UserDTO user) {
    	RegionalBranchOperationResult result = new RegionalBranchOperationResult();
        try {
            User userToUpdate = beanMappingService.mapTo(user, User.class);
            Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
            branchService.assignUser(regionalBranchId, userToUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, RegionalBranchOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
//               result.setData(beanMappingService.mapTo(userToUpdate, RegionalBranchDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, RegionalBranchOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(RegionalBranchOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public RegionalBranchOperationResult assignCar(long regionalBranchId, CarDTO car) {
    	RegionalBranchOperationResult result = new RegionalBranchOperationResult();
        try {
            Car carToUpdate = beanMappingService.mapTo(car, Car.class);
            Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
            branchService.assignCar(regionalBranchId, carToUpdate).forEach((x) -> {
                errors.add(beanMappingService.mapEnumTo(x, RegionalBranchOperationErrorCode.class));
            });
            if (errors.isEmpty()) {
//               result.setData(beanMappingService.mapTo(userToUpdate, RegionalBranchDTO.class));
                result.setIsSuccess(true);
            }
            errors.forEach((e) -> {
                result.getErrorCodes().add(beanMappingService.mapEnumTo(e, RegionalBranchOperationErrorCode.class));
            });
        } catch (Exception ex) {
            result.getErrorCodes().add(RegionalBranchOperationErrorCode.UNKNOWN_ERROR);
            log.error(ex.toString());
        }
        return result;
    }

    @Override
    public List<RegionalBranchDTO> findAll() {
    	List<RegionalBranchDTO> result = new ArrayList<>();
        try {
            List<RegionalBranch> branches = branchService.findAll();
            if (branches != null) {
            	branches.forEach((u) -> {
                    result.add(beanMappingService.mapTo(u, RegionalBranchDTO.class));
                });
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;  
    }

	@Override
	public RegionalBranchDTO findOne(long id) {
		RegionalBranchDTO result = null;
        try {
            RegionalBranch branch = branchService.findOne(id);
            if (branch != null) {
                result = beanMappingService.mapTo(branch, RegionalBranchDTO.class);
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result;
	}

	@Override
	public List<CarDTO> findAllAvailableCarsForBranch(RegionalBranchDTO branch, LocalDateTime day) {
		List<CarDTO> result = new ArrayList<>();
        try {
            RegionalBranch branchToFind = beanMappingService.mapTo(branch, RegionalBranch.class);
            List<Car> cars = branchService.findAllAvailableCarsForBranch(branchToFind, day);
            if (cars != null) {
            	cars.forEach((u) -> {
                    result.add(beanMappingService.mapTo(u, CarDTO.class));
                });
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return result; 
	}

}
