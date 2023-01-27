package net.trexis.software.development.department.repository;

import net.trexis.software.development.department.repository.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostRepository extends JpaRepository<Cost, Long> {

    Cost findByCost(final int cost);
}
