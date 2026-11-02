package dev.sadis.ppoo.repository;

import dev.sadis.ppoo.entity.Career;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ICareerRepository extends JpaRepository<Career, Long> {
    boolean existsByCode(String code);
    boolean existsByNameIgnoreCase(String name);

    @Query("select c from Career c where (:name is null or c.name=:name)")
    Page<Career> findAllFiltered(Pageable pageable, String name);

    Optional<Career> findByCode(String code);
    Page<Career> findByName(Pageable pageable, String name);
}
