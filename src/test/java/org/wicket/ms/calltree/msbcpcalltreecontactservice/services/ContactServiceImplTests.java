package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
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
    void saveList_throwsError_givenNull() {
        when(contactRepository.saveAll(null)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactService.saveList(null);
        });
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
}
