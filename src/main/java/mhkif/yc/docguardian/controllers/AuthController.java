package mhkif.yc.docguardian.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.requests.AuthReq;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.services.implementations.UserServiceImplementor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("doc_guardian/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImplementor service;
    private final ModelMapper mapper;

    @PostMapping("login")
    public ResponseEntity<HttpResponse> login(@Valid @RequestBody AuthReq request){
        User user = service.auth(request.getEmail(), request.getPassword());

        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .path("myrh/api/v1/auth")
                        .status(HttpStatus.ACCEPTED)
                        .message(user.getRole()+" has been authenticated")
                        .developerMessage(user.getRole()+" has been authenticated")
                        .data(Map.of("response", mapper.map(user, UserRes.class)))
                        .build()
        );
    }

    @PostMapping("register")
    public ResponseEntity<HttpResponse>  register(@Valid @RequestBody UserReq request){
        UserRes user = service.create(request);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/register")
                        .status(HttpStatus.CREATED)
                        .message("User has been created successfully")
                        .developerMessage("User has been created successfully")
                        .data(Map.of("response", user))
                        .build()
        );
    }
}