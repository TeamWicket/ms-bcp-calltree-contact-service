package org.wicket.ms.calltree.msbcpcalltreecontactservice.models;

import lombok.*;
import org.springframework.lang.Nullable;
import org.wicket.ms.calltree.msbcpcalltreecontactservice.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Nullable
    private Long pointOfContactId;

    @Version
    private Long version;
}
