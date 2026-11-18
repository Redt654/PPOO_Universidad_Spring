package dev.sadis.ppoo.repository;

import dev.sadis.ppoo.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacultyRepository extends JpaRepository<Faculty, Long>{
    boolean existsByEmail(String email);
    boolean existsByCode(String code);
    boolean existsByPhone(String phone);
}
