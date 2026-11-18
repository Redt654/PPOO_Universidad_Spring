package dev.sadis.ppoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "faculties")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

    @Column( nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

    @Column(name = "dean_name",nullable = false, length = 100)
    private String deanName;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Column(nullable = false, length = 100)
    private String location;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private LocalDateTime updatedAt;
}
