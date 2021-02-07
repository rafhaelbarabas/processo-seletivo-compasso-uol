package com.rafhael.andrade.processoseletivocompasso.service;

import com.rafhael.andrade.processoseletivocompasso.models.Customer;
import com.rafhael.andrade.processoseletivocompasso.models.dto.CustomerDto;
import com.rafhael.andrade.processoseletivocompasso.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomersService {

    @Autowired
    private CustomerRepository repository;

    public List<Customer> getAll(String name) {
        if (name.isEmpty()) {
            return repository.findAll();
        }
        return repository.findByName(name.toLowerCase());
    }

    public Customer getById(Long id) throws ResponseStatusException {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer create(Customer costumer) {
        return repository.save(costumer);
    }

    public Customer update(Long id, CustomerDto custumerDto) {
        Customer customer = getById(id);
        customer.setName(custumerDto.getName());
        return repository.save(customer);
    }

    public void delete(Long id) throws ResponseStatusException {
        repository.findById(id)
                .map(customer -> {
                    repository.delete(customer);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }
}
