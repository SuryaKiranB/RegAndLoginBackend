package com.example.RegAndLoginApi.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

import com.example.RegAndLoginApi.Entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface JwtTokenRepository extends JpaRepository<JwtToken,Long> {

    Optional<JwtToken> findByJwToken(String token);

}
