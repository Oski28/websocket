package com.example.websocketexample.repository;

import com.example.websocketexample.model.RefreshToken;
import com.example.websocketexample.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);

    List<RefreshToken> getAllByExpiryDateBefore(LocalDateTime localDateTime);
}
