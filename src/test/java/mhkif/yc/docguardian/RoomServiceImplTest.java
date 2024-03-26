package mhkif.yc.docguardian;

import mhkif.yc.docguardian.dtos.requests.RoomReq;
import mhkif.yc.docguardian.dtos.responses.RoomRes;
import mhkif.yc.docguardian.entities.Room;
import mhkif.yc.docguardian.entities.User;
import mhkif.yc.docguardian.exceptions.NotFoundException;
import mhkif.yc.docguardian.repositories.InvitationRepository;
import mhkif.yc.docguardian.repositories.RoomRepository;
import mhkif.yc.docguardian.repositories.RoomUsersRepository;
import mhkif.yc.docguardian.repositories.UserRepository;
import mhkif.yc.docguardian.services.EmailService;
import mhkif.yc.docguardian.services.NotificationService;
import mhkif.yc.docguardian.services.implementations.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoomUsersRepository roomUsersRepository;

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private ModelMapper mapper;

    @Test
    public void testGetById_notFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roomService.getById(id));
    }

    @Test
    public void testGetById_success() {
        UUID id = UUID.randomUUID();
        Room room = new Room();
        room.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(room));

        RoomRes response = roomService.getById(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
    }


    @Test
    public void testGetAllPages() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Room> rooms = Arrays.asList(new Room(), new Room());
        Page<Room> roomPage = new PageImpl<>(rooms, pageRequest, 2); // Total 2 rooms
        when(repository.findAll(pageRequest)).thenReturn(roomPage);

        Page<RoomRes> response = roomService.getAllPages(page, size);

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        for (RoomRes roomRes : response) {
            assertNotNull(roomRes.getId());
            assertNotNull(roomRes.getName());
        }
    }

    @Test
    public void testCreate_notFoundOwner() {
        RoomReq request = new RoomReq();
        User owner = new User();
        owner.setId(UUID.randomUUID()); // Set a non-existent owner
        request.setOwner(owner);

        when(userRepository.findById(owner.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> roomService.create(request));
    }

}