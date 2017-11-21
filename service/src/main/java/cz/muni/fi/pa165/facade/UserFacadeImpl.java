package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.contracts.*;
import cz.muni.fi.pa165.service.*;
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
    public CreateUserResponse CreateUser(CreateUserRequest request) {
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
    public UpdateUserResponse UpdateUser(UpdateUserRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DeleteUserResponse DeleteUser(DeleteUserRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
