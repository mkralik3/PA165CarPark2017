package cz.muni.fi.pa165.dto.results;

import java.util.HashSet;
import java.util.Set;

import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.dto.enums.RegionalBranchOperationErrorCode;

/**
 *
 * @author Martin Miskeje
 */
public class RegionalBranchOperationResult extends ResultWithData<RegionalBranchDTO> {
	private final Set<RegionalBranchOperationErrorCode> errorCodes = new HashSet<>();

    public Set<RegionalBranchOperationErrorCode> getErrorCodes() {
        return errorCodes;
    }
}
