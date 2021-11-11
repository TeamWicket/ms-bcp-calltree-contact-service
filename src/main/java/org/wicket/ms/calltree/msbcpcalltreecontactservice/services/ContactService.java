package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;

import java.util.List;

public interface ContactService {
    ContactDto saveOrUpdate(ContactDto contactDto);

    List<ContactDto> saveList (List<ContactDto> contactDtoList);
}
