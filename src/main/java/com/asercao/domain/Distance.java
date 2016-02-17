package com.asercao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Distance.
 */
@Entity
@Table(name = "DISTANCE")
public class Distance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "distance", nullable = false)
    private String distance;

    @NotNull
    @Min(value = 0)
    @Column(name = "bareme", nullable = false)
    private Long bareme;

    @OneToMany(mappedBy = "distance")
    @JsonIgnore
    private Set<Client> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Long getBareme() {
        return bareme;
    }

    public void setBareme(Long bareme) {
        this.bareme = bareme;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Distance distance = (Distance) o;

        if ( ! Objects.equals(id, distance.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Distance{" +
                "id=" + id +
                ", distance='" + distance + "'" +
                ", bareme='" + bareme + "'" +
                '}';
    }
}
