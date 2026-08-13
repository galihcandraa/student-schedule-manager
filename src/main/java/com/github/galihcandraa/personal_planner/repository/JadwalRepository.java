package com.github.galihcandraa.personal_planner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.galihcandraa.personal_planner.model.*;;

public interface JadwalRepository extends JpaRepository<Jadwal, Long> {
    
    @Modifying
    @Query(value = "ALTER TABLE jadwal AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
