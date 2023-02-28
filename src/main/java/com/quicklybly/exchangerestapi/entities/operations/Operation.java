package com.quicklybly.exchangerestapi.entities.operations;

import com.quicklybly.exchangerestapi.entities.UserEntity;
import com.quicklybly.exchangerestapi.utils.DateUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private final LocalDate date = DateUtils.getFormattedNow();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    protected Operation(UserEntity user) {
        this.user = user;
    }
}
