package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.springframework.lang.Nullable;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import java.util.List;

public interface ContactService {

    ContactDto saveOrUpdate(ContactDto contactDto);

    ContactDto findById(Long id);

    List<ContactDto> saveList(List<ContactDto> contactDtoList);

    List<ContactDto> getAllSelectedRole(Role role);

    Integer getNumContacts();

    void deleteContact(Long id);

    ContactDto fetchContactByPhoneNumber(String string);

    List<ContactDto> fetchManyContactsById(long[] id);

    List<ContactDto> getAllContacts(@Nullable String orderDirection, @Nullable String orderByValue, @Nullable Integer page, @Nullable Integer size);
}
