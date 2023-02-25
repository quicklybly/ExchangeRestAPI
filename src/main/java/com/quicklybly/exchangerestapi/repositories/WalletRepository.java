package com.quicklybly.exchangerestapi.repositories;

import com.quicklybly.exchangerestapi.entities.Wallet;
import com.quicklybly.exchangerestapi.entities.WalletKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, WalletKey> {
    List<Wallet> findAllByUserEntityUsername(String username);
}
