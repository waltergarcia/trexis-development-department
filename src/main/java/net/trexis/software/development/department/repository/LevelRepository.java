package net.trexis.software.development.department.repository;

import net.trexis.software.development.department.repository.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Level findByLevel(final String level);
}
