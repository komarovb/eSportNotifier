package com.komarov.patel.research.methodology.esportservice.service;

import com.komarov.patel.research.methodology.esportservice.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
