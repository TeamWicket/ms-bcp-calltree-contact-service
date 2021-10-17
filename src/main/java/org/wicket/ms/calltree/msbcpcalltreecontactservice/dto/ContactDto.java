package org.wicket.ms.calltree.msbcpcalltreecontactservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDto {

    @Nullable
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Role role;

    private Long pointOfContactId;

    private Long version;
}
