package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.exceptions.ContactException;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContactServiceImplTests {
    @InjectMocks
    ContactServiceImpl contactService;

    @Mock
    ContactRepository contactRepository;

    @Mock
    ContactMapper contactMapper;

    @Captor
    ArgumentCaptor<List<Contact>> contactListArgumentCaptor;

    @Captor
    ArgumentCaptor<Contact> contactArgumentCaptor;

    @Captor
    ArgumentCaptor<ContactDto> contactDtoArgumentCaptor;

    @Test
    void saveOrUpdate_throwsError_givenNull() {
        when(contactRepository.save(null)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.saveOrUpdate(null);
        });
    }

    @Test
    void saveOrUpdate_returnsContactDto_givenNewContactDto() {
        Contact contact = Contact.builder()
                .id(null)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        Contact savedContact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(null)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto savedContactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        when(contactMapper.dtoToContact(contactDto)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(savedContact);
        when(contactMapper.contactToDto(savedContact)).thenReturn(contactDto);
        when(contactMapper.contactToDto(savedContact)).thenReturn(savedContactDto);

        assertEquals(savedContactDto, contactService.saveOrUpdate(contactDto));
    }

    @Test
    void saveOrUpdate_returnsContactDto_givenExistingContactDto() {
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();
        when(contactMapper.dtoToContact(contactDto)).thenReturn(contact);
        when(contactMapper.contactToDto(contact)).thenReturn(contactDto);
        when(contactRepository.save(contact)).thenReturn(contact);

        assertEquals(contactDto, contactService.saveOrUpdate(contactDto));
    }

    @Test
    void saveList_returnsListContactDto_givenListOfContactDtos(){
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        List<ContactDto> contactDtoList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();
        contactDtoList.add(contactDto);
        contactList.add(contact);


        when(contactMapper.dtoToContact(contactDto)).thenReturn(contact);
        when(contactMapper.contactToDto(contact)).thenReturn(contactDto);
        when(contactRepository.saveAll(contactList)).thenReturn(contactList);

        assertEquals(contactDtoList, contactService.saveList(contactDtoList));
        Mockito.verify(contactRepository, Mockito.times(1)).saveAll(contactListArgumentCaptor.capture());
        Mockito.verify(contactMapper, Mockito.times(1)).contactToDto(contactArgumentCaptor.capture());
        Mockito.verify(contactMapper, Mockito.times(1)).dtoToContact(contactDtoArgumentCaptor.capture());
    }

    @Test
    void saveList_returnsListContactDtos_givenMultipleContactDtos(){
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        Contact contact2 = Contact.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contact2Dto = ContactDto.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        List<ContactDto> contactDtoList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();

        contactDtoList.add(contactDto);
        contactDtoList.add(contact2Dto);

        contactList.add(contact);
        contactList.add(contact2);

        when(contactMapper.dtoToContact(contactDto)).thenReturn(contact);
        when(contactMapper.dtoToContact(contact2Dto)).thenReturn(contact2);
        when(contactMapper.contactToDto(contact)).thenReturn(contactDto);
        when(contactMapper.contactToDto(contact2)).thenReturn(contact2Dto);

        when(contactRepository.saveAll(contactList)).thenReturn(contactList);

        assertEquals(contactDtoList, contactService.saveList(contactDtoList));
        Mockito.verify(contactRepository, Mockito.times(1)).saveAll(contactListArgumentCaptor.capture());
        Mockito.verify(contactMapper, Mockito.times(2)).contactToDto(contactArgumentCaptor.capture());
        Mockito.verify(contactMapper, Mockito.times(2)).dtoToContact(contactDtoArgumentCaptor.capture());
    }
	
	  @Test
    void findById_throwsException_givenNull() {
        when(contactRepository.findById(null)).thenThrow(ContactException.class);

        Assertions.assertThrows(ContactException.class, () -> {
            contactService.findById(null);
        });
    }

    @Test
    void findById_returnsContactDao_givenExistingContact() {
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        Optional<Contact> optionalContact = Optional.of(contact);

        when(contactMapper.contactToDto(contact)).thenReturn(contactDto);
        when(contactRepository.findById(1L)).thenReturn(optionalContact);

        assertEquals(contactDto, contactService.findById(1L));
    }

    @Test
    void findById_throwsException_givenNonExistingContactId() {
        Optional<Contact> optionalContact = Optional.empty();

        when(contactRepository.findById(1L)).thenReturn(optionalContact);

        Assertions.assertThrows(ContactException.class, () -> {
            contactService.findById(1L);
        });
    }

    @Test
    void getNumContacts_returnsZero_whenNoContacts() {
        when(contactRepository.count()).thenReturn(0L);
        assertEquals(0, contactService.getNumContacts());
    }

    @Test
    void getNumContacts_returnsAnInteger_whenContacts() {
        when(contactRepository.count()).thenReturn(3L);
        assertEquals(3, contactService.getNumContacts());
    }

    @Test
    void getAllSelectedRole_returnsEmptyList_whenNoContacts() {
        when(contactRepository.findAllByRoleEquals(Role.REPORTER)).thenReturn(new ArrayList<>());
        assertEquals(0, contactService.getAllSelectedRole(Role.REPORTER).size());
        assertEquals(0, contactService.getAllSelectedRole(Role.REPORTER).size());
    }

    @Test
    void getAllSelectedRole_returnsListOfContacts_whenContacts() {
        Contact contact = Contact.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        Contact contact2 = Contact.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contactDto = ContactDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        ContactDto contact2Dto = ContactDto.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .role(Role.REPORTER)
                .build();

        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        contactList.add(contact2);
        when(contactMapper.contactToDto(contact)).thenReturn(contactDto);
        when(contactMapper.contactToDto(contact2)).thenReturn(contact2Dto);
        var expectedList = new ArrayList<ContactDto>();
        expectedList.add(contactDto);
        expectedList.add(contact2Dto);

        when(contactRepository.findAllByRoleEquals(Role.REPORTER)).thenReturn(contactList);
        assertEquals(expectedList, contactService.getAllSelectedRole(Role.REPORTER));
    }

}