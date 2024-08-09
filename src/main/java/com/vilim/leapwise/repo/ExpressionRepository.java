package com.vilim.leapwise.repo;

import com.vilim.leapwise.model.entity.ExpressionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressionRepository extends JpaRepository<ExpressionEntity, Long> {
}
