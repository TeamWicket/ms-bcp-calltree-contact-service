package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers.ContactMapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.repository.ContactRepository;

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
}
