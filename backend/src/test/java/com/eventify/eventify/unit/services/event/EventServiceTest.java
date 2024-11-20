package com.eventify.eventify.unit.services.event;

import com.eventify.eventify.dto.event.EventListResponse;
import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventHeader;
import com.eventify.eventify.models.event.EventOrganization;
import com.eventify.eventify.models.event.EventStageEnum;
import com.eventify.eventify.port.dao.event.EventDao;
import com.eventify.eventify.port.service.account.AccountService;
import com.eventify.eventify.port.service.event.management.ManagementService;
import com.eventify.eventify.port.service.event.participate.ParticipateService;
import com.eventify.eventify.port.service.gcp.GcpStorageService;
import com.eventify.eventify.services.event.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceTest {
    @Mock
    private EventDao eventDao;

    @Mock
    private AccountService accountService;

    @Mock
    private GcpStorageService gcpStorageService;

    @Mock
    private ParticipateService participateService;

    @Mock
    private ManagementService managementService;

    @InjectMocks
    private EventServiceImpl eventService;

    Account account = new Account(
            1,
            "username",
            "email",
            null,
            true
    );

    @BeforeEach
    void setUp() {
        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            // Initialization code if needed
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(accountService.getAccountRequest()).thenReturn(account);
    }

    // Criar evento com dados validos
    @Test
    public void testCreateEvent_withValidData_createsEventSuccessfully() {
        // FIXTURE
        String eventName = "Event Name";
        String eventDescription = "Event Description";
        ZonedDateTime dateStart = ZonedDateTime.now();
        ZonedDateTime dateEnd = ZonedDateTime.now().plusDays(1);
        MultipartFile imageData = mock(MultipartFile.class);
        String localName = "Local Name";
        String cepAddress = "12345";
        String stateAddress = "State";
        String cityAddress = "City";
        String neighborhoodAddress = "Neighborhood";
        String numberAddress = "123";
        String streetAddress = "Street";
        String complementAddress = "Complement";
        String lat = "0.0";
        String lng = "0.0";
        String pixKey = "PIX_VALUE_TEST";

        when(eventDao.partiallySave(any(Event.class))).thenReturn(1);

        // EXERCISE
        int eventId = eventService.createEvent(
                eventName, eventDescription, dateStart, dateEnd, imageData, localName, cepAddress, stateAddress,
                cityAddress, neighborhoodAddress, numberAddress, streetAddress, complementAddress, lat, lng, pixKey
        );

        //ASSERT
        assertEquals(1, eventId);
    }

    // Criar evento com dados obrigatorios vazios
    @Test
    public void testCreateEvent_blankValues_error() {
        // FIXTURE
        String eventName = null;
        String eventDescription = null;
        ZonedDateTime dateStart = null;
        ZonedDateTime dateEnd = null;
        MultipartFile imageData = mock(MultipartFile.class);
        String localName = "Local Name";
        String cepAddress = "12345";
        String stateAddress = "State";
        String cityAddress = "City";
        String neighborhoodAddress = "Neighborhood";
        String numberAddress = "123";
        String streetAddress = "Street";
        String complementAddress = "Complement";
        String lat = "0.0";
        String lng = "0.0";
        String pixKey = "PIX_VALUE_TEST";

        //EXERCISE
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent(
                    eventName, eventDescription, dateStart, dateEnd, imageData, localName, cepAddress, stateAddress,
                    cityAddress, neighborhoodAddress, numberAddress, streetAddress, complementAddress, lat, lng, pixKey
            );
        });

        //ASSERT
        assertEquals("Invalid arguments", exception.getMessage());
    }


    @Test
    public void testCreateEvent_withOnlyRequiredData_createsEventSuccessfully() {
        // FIXTURE
        String eventName = "Event Name";
        String eventDescription = "Event Description";
        ZonedDateTime dateStart = ZonedDateTime.now();
        ZonedDateTime dateEnd = ZonedDateTime.now().plusDays(1);
        MultipartFile imageData = null;
        String localName = null;
        String cepAddress = null;
        String stateAddress = null;
        String cityAddress = null;
        String neighborhoodAddress = null;
        String numberAddress = null;
        String streetAddress = null;
        String complementAddress = null;
        String lat = null;
        String lng = null;
        String pixKey = null;

        when(eventDao.partiallySave(any(Event.class))).thenReturn(1);

        // EXERCISE
        int eventId = eventService.createEvent(
                eventName, eventDescription, dateStart, dateEnd, imageData, localName, cepAddress, stateAddress,
                cityAddress, neighborhoodAddress, numberAddress, streetAddress, complementAddress, lat, lng, pixKey
        );

        //ASSERT
        assertEquals(1, eventId);
    }

    @Test
    public void testListPaginatedFromUser_userWithEvent_listOfEventFromUser() {
        // FIXTURE
        int limit = 10;
        int offset = 0;

        List<EventHeader> eventList = new ArrayList<>();
        EventHeader eventHeader = new EventHeader(
                1,
                "Event Name",
                "Event Description",
                ZonedDateTime.now().plusDays(1),
                ZonedDateTime.now().plusDays(2),
                EventStageEnum.CREATED,
                null
        );
        eventList.add(eventHeader);

        when(accountService.getAccountRequest()).thenReturn(account);
        when(
                eventDao.listPaginatedHeaderFromUser(
                       limit, offset, account.getId()
                )).thenReturn(eventList);
        when(
                eventDao.totalFromUser(account.getId())
        ).thenReturn(1);

        // EXERCISE
        EventListResponse response = eventService.
                listPaginatedFromUser(limit, offset);

        // ASSERT
        assertEquals(eventList, response.events());
        assertEquals(1, response.total());
    }

    @Test
    public void testListPaginatedFromUser_userWithoutEvent_noneEvent() {
        // FIXTURE
        int limit = 10;
        int offset = 0;
        List<EventHeader> eventList = new ArrayList<>();

        when(accountService.getAccountRequest()).thenReturn(account);
        when(
                eventDao.listPaginatedHeaderFromUser(
                        limit, offset, account.getId()
                )).thenReturn(eventList);
        when(
                eventDao.totalFromUser(account.getId())
        ).thenReturn(0);

        // EXERCISE
        EventListResponse response = eventService.
                listPaginatedFromUser(limit, offset);

        // ASSERT
        assertEquals(eventList, response.events());
        assertEquals(0, response.total());
    }

    @Test
    public void testGetOrganizationById_eventExists_eventOrganizationInfo() {
        // FIXTURE
        int eventId = 1;
        EventOrganization eventOrganization = new EventOrganization(
          1,
          ZonedDateTime.now(),
            "username",
            10,
            EventStageEnum.CREATED
        );

        when(eventDao.hasAccessToEvent(eventId, account.getId())).thenReturn(true);
        when(eventDao.getOrganizationById(eventId)).thenReturn(eventOrganization);

        // EXERCISE
        EventOrganization response = eventService.getOrganizationById(eventId);

        // ASSERT
        assertEquals(eventOrganization, response);
    }

    @Test
    public void testGetOrganizationById_userDoNotHasAccessToEvent_Exception() {
        // FIXTURE
        int eventId = 1;
        EventOrganization eventOrganization = new EventOrganization(
                1,
                ZonedDateTime.now(),
                "username",
                10,
                EventStageEnum.CREATED
        );

        when(eventDao.hasAccessToEvent(eventId, account.getId())).thenReturn(false);
        when(eventDao.getOrganizationById(eventId)).thenReturn(eventOrganization);

        // EXERCISE
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.getOrganizationById(eventId);
        });

        // ASSERT
        assertEquals("User does not have access to event", exception.getMessage());
    }

    @Test
    public void testUpdateEvent_validInformations_Succsess() {
        // FIXTURE
        String eventName = "Event Name";
        String eventDescription = "Event Description";
        ZonedDateTime dateStart = ZonedDateTime.now();
        ZonedDateTime dateEnd = ZonedDateTime.now().plusDays(1);
        MultipartFile imageData = mock(MultipartFile.class);
        String localName = "Local Name";
        String cepAddress = "12345";
        String stateAddress = "State";
        String cityAddress = "City";
        String neighborhoodAddress = "Neighborhood";
        String numberAddress = "123";
        String streetAddress = "Street";
        String complementAddress = "Complement";
        String lat = "0.0";
        String lng = "0.0";
        String pixKey = "PIX_VALUE_TEST";

        Event event = new Event(
                1,
                eventName,
                eventDescription,
                ZonedDateTime.now(),
                localName,
                cepAddress,
                stateAddress,
                cityAddress,
                neighborhoodAddress,
                numberAddress,
                streetAddress,
                complementAddress,
                0.0,
                0.0,
                dateStart,
                dateEnd,
                EventStageEnum.CREATED,
                pixKey
        );

        when(accountService.getAccountRequest()).thenReturn(account);
        when(eventDao.hasAccessToEvent(1, account.getId())).thenReturn(true);
        when(eventDao.readById(1)).thenReturn(event);

        // EXERCISE
        eventService.updateEvent(
                1,
                eventName, eventDescription, dateStart, dateEnd, imageData, localName, cepAddress, stateAddress,
                cityAddress, neighborhoodAddress, numberAddress, streetAddress, complementAddress, lat, lng, pixKey
        );
    }

    @Test
    public void testUpdateEvent_invalidInformations_error() {
        // FIXTURE
        String eventName = null;
        String eventDescription = "Event Description";
        ZonedDateTime dateStart = ZonedDateTime.now();
        ZonedDateTime dateEnd = ZonedDateTime.now().plusDays(1);
        MultipartFile imageData = mock(MultipartFile.class);
        String localName = "Local Name";
        String cepAddress = "12345";
        String stateAddress = "State";
        String cityAddress = "City";
        String neighborhoodAddress = "Neighborhood";
        String numberAddress = "123";
        String streetAddress = "Street";
        String complementAddress = "Complement";
        String lat = "0.0";
        String lng = "0.0";
        String pixKey = "PIX_VALUE_TEST";

        Event event = new Event(
                1,
                eventName,
                eventDescription,
                ZonedDateTime.now(),
                localName,
                cepAddress,
                stateAddress,
                cityAddress,
                neighborhoodAddress,
                numberAddress,
                streetAddress,
                complementAddress,
                0.0,
                0.0,
                dateStart,
                dateEnd,
                EventStageEnum.CREATED,
                pixKey
        );

        when(accountService.getAccountRequest()).thenReturn(account);
        when(eventDao.hasAccessToEvent(1, account.getId())).thenReturn(true);
        when(eventDao.readById(1)).thenReturn(event);

        // EXERCISE
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            eventService.updateEvent(
                    1,
                    eventName, eventDescription, dateStart, dateEnd, imageData, localName, cepAddress, stateAddress,
                    cityAddress, neighborhoodAddress, numberAddress, streetAddress, complementAddress, lat, lng, pixKey
            );
        });

        //ASSERT
        assertEquals("Invalid arguments", exception.getMessage());
    }

    @Test
    public void testDeleteEvent_validInformations_succsess() {
        // FIXTURE
        int eventId = 1;

        when(accountService.getAccountRequest()).thenReturn(account);
        when(eventDao.hasAccessToEvent(1, account.getId())).thenReturn(true);

        // EXERCISE
        eventService.deleteEvent(eventId);
    }
}