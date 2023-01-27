package net.trexis.software.development.department.repository;

import net.trexis.software.development.department.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(final String role);
}