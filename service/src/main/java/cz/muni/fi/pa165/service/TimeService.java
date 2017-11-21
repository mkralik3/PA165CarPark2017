package cz.muni.fi.pa165.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public interface TimeService {
    public LocalDateTime getCurrentTime();
}
