package app.core.controller;

import app.core.JsonHelper;
import app.core.model.AuthRequest;
import app.core.service.AuthServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private final JsonHelper jsonHelper;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> login(@Valid @RequestBody AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        boolean isAuthSuccessful = authService.authenticate(authRequest, request, response);
        return isAuthSuccessful ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body(jsonHelper.createJson(Map.of("message","Неправильные имя пользователя или пароль")));

    }

    //TODO: убрать нахер отсюда
    @PostMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> testAuth() {
        return ResponseEntity.ok().body(jsonHelper.createJson(Map.of("message","Тестовый эндпоинт")));
    }

    //TODO: добавить рест для регистрации (удаления??) пользователя

}
