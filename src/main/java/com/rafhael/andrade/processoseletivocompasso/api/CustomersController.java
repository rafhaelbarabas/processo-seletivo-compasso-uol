package com.rafhael.andrade.processoseletivocompasso.api;

import com.rafhael.andrade.processoseletivocompasso.models.Customer;
import com.rafhael.andrade.processoseletivocompasso.models.dto.CustomerDto;
import com.rafhael.andrade.processoseletivocompasso.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomersController {

    @Autowired
    private CustomersService service;

    @GetMapping
    public List<Customer> getAll(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return service.getAll(name);
    }

    @GetMapping("{id}")
    public Customer getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer) {
        return service.create(customer);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer update(@PathVariable Long id, @RequestBody CustomerDto customer) {
        return service.update(id, customer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
