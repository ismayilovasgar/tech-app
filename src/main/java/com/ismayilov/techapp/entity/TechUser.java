package com.ismayilov.techapp.entity;

import com.ismayilov.techapp.dto.request.AccountRequestDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 200)
    Long id;

    @Column(name = "user_name", length = 50)
    String name;

    @Column(name = "user_surname", length = 50)
    String surname;

    @Column(name = "password", length = 30, unique = true)
    String password;

    @Column(name = "pin", length = 90)
    String pin;

    @Column(name = "role", length = 30)
    String role;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    List<Account> accountList;

    public void addAccountToList(List<AccountRequestDTO> accountRequestDTOList) {

        accountList = new ArrayList<>();
        accountList.forEach(accountDTO -> accountList.add(
                Account.builder()
                        .balance(accountDTO.getBalance())
                        .AccountNo(accountDTO.getAccountNo())
                        .currency(accountDTO.getCurrency())
                        .isActive(accountDTO.getIsActive())
                        .user(this).build())
        );
    }
}
