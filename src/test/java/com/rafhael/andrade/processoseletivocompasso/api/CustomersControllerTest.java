package com.rafhael.andrade.processoseletivocompasso.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafhael.andrade.processoseletivocompasso.enums.Gender;
import com.rafhael.andrade.processoseletivocompasso.enums.State;
import com.rafhael.andrade.processoseletivocompasso.models.City;
import com.rafhael.andrade.processoseletivocompasso.models.Customer;
import com.rafhael.andrade.processoseletivocompasso.models.dto.CustomerDto;
import com.rafhael.andrade.processoseletivocompasso.service.CustomersService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomersControllerTest {

    final String API_PATH = "/api/customers/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomersService service;

    private ObjectMapper mapper = new ObjectMapper();

    private City createCity() {
        return new City(1L, "Criciúma", State.SC);
    }

    private Customer createCustomer() {
        return new Customer(1L,
                "Rafhael de Andrade Barabas",
                Gender.M,
                LocalDate.parse("1994-01-28"),
                27,
                createCity());
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        Customer customer = createCustomer();
        String json = mapper.writeValueAsString(customer);

        when(service.create(any(Customer.class))).thenReturn(customer);

        mvc.perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(service).create(captor.capture());

        Customer captured = captor.getValue();
        assertEquals(customer.getId(), captured.getId());
        assertEquals(customer.getName(), captured.getName());
        assertEquals(customer.getAge(), captured.getAge());
        assertEquals(customer.getBirthDate(), captured.getBirthDate());
        assertEquals(customer.getGerden(), captured.getGerden());
        assertEquals(customer.getCity().getId(), captured.getCity().getId());
        assertEquals(customer.getCity().getName(), captured.getCity().getName());
        assertEquals(customer.getCity().getState().name(), captured.getCity().getState().name());
    }

    @Test
    public void shouldFindAllCustomers() throws Exception {
        List<Customer> list = new ArrayList<Customer>();
        list.add(createCustomer());

        when(service.getAll("")).thenReturn(list);

        mvc.perform(get(API_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(list.size() > 0);
    }

    @Test
    public void shouldFindCustomerById() throws Exception {
        Customer customer = createCustomer();
        Integer cityId = Math.toIntExact(customer.getCity().getId());

        when(service.getById(eq(1L))).thenReturn(customer);

        mvc.perform(get(API_PATH + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(customer.getName())))
                .andExpect(jsonPath("$.gerden", Matchers.is(customer.getGerden().name())))
                .andExpect(jsonPath("$.birthDate", Matchers.is(customer.getBirthDate().toString())))
                .andExpect(jsonPath("$.age", Matchers.is(customer.getAge())))
                .andExpect(jsonPath("$.city.id", Matchers.is(cityId)))
                .andExpect(jsonPath("$.city.name", Matchers.is(customer.getCity().getName())))
                .andExpect(jsonPath("$.city.state", Matchers.is(customer.getCity().getState().name())));
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        Customer customer = createCustomer();

        when(service.getById(eq(1L))).thenReturn(customer);

        mvc.perform(delete(API_PATH + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        Customer customer = createCustomer();
        customer.setName("Rafhael Barabas");
        CustomerDto dto = new CustomerDto("Rafhael Barabas");

        String json = mapper.writeValueAsString(customer);

        when(service.getById(eq(1L))).thenReturn(customer);
        when(service.update(eq(customer.getId()), eq(dto))).thenReturn(customer);

        mvc.perform(put(API_PATH + customer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        assertEquals(dto.getName(), customer.getName());
    }

}
