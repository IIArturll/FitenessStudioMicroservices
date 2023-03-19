package it.academy.userservice.services.api;

public interface IMailService {
    void send(String mail);
    boolean checkVerification(String mail,String code);
}
