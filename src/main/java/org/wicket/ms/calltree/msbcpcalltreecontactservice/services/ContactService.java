package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;

public interface ContactService {
    ContactDto saveOrUpdate(ContactDto contactDto);
}
