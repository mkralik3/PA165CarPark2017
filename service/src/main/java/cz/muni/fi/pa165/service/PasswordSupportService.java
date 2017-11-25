package cz.muni.fi.pa165.service;

/**
 *
 * @author Martin Miskeje
 */
public interface PasswordSupportService {
    String createHash(String password);
    boolean validatePassword(String password, String correctHash);
}