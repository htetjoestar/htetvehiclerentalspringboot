package com.htetvehiclerental.htetvehiclerental.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    @Query("SELECT e FROM Employee e WHERE e.employee_id = :id")
    Optional<Employee> findEmployeeById(@Param("id") Long id);
    @Query("SELECT u FROM Employee u WHERE u.emp_email = :email AND u.emp_password = :password")
    Employee findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    
    @Query("SELECT u FROM Employee u WHERE u.emp_email = :email AND u.emp_password = :password AND u.emp_role = 'Portal Admin'")
    Employee findPortalAdmin(@Param("email") String email, @Param("password") String password);

    
    @Query(value = "SELECT u FROM Employee u WHERE " +
        "(:keyword IS NULL OR LOWER(u.emp_first_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(u.emp_last_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(u.emp_email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
        "AND (:nextweek IS NULL OR " +
        "    (FUNCTION('DATE', u.emp_created_date) > :now AND (FUNCTION('DATE', u.emp_created_date)) < :nextweek))"
        + "AND (:active IS NULL OR u.emp_active = :active)",
        countQuery = "SELECT u FROM Employee u WHERE " +
        "(:keyword IS NULL OR LOWER(u.emp_first_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(u.emp_last_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(u.emp_email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
        "AND (:nextweek IS NULL OR " +
        "    (FUNCTION('DATE', u.emp_created_date) > :now AND (FUNCTION('DATE', u.emp_created_date)) < :nextweek))"
        + "AND (:active IS NULL OR u.emp_active = :active)")
    Page<Employee> searchWithFilters(@Param("keyword") String keyword,
                                @Param("now") LocalDate now,
                                @Param("nextweek") LocalDate nextweek,
                                @Param("active") Boolean active,
                                Pageable pageable);
}
