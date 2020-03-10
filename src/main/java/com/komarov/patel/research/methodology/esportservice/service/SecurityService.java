package com.komarov.patel.research.methodology.esportservice.service;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
