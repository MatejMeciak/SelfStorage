package com.appslab.selfstorage.repositories;

import com.appslab.selfstorage.models.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report,Long> {
}
