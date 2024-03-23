package mhkif.yc.docguardian.services.implementations;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.requests.DocumentReq;
import mhkif.yc.docguardian.dtos.responses.DocumentRes;
import mhkif.yc.docguardian.entities.Document;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.DocumentRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public DocumentRes getById(UUID id) {
       Optional<Document> document = repository.findById(id);
        return document.map(
                room -> mapper.map(room, DocumentRes.class)
        ).orElseThrow(() -> new NotFoundException("Document Not Exist with the given Id : " + id)
        );
    }

    @Override
    public Page<DocumentRes> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                document -> mapper.map(document, DocumentRes.class)
        );
    }

    @Override
    public List<DocumentRes> getAll() {
        return repository.findAll().stream().map(
                document -> mapper.map(document, DocumentRes.class)
        ).toList();
    }

    @Override
    public Page<DocumentRes> getPagesByUser(int page, int size, UUID userId) {
        User sender = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Doc sender not found"));
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAllBySender(pageRequest, sender).map(
                doc -> mapper.map(doc, DocumentRes.class)
        );
    }

    @Override
    public DocumentRes create(DocumentReq request) {
        User owner = userRepository.findById(request.getSender().getId()).orElseThrow(() -> new NotFoundException("User sender not found"));
        Document document = repository.save(mapper.map(request, Document.class));

        return mapper.map(document, DocumentRes.class);
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
