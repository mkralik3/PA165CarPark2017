package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.dao.*;
import cz.muni.fi.pa165.entity.RegionalBranch;
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

}
