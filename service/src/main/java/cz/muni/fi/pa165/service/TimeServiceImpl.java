package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeServiceImpl implements TimeService{

    @Override
    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

}

