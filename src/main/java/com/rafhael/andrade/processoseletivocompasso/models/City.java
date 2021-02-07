package com.rafhael.andrade.processoseletivocompasso.models;

import com.rafhael.andrade.processoseletivocompasso.enums.State;

import javax.persistence.*;

@Entity
@Table(name = "cities", uniqueConstraints = {@UniqueConstraint(columnNames = {"name","state"})})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private State state;

    public City() {
    }

    public City(Long id, String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
