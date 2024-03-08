package mhkif.yc.docguardian.services.implementations;

import lombok.RequiredArgsConstructor;
import mhkif.yc.docguardian.dtos.InvitationDto;
import mhkif.yc.docguardian.entities.*;
import mhkif.yc.docguardian.enums.RoomPermission;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.InvitationRepository;
import mhkif.yc.docguardian.repositories.RoomRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.InvitationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository repository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper mapper;

    @Override
    public InvitationDto getById(UUID id) {
        Optional<Invitation> invitationOp = repository.findById(id);
        return invitationOp.map(
                room -> mapper.map(room, InvitationDto.class)
        ).orElseThrow(() -> new NotFoundException("Invitation Not Exist with the given Id : " + id)
        );
    }

    @Override
    public Page<InvitationDto> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                room -> mapper.map(room, InvitationDto.class)
        );
    }

    @Override
    public List<InvitationDto> getAll() {
        return repository.findAll().stream().map(
                room -> mapper.map(room, InvitationDto.class)
        ).toList();
    }

    @Override
    public InvitationDto create(InvitationDto request) {
        User sender = userRepository.findById(request.getSender().getId()).orElseThrow(() -> new NotFoundException("Sender not found"));
        User recipient = userRepository.findById(request.getSender().getId()).orElseThrow(() -> new NotFoundException("Recipient not found"));
        Room room = roomRepository.findById(request.getRoom().getId()).orElseThrow(() -> new NotFoundException("Room not found"));
        Invitation invitation =   repository.save(mapper.map(request, Invitation.class));
        return mapper.map(invitation, InvitationDto.class);
    }

    @Override
    public InvitationDto update(InvitationDto room) {
        return null;
    }
}
