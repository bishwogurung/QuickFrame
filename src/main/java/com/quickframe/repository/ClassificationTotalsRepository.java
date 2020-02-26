package com.quickframe.repository;

import com.quickframe.domain.ClassificationTotals;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationTotalsRepository extends CrudRepository<ClassificationTotals, String> {
}
