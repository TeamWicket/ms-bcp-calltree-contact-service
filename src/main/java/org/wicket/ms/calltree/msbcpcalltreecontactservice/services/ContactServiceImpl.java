package org.wicket.ms.calltree.msbcpcalltreecontactservice.services;

import org.springframework.lang.Nullable;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.dto.ContactDto;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import java.util.List;

public class ContactServiceImpl implements ContactService{
    @Override
    public List<ContactDto> saveList(List<ContactDto> dtoList) {
        return null;
    }

    @Override
    public ContactDto saveOrUpdate(ContactDto contactDto) {
        return null;
    }

    @Override
    public void deleteContact(Long id) {

    }

    @Override
    public Integer getNumContacts() {
        return null;
    }

    @Override
    public List<ContactDto> getAllContacts(@Nullable String orderDirection, @Nullable String orderByValue, @Nullable Integer page, @Nullable Integer size) {
        return null;
    }

    @Override
    public ContactDto getContact(Long id) {
        return null;
    }

    @Override
    public List<ContactDto> getAllSelectedRole(Role role) {
        return null;
    }

    @Override
    public List<ContactDto> getCalltreeUntilRole(Role role) {
        return null;
    }

    @Override
    public ContactDto fetchContactByPhoneNumber(String string) {
        return null;
    }

    @Override
    public List<ContactDto> fetchManyContactsById(long[] id) {
        return null;
    }
}
