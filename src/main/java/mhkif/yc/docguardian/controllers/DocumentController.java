package mhkif.yc.docguardian.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.HttpResponse;
import mhkif.yc.docguardian.dtos.requests.DocumentReq;
import mhkif.yc.docguardian.dtos.responses.DocumentRes;
import mhkif.yc.docguardian.services.DocumentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("doc_guardian/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;


    @PostMapping("")
    public ResponseEntity<HttpResponse> save(@Valid @ModelAttribute DocumentReq request){
        DocumentRes doc = service.create(request);
        return ResponseEntity.accepted().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.CREATED.value())
                        .path("doc_guardian/api/v1/documents/save")
                        .status(HttpStatus.CREATED)
                        .message("Document has been created successfully")
                        .developerMessage("Document has been created successfully")
                        .data(Map.of("response", doc))
                        .build()
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<HttpResponse> getPagination(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false,defaultValue = "5") int size){
        Page<DocumentRes> docPages = service.getAllPages(page, size);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/documents/pages")
                        .status(HttpStatus.OK)
                        .message("Doc Pages has been retrieved successfully")
                        .developerMessage("Doc  Pages has been retrieved  successfully")
                        .data(Map.of("response", docPages))
                        .build()
        );
    }


    @GetMapping("{id}")
    public ResponseEntity<HttpResponse> get(@PathVariable UUID id){

        DocumentRes docRes =  service.getById(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/documents/:id")
                        .status(HttpStatus.OK)
                        .message("Document has been retrieved successfully")
                        .developerMessage("Document has been retrieved successfully")
                        .data(Map.of("response", docRes))
                        .build()
        );
    }
    @GetMapping("")
    public ResponseEntity<HttpResponse> getAll(){
        List<DocumentRes> docList = service.getAll();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/documents/pages")
                        .status(HttpStatus.OK)
                        .message("Doc List has been retrieved successfully")
                        .developerMessage("Doc List has been retrieved successfully")
                        .data(Map.of("response", docList))
                        .build()
        );

    }

    @DeleteMapping("{id}")
   public ResponseEntity<HttpResponse> deleteUser(@PathVariable UUID id) {
        boolean res = service.delete(id);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().toString())
                        .statusCode(HttpStatus.OK.value())
                        .path("doc_guardian/api/v1/documents")
                        .status(HttpStatus.OK)
                        .message("Doc has been deleted successfully")
                        .developerMessage("Doc has been deleted successfully")
                        .data(Map.of("response", res))
                        .build()
        );
    }
}
