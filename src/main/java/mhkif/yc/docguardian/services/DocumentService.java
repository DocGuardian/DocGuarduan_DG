package mhkif.yc.docguardian.services;

import mhkif.yc.docguardian.dtos.requests.DocumentReq;
import mhkif.yc.docguardian.dtos.responses.DocumentRes;
import mhkif.yc.docguardian.entities.Document;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DocumentService extends Service<Document, UUID, DocumentReq, DocumentRes>{
    Page<DocumentRes> getPagesByUser(int page, int size, UUID userId);

    boolean delete(UUID id);
}
