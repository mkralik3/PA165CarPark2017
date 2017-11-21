package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.contracts.UserDTO;
import cz.muni.fi.pa165.contracts.request.CreateUserRequest;
import cz.muni.fi.pa165.contracts.request.DeleteUserRequest;
import cz.muni.fi.pa165.contracts.request.UpdateUserRequest;
import cz.muni.fi.pa165.contracts.response.CreateUserResponse;
import cz.muni.fi.pa165.contracts.response.DeleteUserResponse;
import cz.muni.fi.pa165.contracts.response.UpdateUserResponse;
import cz.muni.fi.pa165.enums.UserErrorCode;
import cz.muni.fi.pa165.service.*;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martin Miskeje
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Inject
    private UserService userService;

    @Inject
    private ConversionServiceFromContracts conversionFrom;

    @Inject
    private ConversionServiceToContracts conversionTo;
    
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        if (request != null && request.getData() != null) {
            try {
                cz.muni.fi.pa165.entity.User user = conversionFrom.Convert(request.getData());
                Set<cz.muni.fi.pa165.service.errors.UserErrorCode> errors = userService.create(user, request.getPassword());
                if (errors.isEmpty()) {
                    response.setData(conversionTo.Convert(user));
                    response.setIsSuccess(true);
                }
                errors.forEach((e) -> {
                    response.getErrorCodes().add(conversionTo.Convert(e));
                });
            }
            catch (Exception ex) {
                response.getErrorCodes().add(UserErrorCode.UNKNOWN_ERROR);
                // todo log
            }
        }
        return response;
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isManager(UserDTO userDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserDTO> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserDTO findUserByUserName(String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
