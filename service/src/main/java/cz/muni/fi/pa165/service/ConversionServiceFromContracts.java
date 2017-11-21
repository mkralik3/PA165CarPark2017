package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.contracts.*;
import cz.muni.fi.pa165.enums.UserType;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
// Idea je, ze tu by mal byt prave pouzity automapper, ak to dava zmysel.
// Cielom je mat konverziu pod kontrolou a aby struktura kontraktu nepodliehala pravidlam automapera.
@Service
public class ConversionServiceFromContracts {
    
    public cz.muni.fi.pa165.entity.User Convert(UserDTO source){
        cz.muni.fi.pa165.entity.User result = null;
        if (source != null) {
            result = new cz.muni.fi.pa165.entity.User();
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
}
