package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

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
}
