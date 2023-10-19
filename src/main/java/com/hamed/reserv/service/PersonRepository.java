package com.hamed.reserv.service;

import com.hamed.reserv.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author H.Mohammadi
 * @created 2022/06/10
 */
@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByEmail(String email);

    List<Person> findByIsDoctor(boolean isDoctor);

    List<Person> findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM Person  WHERE name_family LIKE %?1%  OR address LIKE %?1% OR  about_me LIKE %?1%  AND is_Doctor = ?2", nativeQuery = true)
    List<Person> findPersonByNameFamily(String nameFamily,Boolean isDoctor);
}
