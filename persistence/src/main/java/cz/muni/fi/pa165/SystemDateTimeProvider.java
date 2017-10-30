/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 *
 * @author Martin Miškeje
 */
@Service
public class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public LocalDateTime provideNow() {
        return LocalDateTime.now();
    }
    
}
