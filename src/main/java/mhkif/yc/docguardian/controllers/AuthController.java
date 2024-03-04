package mhkif.yc.docguardian.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.requests.AuthReq;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.services.implementations.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("doc_guardian/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl service;
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

    @GetMapping("account-verification")
    public ResponseEntity<HttpResponse> confirmAccount(@RequestParam("token") String token) throws  Exception{
        Boolean isSuccess = service.verifyToken(token);
        if(!isSuccess){
            return ResponseEntity.internalServerError().body(
                    HttpResponse.builder()
                            .timeStamp(LocalDateTime.now().toString())
                            .status(HttpStatus.NOT_FOUND)
                            .message("Account Not Found")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .data(Map.of("Success",false ))
                            .build()
            );
        }
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .message("Account Verified")
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("Success",true ))
                        .build()
        );
    }

    @GetMapping("account-verification/re-send")
    public ResponseEntity<HttpResponse> reSendVerification(@RequestParam("token") String token) throws Exception {
        Boolean isSuccess = service.sendVerification(token);

        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .status(HttpStatus.OK)
                        .message("Account Verified has been sent")
                        .statusCode(HttpStatus.OK.value())
                        .data(Map.of("Success",isSuccess ))
                        .build()
        );
    }
}
