package dev.sadis.ppoo.repository;

import dev.sadis.ppoo.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Long> {
    @EntityGraph(attributePaths = "faculty")
    Optional<Teacher> findById(Long id);
    boolean existsByEmail(String email);
    boolean existsByDocument(String document);
    boolean existsByFacultyId(Long id);

    @EntityGraph(attributePaths = "faculty")
    Page<Teacher> findByFacultyId(Long id, Pageable pageable);
}