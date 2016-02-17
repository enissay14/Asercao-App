package com.asercao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Typeachat.
 */
@Entity
@Table(name = "TYPEACHAT")
public class Typeachat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "typeachat")
    @JsonIgnore
    private Set<Achat> achats = new HashSet<>();

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

    public Set<Achat> getAchats() {
        return achats;
    }

    public void setAchats(Set<Achat> achats) {
        this.achats = achats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Typeachat typeachat = (Typeachat) o;

        if ( ! Objects.equals(id, typeachat.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Typeachat{" +
                "id=" + id +
                ", type='" + type + "'" +
                '}';
    }
}
