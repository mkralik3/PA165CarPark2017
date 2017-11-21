package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.*;
import cz.muni.fi.pa165.enums.*;
import cz.muni.fi.pa165.service.errors.*;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
// Idea je, ze tu by mal byt prave pouzity automapper, ak to dava zmysel.
// Cielom je mat konverziu pod kontrolou a aby struktura kontraktu nepodliehala pravidlam automapera.
@Service
public class ConversionServiceToContracts {
    public cz.muni.fi.pa165.contracts.User Convert(User source){
        cz.muni.fi.pa165.contracts.User result = null;
        if (source != null) {
            result = new cz.muni.fi.pa165.contracts.User();
            result.setActivationDate(source.getActivationDate());
            result.setCreationDate(source.getCreationDate());
            result.setId(source.getId());
            result.setModificationDate(source.getModificationDate());
            result.setType(Convert(source.getType()));
            result.setUserName(source.getUserName());
        }
        return result;
    }
    
    public cz.muni.fi.pa165.contracts.UserType Convert(UserType source) {
        switch (source){
            case ADMIN:
                return cz.muni.fi.pa165.contracts.UserType.ADMIN;
            case BRANCH_MANAGER:
                return cz.muni.fi.pa165.contracts.UserType.BRANCH_MANAGER;
            default:
                return cz.muni.fi.pa165.contracts.UserType.USER;
        }
    }
    
    public cz.muni.fi.pa165.contracts.UserErrorCode Convert(UserErrorCode source){
        switch(source) {
            case DATABASE_ERROR:
                return cz.muni.fi.pa165.contracts.UserErrorCode.DATABASE_ERROR;
            case PASSWORD_LENGTH:
                return cz.muni.fi.pa165.contracts.UserErrorCode.PASSWORD_LENGTH;
            case PASSWORD_REQUIRED:
                return cz.muni.fi.pa165.contracts.UserErrorCode.PASSWORD_REQUIRED;
            case USERNAME_EXISTS:
                return cz.muni.fi.pa165.contracts.UserErrorCode.USERNAME_EXISTS;
            case USERNAME_REQUIRED:
                return cz.muni.fi.pa165.contracts.UserErrorCode.USERNAME_REQUIRED;
            default:
                return cz.muni.fi.pa165.contracts.UserErrorCode.UNKNOWN_ERROR;
        }
    }
}
