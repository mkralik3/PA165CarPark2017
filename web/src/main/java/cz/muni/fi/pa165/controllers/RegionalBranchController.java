package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.facade.RegionalBranchFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

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
}
