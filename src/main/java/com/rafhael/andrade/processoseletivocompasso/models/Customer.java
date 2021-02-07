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
    @JoinColumn(nullable = false, name = "i_city", foreignKey = @ForeignKey(name="fk_costumer_city"))
    private City city;

    public Customer() {
    }

    public Customer(Long id, String name, Gender gerden, LocalDate birthDate, Integer age, City city) {
        this.id = id;
        this.name = name;
        this.gerden = gerden;
        this.birthDate = birthDate;
        this.age = age;
        this.city = city;
    }

    @PrePersist
    public void verifyAge(){
        LocalDate birthDatePersist = getBirthDate();
        Integer agePersist = getAge();

        if(isNull(agePersist)){
            setAge(LocalDate.now().getYear() - birthDatePersist.getYear());
        }
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

    public Gender getGerden() {
        return gerden;
    }

    public void setGarden(Gender gerden) {
        this.gerden = gerden;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public void setCity(City city) {
        this.city = city;
    }
}
