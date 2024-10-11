package com.eventify.eventify.models.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String imageData;

    @Getter
    @Setter
    private boolean isVerified;

    public Account() {
        this.isVerified = false;
    }
}
