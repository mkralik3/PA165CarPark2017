package cz.muni.fi.pa165.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper dozer;

    @Override
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public  <T> T mapTo(Object u, Class<T> mapToClass)
    {
        return dozer.map(u,mapToClass);
    }

    @Override
    public Mapper getMapper(){
        return dozer;
    }

    @Override
    // Dozer does not support direct enum mapping
    public  <T extends Enum> T mapEnumTo(Enum u, Class<T> mapToClass) 
    {
        return (T)T.valueOf(mapToClass, u.name());
    }
}
