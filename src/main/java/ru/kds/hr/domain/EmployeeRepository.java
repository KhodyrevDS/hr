package ru.kds.hr.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Employee repository
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
