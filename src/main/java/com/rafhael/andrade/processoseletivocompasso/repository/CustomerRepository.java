package com.rafhael.andrade.processoseletivocompasso.repository;

import com.rafhael.andrade.processoseletivocompasso.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Customer> findByName(@Param("name") String name);
}
