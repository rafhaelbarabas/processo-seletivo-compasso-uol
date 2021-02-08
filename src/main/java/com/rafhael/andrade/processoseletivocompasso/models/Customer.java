package com.rafhael.andrade.processoseletivocompasso.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.rafhael.andrade.processoseletivocompasso.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private Gender gerden;

    @Column(name = "birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    @Column
    private Integer age;

    @ManyToOne
    @JoinColumn(nullable = false, name = "i_city", foreignKey = @ForeignKey(name = "fk_customer_city"))
    private City city;

    public Customer() {
    }

    @PrePersist
    public void verifyAge() {
        LocalDate birthDatePersist = getBirthDate();
        Integer agePersist = getAge();

        if (isNull(agePersist)) {
            setAge(LocalDate.now().getYear() - birthDatePersist.getYear());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGerden() {
        return gerden;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public static class Builder {

        private Long id;
        private String name;
        private Gender gerden;
        private LocalDate birthDate;
        private Integer age;
        private City city;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder gerden(Gender gerden) {
            this.gerden = gerden;
            return this;
        }

        public Builder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder city(City city) {
            this.city = city;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }

    private Customer(Customer.Builder builder) {
        id = builder.id;
        name = builder.name;
        gerden = builder.gerden;
        birthDate = builder.birthDate;
        age = builder.age;
        city = builder.city;
    }
}
