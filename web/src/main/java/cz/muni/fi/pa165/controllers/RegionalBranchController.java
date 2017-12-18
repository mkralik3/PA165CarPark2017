package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.config.ApiDefinition;
import cz.muni.fi.pa165.dto.CarDTO;
import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.RegionalBranchOperationResult;
import cz.muni.fi.pa165.dto.results.SimpleResult;
import cz.muni.fi.pa165.exceptions.ResourceNotFound;
import cz.muni.fi.pa165.exceptions.ResourceNotValid;
import cz.muni.fi.pa165.facade.RegionalBranchFacade;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(ApiDefinition.Branch.BASE)
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
    
    @RequestMapping(value = ApiDefinition.Branch.ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchDTO getRegionalBranch(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId){
        RegionalBranchDTO result = branchFacade.findOne(branchId);
        if (result!= null) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult createBranch(@Valid @RequestBody RegionalBranchDTO branch,
                                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        RegionalBranchOperationResult result = branchFacade.createRegionalBranch(branch);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult updateBranch(@Valid @RequestBody RegionalBranchDTO branch,
                                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        RegionalBranchOperationResult result = branchFacade.updateRegionalBranch(branch);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final SimpleResult deleteRegionalBranch(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId){
        SimpleResult result = branchFacade.deleteRegionalBranch(branchId);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ASSIGN_USER, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignUser(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @Valid @RequestBody UserDTO user,
                                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }

        RegionalBranchOperationResult result = branchFacade.assignUser(branchId, user);
        if (result.getIsSuccess()) {
            return result;
        } else {
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ASSIGN_CAR, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignCar(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @Valid @RequestBody CarDTO car,
                                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        RegionalBranchOperationResult result = branchFacade.assignCar(branchId, car);
        if (result.getIsSuccess()) {
            return result;
        } else {
            result.
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = ApiDefinition.Branch.FIND_AVAILABLE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarDTO> assignCar(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @RequestBody Map<String, LocalDateTime> date) {
        LocalDateTime day = date.get("date");
        RegionalBranchDTO branch = branchFacade.findOne(branchId);
        List<CarDTO> resultList = branchFacade.findAllAvailableCarsForBranch(branch, day);
        if(resultList == null) {
            result = Collections.emptyList();
        }
        return result;
    }
}
