package dev.sadis.ppoo.repository;

import dev.sadis.ppoo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    @EntityGraph(attributePaths = "career")
    Optional<Student> findById(Long id);

    boolean existsByEmail(String email);
    boolean existsByDocument(String document);
    boolean existsByStudentCode(String studentCode);

    @Query("select s from Student s join fetch s.career " +
            "where (:careerId is null or s.career.id=:careerId) " +
            "and (:isActive is null or s.isActive=:isActive)")
    Page<Student> findAllFiltered(Pageable pageable, Long careerId, Boolean isActive);

    @EntityGraph(attributePaths = "career")
    Optional<Student> findByEmail(String email);

    @EntityGraph(attributePaths = "career")
    Optional<Student> findByDocument(String document);

    @EntityGraph(attributePaths = "career")
    Optional<Student> findByStudentCode(String studentCode);
}
