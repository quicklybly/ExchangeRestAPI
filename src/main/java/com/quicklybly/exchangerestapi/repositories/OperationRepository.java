package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.operations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Long countAllByDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
