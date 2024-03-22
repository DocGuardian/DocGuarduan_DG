package mhkif.yc.docguardian.controllers;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.requests.SendInvitationRequest;
import mhkif.yc.docguardian.dtos.requests.SendMessageRequest;
import mhkif.yc.docguardian.entities.InvitationNotification;
import mhkif.yc.docguardian.entities.MessageNotification;
import mhkif.yc.docguardian.services.InvitationService;
import mhkif.yc.docguardian.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("doc_guardian/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final InvitationService invitationService;
    private final NotificationService service;

    @PostMapping("/invitations")
    public ResponseEntity<HttpResponse> sendInvitation(@RequestBody SendInvitationRequest request) {
        InvitationNotification notification =  service.sendInvitation(request.getSender().getId(), request.getRecipient().getId(), request.getRoom().getId(), request.getMessage());
        return  ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/notifications/invitations")
                        .status(HttpStatus.OK)
                        .message("Invitation notification has been sent successfully")
                        .developerMessage("Invitation notification has been sent successfully")
                        .data(Map.of("response", notification))
                        .build()
        );
    }

    @PostMapping("/messages")
    public ResponseEntity<HttpResponse> sendMessage(@RequestBody SendMessageRequest request) {
       MessageNotification notification = service.sendMessage(request.getSender().getId(), request.getRecipient().getId(),request.getRoom().getId(), request.getMessage());
        return  ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/notifications/messages")
                        .status(HttpStatus.OK)
                        .message("Message notification has been sent successfully")
                        .developerMessage("Message notification has been sent successfully")
                        .data(Map.of("response", notification))
                        .build()
        );    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpResponse>  delete(@PathVariable UUID id) {
        boolean res = service.delete(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/notifications")
                        .status(HttpStatus.OK)
                        .message("Notification has been deleted successfully")
                        .developerMessage("Notification has been deleted successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }
}
