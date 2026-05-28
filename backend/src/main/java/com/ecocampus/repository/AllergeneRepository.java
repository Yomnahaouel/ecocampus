package com.ecocampus.repository;

import com.ecocampus.entity.Allergene;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergeneRepository extends JpaRepository<Allergene, Long> {
}
