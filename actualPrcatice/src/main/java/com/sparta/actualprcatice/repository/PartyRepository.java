package com.sparta.actualprcatice.repository;

import com.sparta.actualprcatice.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

}