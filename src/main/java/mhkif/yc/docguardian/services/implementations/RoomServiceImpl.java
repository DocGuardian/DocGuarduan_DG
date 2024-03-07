package mhkif.yc.docguardian.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.dtos.responses.UserRes;
import mhkif.yc.docguardian.entities.*;
import mhkif.yc.docguardian.enums.RoomPermission;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.*;
import mhkif.yc.docguardian.services.EmailService;
import mhkif.yc.docguardian.services.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final UserRepository userRepository;
    private final RoomUsersRepository roomUsersRepository;
    private final InvitationRepository invitationRepository;
    private final EmailService emailService;
    private  final ModelMapper mapper;

    @Value("${spring.mail.properties.verify.host}")
    private String host;

    @Override
    public RoomRes getById(UUID id) {
        Optional<Room> roomOp = repository.findById(id);
        return roomOp.map(
                room -> mapper.map(room, RoomRes.class)
        ).orElseThrow(() -> new NotFoundException("Room Not Exist with the given Id : " + id)
        );
    }

    @Override
    public Page<RoomRes> getAllPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                room -> mapper.map(room, RoomRes.class)
        );
    }

    @Override
    public List<RoomRes> getAll() {
        return repository.findAll().stream().map(
                room -> mapper.map(room, RoomRes.class)
        ).toList();
    }

    @Override
    public RoomRes create(RoomReq request) {
        User owner = userRepository.findById(request.getOwner().getId()).orElseThrow(() -> new NotFoundException("Owner not found"));
        Room room = mapper.map(request, Room.class);
        room.setOwner(owner);
        room.setStorage(0);
        room.setCreatedAt(LocalDateTime.now());
        room.getUsers().add(owner);
        Room savedRoom = repository.save(room);

        RoomUsersId roomUsersId = new RoomUsersId();
        roomUsersId.setRoom_id(savedRoom.getId());
        roomUsersId.setUser_id(savedRoom.getOwner().getId());

        RoomUsers roomUsers = new RoomUsers();
        roomUsers.setId(roomUsersId);
        roomUsers.setPermission(RoomPermission.ADMIN);
        roomUsers.setExpiredAt(LocalDateTime.now().plusDays(10));
        roomUsers.setCreatedAt(LocalDateTime.now());

        roomUsersRepository.save(roomUsers);


        return mapper.map(savedRoom, RoomRes.class);
    }

    @Override
    public RoomRes update(RoomRes room) {
        return null;
    }

    @Override
    public RoomRes inviteUserViaEmail(UUID id, UUID userId, String recipientEmail) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Room room = repository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
        if(recipientEmail == null || recipientEmail.isEmpty()){
           throw  new NotFoundException("Recipient email not found");
        }

        String name = user.getFirst_name()+ " "+ user.getLast_name();
        String url = "http://localhost:8080/doc_guardian/api/v1/rooms/"+id;
        String subject = "DocGuardian : Room Invitation ";
        String body = invitationEmailMessage(name, url, this.host);
        emailService.sendSimpleMailMessage(name, user.getEmail(), recipientEmail, subject, body);


        return mapper.map(room, RoomRes.class);
    }

    @Override
    public RoomRes inviteUser(UUID id, UUID userId, UUID recipientId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        User recipient = userRepository.findById(recipientId).orElseThrow(() -> new NotFoundException("Recipient not found"));
        Room room = repository.findById(id).orElseThrow(() -> new NotFoundException("Room not found"));
        Invitation invitation = new Invitation();
        invitation.setRoom(room);
        invitation.setSender(user);
        invitation.setRecipient(recipient);
        invitation.setCreatedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        return mapper.map(room, RoomRes.class);
    }

    private String invitationEmailMessage(String name, String url, String host) {
        return "Hello , \n\n" +
                "You have been invited by "+name+"to a virtual room in DocGuardian ," +
                " Please click the link below to see the invitation  . \n\n" +
                url +" \n\n" +
                "The Support Team DocGuardian .";
    }
}
