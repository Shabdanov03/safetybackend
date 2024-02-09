package safetybackend.sefetybackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safetybackend.sefetybackend.api.AdminApi;
import safetybackend.sefetybackend.dto.response.SimpleResponse;
import safetybackend.sefetybackend.service.UserService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdminController implements AdminApi {

    private final UserService userService;

    @Override
    public SimpleResponse delete(Long id) {
        return userService.deleteById(id);
    }
}
