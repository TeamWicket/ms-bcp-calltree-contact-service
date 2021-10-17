package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.springframework.lang.Nullable;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import java.util.List;

public interface ContactService {
    List<ContactDto> saveList(List<ContactDto> dtoList);

    ContactDto saveOrUpdate(ContactDto contactDto);

    void deleteContact(Long id);

    Integer getNumContacts();

    List<ContactDto> getAllContacts(@Nullable String orderDirection, @Nullable String orderByValue,
                                    @Nullable Integer page, @Nullable Integer size);

    ContactDto getContact(Long id);

    List<ContactDto> getAllSelectedRole(Role role);

    List<ContactDto> getCalltreeUntilRole(Role role);

    ContactDto fetchContactByPhoneNumber(String string);

    List<ContactDto> fetchManyContactsById(long[] id);
}
