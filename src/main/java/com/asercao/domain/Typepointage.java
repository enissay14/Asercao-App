package com.asercao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Typepointage.
 */
@Entity
@Table(name = "TYPEPOINTAGE")
public class Typepointage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "typepointage")
    @JsonIgnore
    private Set<Pointage> pointages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Pointage> getPointages() {
        return pointages;
    }

    public void setPointages(Set<Pointage> pointages) {
        this.pointages = pointages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Typepointage typepointage = (Typepointage) o;

        if ( ! Objects.equals(id, typepointage.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Typepointage{" +
                "id=" + id +
                ", type='" + type + "'" +
                '}';
    }
}
