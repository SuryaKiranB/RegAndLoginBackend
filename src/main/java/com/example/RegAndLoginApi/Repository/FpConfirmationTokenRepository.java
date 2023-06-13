package com.example.RegAndLoginApi.Repository;

import com.example.RegAndLoginApi.Entity.ConfirmationToken;
import com.example.RegAndLoginApi.Entity.FpConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FpConfirmationTokenRepository extends JpaRepository<FpConfirmationToken,Long> {
    FpConfirmationToken findByFpToken(String fpToken);
}
