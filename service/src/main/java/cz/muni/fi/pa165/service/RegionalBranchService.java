package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.entity.RegionalBranch;
import java.util.List;


/**
 *
 * @author Martin Miskeje
 */
public interface RegionalBranchService {
    void create(RegionalBranch regionalBranch);
    
    void update(RegionalBranch regionalBranch);

    void delete(long id);

    List<RegionalBranch> findAll();

    RegionalBranch findOne(long id);
}
