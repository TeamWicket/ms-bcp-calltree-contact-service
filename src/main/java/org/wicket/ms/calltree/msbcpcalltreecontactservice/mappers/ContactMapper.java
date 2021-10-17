package org.wicket.ms.calltree.msbcpcalltreecontactservice.mappers;

import org.mapstruct.Mapper;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.models.Contact;

@Mapper
public interface ContactMapper {
    ContactDto contactToDto(Contact contact);
    Contact dtoToContact(ContactDto contactDto);
}
