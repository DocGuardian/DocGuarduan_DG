package mhkif.yc.docguardian.controllers;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.services.InvitationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("doc_guardian/api/v1/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService service;

    @PutMapping("{id}")
    public ResponseEntity<HttpResponse> update(@RequestBody InvitationDto invitationDto) {
        InvitationDto res = service.update(invitationDto);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/invitations")
                        .status(HttpStatus.OK)
                        .message("Invitation has been updated successfully")
                        .developerMessage("Invitation has been updated successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<HttpResponse> getPagination(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "5") int size) {
        Page<InvitationDto> res = service.getAllPages(page, size);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/invitations/pages")
                        .status(HttpStatus.OK)
                        .message("Invitations Pages has been retrieved successfully")
                        .developerMessage("Invitations  Pages has been retrieved  successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<HttpResponse> get(@PathVariable UUID id) {

        InvitationDto res = service.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/rooms/:id")
                        .status(HttpStatus.OK)
                        .message("Invitation has been retrieved successfully")
                        .developerMessage("Invitation has been retrieved  successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }

    @GetMapping("")
    public ResponseEntity<HttpResponse> getAll() {
        List<InvitationDto> resList = service.getAll();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/rooms/pages")
                        .status(HttpStatus.OK)
                        .message("Invitations List has been retrieved successfully")
                        .developerMessage("Invitations List has been retrieved  successfully")
                        .data(Map.of("response", resList))
                        .build()
        );

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpResponse>  delete(@PathVariable UUID id) {
        boolean res = service.delete(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/invitations")
                        .status(HttpStatus.OK)
                        .message("Invitation has been deleted successfully")
                        .developerMessage("Invitation has been deleted successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }
}
