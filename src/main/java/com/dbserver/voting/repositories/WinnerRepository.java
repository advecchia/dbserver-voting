package com.dbserver.voting.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dbserver.voting.models.Winner;

@Repository
public interface WinnerRepository extends JpaRepository<Winner, String> {

	Winner findByLunchDate(Date lunchDate);

}
