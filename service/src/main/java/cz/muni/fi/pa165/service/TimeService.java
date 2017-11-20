package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface TimeService {
    public Date getCurrentTime();
}
