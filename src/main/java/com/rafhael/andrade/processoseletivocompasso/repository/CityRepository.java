package com.rafhael.andrade.processoseletivocompasso.repository;

import com.rafhael.andrade.processoseletivocompasso.enums.State;
import com.rafhael.andrade.processoseletivocompasso.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE c.name = :name AND c.state = :state")
    List<City> findByCityAndState(@Param("name") String name, @Param("state") State state);

    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<City> findByName(@Param("name") String name);

    @Query("SELECT c FROM City c WHERE c.state = :state")
    List<City> findByState(@Param("state") State state);
}
