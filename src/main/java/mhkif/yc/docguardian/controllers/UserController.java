package mhkif.yc.docguardian.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.dtos.NotificationDto;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.services.InvitationService;
import mhkif.yc.docguardian.services.NotificationService;
import mhkif.yc.docguardian.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("doc_guardian/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService service;
    private final InvitationService invitationService;
    private final NotificationService notificationService;


    @PostMapping("")
    public ResponseEntity<HttpResponse> save(@Valid @RequestBody UserReq request){
        UserRes user = service.create(request);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/save")
                        .status(HttpStatus.CREATED)
                        .message("User has been created successfully")
                        .developerMessage("User has been created successfully")
                        .data(Map.of("response", user))
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<UserRes>  update(@RequestBody UserReq req, @PathVariable UUID id) {
        return ResponseEntity.ok(new UserRes());
    }
    @GetMapping("/pages")
    public ResponseEntity<Page<UserRes>> getPagination(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "5") int size){

        return ResponseEntity.ok(service.getAllPages(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRes> get(@PathVariable UUID id){
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("search-by")
    public ResponseEntity<HttpResponse> getByEmail(@RequestParam() String email){
        UserRes user= service.getByEmail(email);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/email")
                        .status(HttpStatus.OK)
                        .message("User By Email has been retrieved successfully")
                        .developerMessage("User By Email has been retrieved successfully")
                        .data(Map.of("response", user))
                        .build()
        );
    }

    @GetMapping("{id}/invitations")
    public ResponseEntity<HttpResponse> getInvitations(@PathVariable UUID id){
        List<InvitationDto> invitations = service.getInvitations(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/users/invitations")
                        .status(HttpStatus.CREATED)
                        .message("User's invitations has been retrieved successfully")
                        .developerMessage("User's invitations has been retrieved successfully")
                        .data(Map.of("response", invitations))
                        .build()
        );
    }

    @PostMapping("{id}/invitations/{inv_id}")
    public ResponseEntity<HttpResponse> updateInvitation(@RequestBody @Valid InvitationDto invitation){
        InvitationDto invitations = invitationService.create(invitation);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/users/invitations")
                        .status(HttpStatus.CREATED)
                        .message("User's invitation has been updated successfully")
                        .developerMessage("User's invitations has been updated successfully")
                        .data(Map.of("response", invitations))
                        .build()
        );
    }

    @GetMapping("{id}/notifications")
    public ResponseEntity<HttpResponse> getNotifications(@PathVariable UUID id){
        List<NotificationDto> notifications = notificationService.getNotificationsForUser(id);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/users/{id}/notifications")
                        .status(HttpStatus.CREATED)
                        .message("User's notifications has been retrieved successfully")
                        .developerMessage("User's notifications has been retrieved successfully")
                        .data(Map.of("response", notifications))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<List<UserRes>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("{id}")
    void deleteUser(@PathVariable UUID id) {
    }
}
