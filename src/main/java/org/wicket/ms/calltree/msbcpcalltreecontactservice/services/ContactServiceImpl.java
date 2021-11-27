package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.exceptions.ContactException;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ContactMapper mapper;

    @Override
    public ContactDto saveOrUpdate(ContactDto contactDto) {
        Contact newContact = mapper.dtoToContact(contactDto);
        Contact savedContact = repository.save(newContact);
        return mapper.contactToDto(savedContact);
    }

    @Override
    public ContactDto findById(Long id) {
        var contact = repository.findById(id);
        if (contact.isPresent()) {
            return mapper.contactToDto(contact.get());
        } else {
            throw new ContactException("Contact not found");
        }
    }

    @Override
    public List<ContactDto> saveList(List<ContactDto> contactDtoList) {

        List<Contact> newContactList = contactDtoList
                .stream()
                .map(mapper::dtoToContact)
                .toList();

        List<Contact> savedContactList = repository.saveAll(newContactList);

        return savedContactList
                .stream()
                .map(mapper::contactToDto)
                .toList();
    }

    @Override
    public List<ContactDto> fetchManyContactsById(long[] id) {
        List<ContactDto> contactDtoList = new ArrayList<>();
        for (long i : id) {
            var contact = repository.findById(i);
            if (contact.isPresent()) {
                contactDtoList.add(mapper.contactToDto(contact.get()));
            } else {
                throw new ContactException("Contact not found");
            }
        }
        return contactDtoList;
    }

    @Override
    public void deleteContact(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ContactDto fetchContactByPhoneNumber(String phoneNumber) {
        var contact = repository.findByPhoneNumber(phoneNumber);
        return contact.map(mapper::contactToDto).orElse(null);
    }

    @Override
    public List<ContactDto> getAllSelectedRole(Role role) {
        var contacts = repository.findAllByRoleEquals(role);
        return contacts
                .stream()
                .map(mapper::contactToDto)
                .toList();
    }

    @Override
    public Integer getNumContacts() {
        return Math.toIntExact(repository.count());
    }

    @Override
    public List<ContactDto> getAllContacts(@Nullable String orderDirection, @Nullable String orderByValue, @Nullable Integer page, @Nullable Integer size) {

        if ((orderDirection == null || orderByValue == null) && (page == null || size == null)) {
            return repository.findAll().stream()
                    .map(mapper::contactToDto)
                    .toList();
        } else {
            return sortList(orderDirection, orderByValue, page, size);
        }
    }

    private List<ContactDto> sortList(String orderDirection, String orderByValue, Integer page, Integer size) {
        Sort.Direction dir = orderDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        var contacts = repository.findAll(PageRequest.of(page, size, by(dir, orderByValue))).getContent();
        return contacts.stream().map(mapper::contactToDto).toList();
    }
}
