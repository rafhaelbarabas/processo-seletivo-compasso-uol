package com.rafhael.andrade.processoseletivocompasso.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafhael.andrade.processoseletivocompasso.enums.State;
import com.rafhael.andrade.processoseletivocompasso.models.City;
import com.rafhael.andrade.processoseletivocompasso.service.CitiesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CitiesControllerTest {

    final String API_PATH = "/api/cities/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CitiesService service;

    private ObjectMapper mapper = new ObjectMapper();

    private City createCity() {
        return new City(1L, "Criciúma", State.SC);
    }

    @Test
    public void shouldCreateCity() throws Exception {
        City city = createCity();
        String json = mapper.writeValueAsString(city);

        when(service.create(any(City.class))).thenReturn(city);

        mvc.perform(post(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        verify(service).create(captor.capture());

        City captured = captor.getValue();
        assertEquals(city.getId(), captured.getId());
        assertEquals(city.getName(), captured.getName());
        assertEquals(city.getState(), captured.getState());
    }

    @Test
    public void shouldFindAllCities() throws Exception {
        List<City> list = new ArrayList<City>();
        list.add(createCity());

        when(service.getAll("", "")).thenReturn(list);

        mvc.perform(get(API_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertTrue(list.size() > 0);
    }

    @Test
    public void shouldFindCityById() throws Exception {
        City city = createCity();

        when(service.getById(eq(1L))).thenReturn(city);

        mvc.perform(get(API_PATH + city.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(city.getName())))
                .andExpect(jsonPath("$.state", Matchers.is(city.getState().name())));
    }
}
