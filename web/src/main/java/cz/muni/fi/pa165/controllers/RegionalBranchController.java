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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOG = LoggerFactory.getLogger(RegionalBranchController.class);

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
        LOG.debug("REST create branch: ", branch);
        return branchFacade.createRegionalBranch(branch);
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult updateBranch(@Valid @RequestBody RegionalBranchDTO branch,
                                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResourceNotValid();
        }
        LOG.debug("REST update branch: ", branch);

        return branchFacade.updateRegionalBranch(branch);
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteRegionalBranch(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId){
        try {
            branchFacade.deleteRegionalBranch(branchId);
        } catch (Exception ex) {
            LOG.warn(ex.getMessage());
            throw new ResourceNotFound();
        }
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ASSIGN_USER, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignUser(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @Valid @RequestBody UserDTO user,
                                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            LOG.debug("Resource not valid user:", user);
            throw new ResourceNotValid();
        }
        LOG.debug("Assign user : " + user + "  to the branch with id: " + branchId);
        return branchFacade.assignUser(branchId, user);
    }
    
    @RequestMapping(value = ApiDefinition.Branch.ASSIGN_CAR, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final RegionalBranchOperationResult assignCar(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @Valid @RequestBody CarDTO car,
                                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            LOG.debug("Resource not valid user:", car);
            throw new ResourceNotValid();
        }
        LOG.debug("Assign car : " + car + "  to the branch with id: " + branchId);
        return branchFacade.assignCar(branchId, car);
    }
    
    @RequestMapping(value = ApiDefinition.Branch.FIND_AVAILABLE, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CarDTO> findAvailable(@PathVariable(ApiDefinition.Branch.PATH_ID) long branchId, @RequestBody Map<String, LocalDateTime> date) {
        LocalDateTime day = date.get("date");
        LOG.debug("Find all available car in date: " + day + " for branch with id: ", branchId);
        RegionalBranchDTO branch = branchFacade.findOne(branchId);
        List<CarDTO> result = branchFacade.findAllAvailableCarsForBranch(branch, day);
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }
}
