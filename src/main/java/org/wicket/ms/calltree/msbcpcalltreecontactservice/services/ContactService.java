package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import java.util.List;

public interface ContactService {
  
    ContactDto saveOrUpdate(ContactDto contactDto);
  
    ContactDto findById(Long id);

    List<ContactDto> saveList (List<ContactDto> contactDtoList);

    List<ContactDto> getAllSelectedRole(Role role);

    Integer getNumContacts();

    public void deleteContact(Long id);

    ContactDto fetchContactByPhoneNumber(String string);

    List<ContactDto> fetchManyContactsById(long[] id);
}
