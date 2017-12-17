package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.RegionalBranchOperationResult;
import cz.muni.fi.pa165.dto.results.SimpleResult;
import cz.muni.fi.pa165.exceptions.ResourceNotFound;
import cz.muni.fi.pa165.facade.RegionalBranchFacade;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/branch")
public class RegionalBranchController {

    @Inject
    private RegionalBranchFacade branchFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<RegionalBranchDTO> getAllBranches(){
        List<RegionalBranchDTO> result = branchFacade.findAll();
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchDTO getRegionalBranch(@PathVariable("id") long branchId){
        RegionalBranchDTO result = branchFacade.findOne(branchId);
        if (result!= null) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult createPerson(@RequestBody RegionalBranchDTO branch) {
        RegionalBranchOperationResult result = branchFacade.createRegionalBranch(branch);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult updatePerson(@RequestBody RegionalBranchDTO branch) {
        RegionalBranchOperationResult result = branchFacade.updateRegionalBranch(branch);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final SimpleResult deleteRegionalBranch(@PathVariable("id") long branchId){
        SimpleResult result = branchFacade.deleteRegionalBranch(branchId);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/assignUser/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignUser(@PathVariable("id") long branchId, @RequestBody UserDTO user) {
        RegionalBranchOperationResult result = branchFacade.assignUser(branchId, user);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/assignCar/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignCar(@PathVariable("id") long branchId, @RequestBody CarDTO car) {
        RegionalBranchOperationResult result = branchFacade.assignCar(branchId, car);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = "/findAvailable", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarDTO> assignCar(@RequestBody RegionalBranchDTO branch, @RequestBody LocalDateTime day) {
        List<CarDTO> resultList = branchFacade.findAllAvailableCarsForBranch(branch, day);
        if (resultList != null && !resultList.isEmpty()){
            return resultList;
        } else {
            throw new ResourceNotFound();
        }
    }
}
