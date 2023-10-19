package com.hamed.reserv.service;

import com.hamed.reserv.model.ReservTimeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author H.Mohammadi
 * @created 2022/06/20
 */
public interface ReservTimeClientRepository extends JpaRepository<ReservTimeClient, Long> {
    List<ReservTimeClient> findByUserId(String userId);
    List<ReservTimeClient> findByDoctorId(String doctorId);
}
