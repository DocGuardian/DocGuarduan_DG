package mhkif.yc.docguardian.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.requests.UserReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("doc_guardian/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;
    private final ModelMapper mapper;

    @PostMapping("")
    public ResponseEntity<HttpResponse> save(@Valid @RequestBody RoomReq request){
        RoomRes room = service.create(request);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/rooms/save")
                        .status(HttpStatus.CREATED)
                        .message("Room has been created successfully")
                        .developerMessage("Room has been created successfully")
                        .data(Map.of("response", room))
                        .build()
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<RoomRes> update(@RequestBody RoomReq req, @PathVariable UUID id) {
        return ResponseEntity.ok(new RoomRes());
    }
    @GetMapping("/pages")
    public ResponseEntity<Page<RoomRes>> getPagination(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "5") int size){

        return ResponseEntity.ok(service.getAllPages(page, size));
    }

    @GetMapping("{id}")
    public ResponseEntity<RoomRes> get(@PathVariable UUID id){
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("")
    public ResponseEntity<List<RoomRes>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("{id}")
    void deleteUser(@PathVariable UUID id) {
    }
}
