package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.exceptions.ContactException;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

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
        if(contact.isPresent()) {
            return mapper.contactToDto(contact.get());
        }else{
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
    public ContactDto fetchContactByPhoneNumber(String phoneNumber) {
        var contact = repository.findByPhoneNumber(phoneNumber);
        return contact.map(mapper::contactToDto).orElse(null);
    }
}
