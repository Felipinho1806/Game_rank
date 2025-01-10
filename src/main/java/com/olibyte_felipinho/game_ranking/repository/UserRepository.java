package com.olibyte_felipinho.game_ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olibyte_felipinho.game_ranking.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    
    
}
