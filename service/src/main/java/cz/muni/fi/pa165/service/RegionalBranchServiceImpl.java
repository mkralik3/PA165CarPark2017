package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.CarDAO;
import cz.muni.fi.pa165.dao.RegionalBranchDAO;
import cz.muni.fi.pa165.dao.UserDAO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.service.enums.RegionalBranchOperationErrorCode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje, Matej Kralik
 */
@Service
public class RegionalBranchServiceImpl implements RegionalBranchService {

    @Inject
    private RegionalBranchDAO regionalBranchDao;
    
    @Inject
    private CarDAO carDao;
    
    @Inject
    private UserDAO userDao;
    
    @Inject
    private TimeService timeService;
    
    @Override
    public Set<RegionalBranchOperationErrorCode> create(RegionalBranch regionalBranch) {
        if (regionalBranch == null) {
            throw new IllegalArgumentException("regional branch is null");
        }
        Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
        regionalBranch.setCreationDate(timeService.getCurrentTime());
        regionalBranch.setModificationDate(timeService.getCurrentTime());
        try{
            regionalBranchDao.save(regionalBranch);
        }catch (DataAccessException ex) {
            errors.add(RegionalBranchOperationErrorCode.DATABASE_ERROR);
            // todo log
        }
        return errors;
    }

    @Override
    public Set<RegionalBranchOperationErrorCode> update(RegionalBranch regionalBranch) {
    	if (regionalBranch == null) {
            throw new IllegalArgumentException("regional branch is null");
        }
    	RegionalBranch existingBranch = regionalBranchDao.findOne(regionalBranch.getId());
        if (existingBranch == null) {
            throw new IllegalArgumentException("branch doesn't exist");
        }
        Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
        regionalBranch.setModificationDate(timeService.getCurrentTime());
        try{
            regionalBranchDao.save(regionalBranch);
        }catch (DataAccessException ex) {
            errors.add(RegionalBranchOperationErrorCode.DATABASE_ERROR);
            // todo log
        }
        return errors;
    }

    @Override
    public RegionalBranch delete(long id) {
        regionalBranchDao.delete(id);
        RegionalBranch branch = regionalBranchDao.findOne(id);
        if (branch != null) {
        	List<RegionalBranch> children = branch.getChildren();
        	if(children!=null){
        		RegionalBranch newParent = branch.getParent();
        		for(RegionalBranch child : children){
        			child.setParent(newParent);
        			regionalBranchDao.save(child);
        		}
        	}
        	regionalBranchDao.delete(branch);
        	//TODO log
        }
        return branch;
    }

    @Override
    public List<RegionalBranch> findAll() {
    	return regionalBranchDao.findAll();
    }

    @Override
    public RegionalBranch findOne(long id) {
    	return regionalBranchDao.findOne(id);
    }

	@Override
	public Set<RegionalBranchOperationErrorCode> assignUser(long regionalBranchId, User user) {
		if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
    	RegionalBranch existingBranch = regionalBranchDao.findOne(regionalBranchId);
        if (existingBranch == null) {
            throw new IllegalArgumentException("branch doesn't exist");
        }
        Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
		User findedUser = userDao.findOne(user.getId());
		if(findedUser == null){
	        errors.add(RegionalBranchOperationErrorCode.USER_DOESNT_EXIST);
	        //todo log
		}else{
			if(findedUser.getRegionalBranch()!=null){ //remove from old branch
				RegionalBranch old = findedUser.getRegionalBranch();
				old.getEmployees().remove(findedUser);
				this.update(old);
			}
			existingBranch.addEmployee(findedUser);
			errors.addAll(this.update(existingBranch));
			//todo log
		}
		return errors;
	}

	@Override
	public Set<RegionalBranchOperationErrorCode> assignCar(long regionalBranchId, Car car) {
		if (car == null) {
            throw new IllegalArgumentException("car is null");
        }
    	RegionalBranch existingBranch = regionalBranchDao.findOne(regionalBranchId);
        if (existingBranch == null) {
            throw new IllegalArgumentException("branch doesn't exist");
        }
        Set<RegionalBranchOperationErrorCode> errors = new HashSet<>();
		Car foundCar = carDao.findOne(car.getId());
		if(foundCar == null){
                    errors.add(RegionalBranchOperationErrorCode.CAR_DOESNT_EXIST);
	        //todo log
		}else{
			if(foundCar.getRegionalBranch()!=null){ //remove from old branch
				RegionalBranch old = foundCar.getRegionalBranch();
				if(old.getEmployees().remove(foundCar)){
					errors.addAll(this.update(old));
				}else{
					
				}
			}
			existingBranch.addCar(foundCar);
			errors.addAll(this.update(existingBranch));
			//todo log
		}
		return errors;
	}

	@Override
	public List<Car> findAllAvailableCarsForBranch(RegionalBranch regionalBranch, LocalDateTime day) {
		return regionalBranchDao.findAllAvailableCarsForBranch(regionalBranch.getName(), day);
	}
}
