package com.ismayilov.techapp.entity;

import com.ismayilov.techapp.dto.request.AccountRequestDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = "accountList")
public class TechUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 200)
    Long id;

    @Column(name = "user_name", length = 50)
    String name;

    @Column(name = "user_surname", length = 50)
    String surname;

    @Column(name = "password", length = 255)
    String password;

    @Column(name = "pin", length = 90, unique = true)
    String pin;

    @Column(name = "role", length = 50)
    String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Builder.Default
    List<Account> accountList = new ArrayList<>();


    public void addAccountToList(List<AccountRequestDTO> accountRequestDTOList) {
        this.accountList = new ArrayList<>();
        accountRequestDTOList.forEach(dto -> {
            Account account = Account.builder()
                    .balance(dto.getBalance())
                    .currency(dto.getCurrency())
                    .isActive(dto.getIsActive())
                    .accountNo(dto.getAccountNo())
                    .user(this)
                    .build();

            this.accountList.add(account);
        });

    }

}
