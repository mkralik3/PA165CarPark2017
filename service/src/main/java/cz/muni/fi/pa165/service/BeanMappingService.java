package cz.muni.fi.pa165.service;

import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;

import cz.muni.fi.pa165.dto.RegionalBranchDTO;
import cz.muni.fi.pa165.entity.RegionalBranch;

public interface BeanMappingService {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
    <T extends Enum> T mapEnumTo(Enum u, Class<T> mapToClass);
    Mapper getMapper();

    /**
     * Dozer for some reason doesn't convert sublist with objects
     */
    public RegionalBranch mapTo(RegionalBranchDTO input, Class<RegionalBranch> mapToClass);
    
    /**
     * Dozer for some reason doesn't convert sublist with objects
     */
    public RegionalBranchDTO mapTo(RegionalBranch input, Class<RegionalBranchDTO> mapToClass);
}
