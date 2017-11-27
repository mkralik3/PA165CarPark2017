package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface TimeService {
    public LocalDateTime getCurrentTime();
}
