package app.core.api;

import app.core.model.AuthRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    boolean authenticate(AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response);
}
