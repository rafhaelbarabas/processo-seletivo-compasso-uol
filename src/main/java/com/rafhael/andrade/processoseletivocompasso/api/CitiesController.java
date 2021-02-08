package com.rafhael.andrade.processoseletivocompasso.api;

import com.rafhael.andrade.processoseletivocompasso.models.City;
import com.rafhael.andrade.processoseletivocompasso.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cities")
public class CitiesController {

    @Autowired
    private CityService service;

    @GetMapping
    public List<City> getAll(
            @RequestParam(value = "city", required = false, defaultValue = "") String city,
            @RequestParam(value = "state", required = false, defaultValue = "") String state) {
        return service.getAll(city, state);
    }

    @GetMapping("{id}")
    public City getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public City create(@RequestBody City city) {
        return service.create(city);
    }
}
