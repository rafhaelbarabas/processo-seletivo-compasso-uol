package com.rafhael.andrade.processoseletivocompasso.service;

import com.rafhael.andrade.processoseletivocompasso.enums.State;
import com.rafhael.andrade.processoseletivocompasso.models.City;
import com.rafhael.andrade.processoseletivocompasso.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CitiesService {

    @Autowired
    private CityRepository repository;

    private boolean validation(City city) {
        return repository.findByCityAndState(city.getName(), city.getState()).isEmpty();
    }

    public List<City> getAll(String city, String state) {

        State enumState = state.isEmpty() ? null : Enum.valueOf(State.class, state);

        if (!city.isEmpty() && !state.isEmpty()) {
            return repository.findByCityAndState(city, enumState);
        } else if (!city.isEmpty()) {
            return repository.findByName(city);
        } else if (!state.isEmpty()) {
            return repository.findByState(enumState);
        }

        return repository.findAll();
    }

    public Optional<City> getById(Long id) {
        return repository.findById(id);
    }

    public City create(City city) throws ResponseStatusException {
        if (validation(city)) {
            return repository.save(city);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "city already registred");
        }
    }

}
