package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.config.ApiDefinition;
import cz.muni.fi.pa165.dto.UserAuthDTO;
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.results.UserOperationResult;
import cz.muni.fi.pa165.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(ApiDefinition.User.BASE)
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Inject
    private UserFacade userFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDTO> getAllUsers(){
        List<UserDTO> result = userFacade.findAllUsers();
        if(result == null) {
            result = Collections.emptyList();
        }
        return result;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserOperationResult authenticateUser(@RequestBody UserAuthDTO user) {
        return userFacade.authenticate(user);
    }
}
