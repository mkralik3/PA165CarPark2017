package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.RegionalBranch;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miskeje
 */
@Service
public class RegionalBranchServiceImpl implements RegionalBranchService {

    @Inject
    private RegionalBranchDAO regionalBranchDao;
    @Inject
    private TimeService timeService;
    
    @Override
    public void create(RegionalBranch regionalBranch) {
        if (regionalBranch == null) {
            throw new NullPointerException("regionalBranch");
        }
        regionalBranch.setCreationDate(timeService.getCurrentTime());
        regionalBranch.setModificationDate(timeService.getCurrentTime());
        regionalBranchDao.save(regionalBranch);
    }

    @Override
    public void update(RegionalBranch regionalBranch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RegionalBranch> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RegionalBranch findOne(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
