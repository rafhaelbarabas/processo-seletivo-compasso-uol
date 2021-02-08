package com.rafhael.andrade.processoseletivocompasso.models;

import com.rafhael.andrade.processoseletivocompasso.enums.State;

import javax.persistence.*;

@Entity
@Table(name = "cities", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "state"})})
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public State getState() {
        return state;
    }

    public static class Builder {

        private Long id;
        private String name;
        private State state;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder state(State state) {
            this.state = state;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }

    private City(Builder builder) {
        id = builder.id;
        name = builder.name;
        state = builder.state;
    }
}
