package service;

import dataaccess.MemoryAuthDAO;

public class AuthService {

    private MemoryAuthDAO memoryAuthDAO;

    public AuthService(MemoryAuthDAO memoryAuthDAO) {
        this.memoryAuthDAO = memoryAuthDAO;
    }

    public void clearAuth() {
        memoryAuthDAO.clearAuth();
    }
}
