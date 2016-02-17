package com.asercao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.asercao.domain.util.CustomLocalDateSerializer;
import com.asercao.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pointage.
 */
@Entity
@Table(name = "POINTAGE")
public class Pointage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "nbre_heure", nullable = false)
    private Long nbreHeure;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private Typepointage typepointage;

    @ManyToOne
    private Salarie salarie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNbreHeure() {
        return nbreHeure;
    }

    public void setNbreHeure(Long nbreHeure) {
        this.nbreHeure = nbreHeure;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Typepointage getTypepointage() {
        return typepointage;
    }

    public void setTypepointage(Typepointage typepointage) {
        this.typepointage = typepointage;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pointage pointage = (Pointage) o;

        if ( ! Objects.equals(id, pointage.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pointage{" +
                "id=" + id +
                ", nbreHeure='" + nbreHeure + "'" +
                ", date='" + date + "'" +
                '}';
    }
}
