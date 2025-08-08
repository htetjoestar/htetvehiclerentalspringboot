package com.htetvehiclerental.htetvehiclerental.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.htetvehiclerental.htetvehiclerental.entity.Customer;
import com.htetvehiclerental.htetvehiclerental.entity.Vehicle;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    @Query("SELECT u FROM Customer u WHERE u.cust_email = :email AND u.cust_password = :password")
    Customer findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT c FROM Customer c WHERE c.cust_email = :email AND c.cust_last_name = :lastName")
    Optional<Customer> findByEmailAndLastName(@Param("email") String email, @Param("lastName") String lastName);

        @Query(
            value= "SELECT c FROM Customer c WHERE " +
        "(:keyword IS NULL OR LOWER(c.cust_first_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(c.cust_last_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(c.cust_email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
        "AND (:nextweek IS NULL OR " +
        "    (FUNCTION('DATE', c.cust_created_date) > :now AND (FUNCTION('DATE', c.cust_created_date)) < :nextweek))"
        + "AND (:active IS NULL OR c.cust_active = :active)",
            countQuery = "SELECT c FROM Customer c WHERE " +
        "(:keyword IS NULL OR LOWER(c.cust_first_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(c.cust_last_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
        "OR LOWER(c.cust_email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
        "AND (:nextweek IS NULL OR " +
        "    (FUNCTION('DATE', c.cust_created_date) > :now AND (FUNCTION('DATE', c.cust_created_date)) < :nextweek))"
        + "AND (:active IS NULL OR c.cust_active = :active)")
    Page<Customer> searchWithFilters(@Param("keyword") String keyword,
                                @Param("now") LocalDate now,
                                @Param("nextweek") LocalDate nextweek,
                                @Param("active") Boolean active,
                                Pageable pageable);
}
