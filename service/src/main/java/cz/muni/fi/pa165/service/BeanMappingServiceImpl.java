package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.entity.Car;
import cz.muni.fi.pa165.entity.RegionalBranch;
import cz.muni.fi.pa165.entity.User;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper mapper;

    @Override
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
    	 return objects.stream().map(o -> mapper.map(o, mapToClass)).collect(Collectors.toList());
    }

    @Override
    public  <T> T mapTo(Object u, Class<T> mapToClass)
    {
        return mapper.map(u,mapToClass);
    }

    @Override
    public Mapper getMapper(){
        return mapper;
    }

    @Override
    // Dozer does not support direct enum mapping
    public  <T extends Enum> T mapEnumTo(Enum u, Class<T> mapToClass) 
    {
        return (T)T.valueOf(mapToClass, u.name());
    }
    
	@Override
    public RegionalBranch mapTo(RegionalBranchDTO input, Class<RegionalBranch> mapToClass){
        if(input==null) {
        	return null;
        }
		RegionalBranch result = new RegionalBranch();
        result.setId(input.getId());
        result.setName(input.getName());
  //      result.setParent((input.getParent()==null ? null : this.mapTo(input.getParent(), RegionalBranch.class))); //TODO circular recursion
        result.setModificationDate(null);
        result.setCreationDate(null);
        input.getCars().forEach(item->
        	result.addCar(this.mapTo(item, Car.class))
        );
        input.getEmployees().forEach(item->
	    	result.addEmployee(this.mapTo(item, User.class))
	    );
        input.getChildren().forEach(item->
	    	result.addChild(this.mapTo(item, RegionalBranch.class))
	    );
		return result;
    }

	@Override
	public RegionalBranchDTO mapTo(RegionalBranch input, Class<RegionalBranchDTO> mapToClass){
		if(input==null) {
        	return null;
        }
		RegionalBranchDTO result = new RegionalBranchDTO();
        result.setId(input.getId());
        result.setName(input.getName());
  //      result.setParent((input.getParent()==null ? null : this.mapTo(input.getParent(), RegionalBranchDTO.class))); //TODO circular recursion
        input.getCars().forEach(item->
        	result.addCar(this.mapTo(item, CarDTO.class))
        );
        input.getEmployees().forEach(item->
	    	result.addEmployee(this.mapTo(item, UserDTO.class))
	    );
        input.getChildren().forEach(item->
	    	result.addChild(this.mapTo(item,RegionalBranchDTO.class))
	    );
		return result;
	}
}
