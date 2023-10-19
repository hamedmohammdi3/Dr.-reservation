package com.hamed.reserv.service;

import com.hamed.reserv.model.TimeSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author H.Mohammadi
 * @created 2022/06/19
 */
public interface TimeSetRepository extends JpaRepository<TimeSet, Long> {
     List<TimeSet> findByTimeAndPersonId(String time, String personId);

}
