package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.contracts.UserDTO;
import cz.muni.fi.pa165.entity.User;
import cz.muni.fi.pa165.enums.UserType;
import cz.muni.fi.pa165.service.errors.UserErrorCode;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
// Idea je, ze tu by mal byt prave pouzity automapper, ak to dava zmysel.
// Cielom je mat konverziu pod kontrolou a aby struktura kontraktu nepodliehala pravidlam automapera.
@Service
public class ConversionServiceToContracts {
    public UserDTO Convert(User source){
        UserDTO result = null;
        if (source != null) {
            result = new UserDTO();
            result.setActivationDate(source.getActivationDate());
            result.setCreationDate(source.getCreationDate());
            result.setId(source.getId());
            result.setModificationDate(source.getModificationDate());
            result.setType(Convert(source.getType()));
            result.setUserName(source.getUserName());
        }
        return result;
    }
    
    public cz.muni.fi.pa165.enums.UserType Convert(UserType source) {
        switch (source){
            case ADMIN:
                return cz.muni.fi.pa165.enums.UserType.ADMIN;
            case BRANCH_MANAGER:
                return cz.muni.fi.pa165.enums.UserType.BRANCH_MANAGER;
            default:
                return cz.muni.fi.pa165.enums.UserType.USER;
        }
    }
    
    public cz.muni.fi.pa165.enums.UserErrorCode Convert(UserErrorCode source){
        switch(source) {
            case DATABASE_ERROR:
                return cz.muni.fi.pa165.enums.UserErrorCode.DATABASE_ERROR;
            case PASSWORD_LENGTH:
                return cz.muni.fi.pa165.enums.UserErrorCode.PASSWORD_LENGTH;
            case PASSWORD_REQUIRED:
                return cz.muni.fi.pa165.enums.UserErrorCode.PASSWORD_REQUIRED;
            case USERNAME_EXISTS:
                return cz.muni.fi.pa165.enums.UserErrorCode.USERNAME_EXISTS;
            case USERNAME_REQUIRED:
                return cz.muni.fi.pa165.enums.UserErrorCode.USERNAME_REQUIRED;
            default:
                return cz.muni.fi.pa165.enums.UserErrorCode.UNKNOWN_ERROR;
        }
    }
}
