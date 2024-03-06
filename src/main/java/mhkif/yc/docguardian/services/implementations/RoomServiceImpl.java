package mhkif.yc.docguardian.services.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.RoomUsers;
import mhkif.yc.docguardian.entities.RoomUsersId;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.enums.Permission;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.*;
import mhkif.yc.docguardian.services.RoomService;
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
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final UserRepository userRepository;
    private final RoomUsersRepository roomUsersRepository;
    private  final ModelMapper mapper;

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
        roomUsers.setPermission(Permission.ADMIN);
        roomUsers.setExpiredAt(LocalDateTime.now().plusDays(10));
        roomUsers.setCreatedAt(LocalDateTime.now());

        roomUsersRepository.save(roomUsers);


        return mapper.map(savedRoom, RoomRes.class);
    }

    @Override
    public RoomRes update(RoomRes room) {
        return null;
    }
}
