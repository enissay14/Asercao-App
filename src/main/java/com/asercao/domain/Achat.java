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
 * A Achat.
 */
@Entity
@Table(name = "ACHAT")
public class Achat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Long montant;


    @Column(name = "nbre_heure")
    private Long nbreHeure;


    @Column(name = "taux_horaire")
    private Long tauxHoraire;

    @ManyToOne
    private Typeachat typeachat;

    @ManyToOne
    private Affaire affaire;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Long getNbreHeure() {
        return nbreHeure;
    }

    public void setNbreHeure(Long nbreHeure) {
        this.nbreHeure = nbreHeure;
    }

    public Long getTauxHoraire() {return tauxHoraire;}

    public void setTauxHoraire(Long tauxHoraire) { this.tauxHoraire = tauxHoraire;}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Typeachat getTypeachat() {
        return typeachat;
    }

    public void setTypeachat(Typeachat typeachat) {
        this.typeachat = typeachat;
    }

    public Affaire getAffaire() {
        return affaire;
    }

    public void setAffaire(Affaire affaire) {
        this.affaire = affaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Achat achat = (Achat) o;

        if ( ! Objects.equals(id, achat.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Achat{" +
                "id=" + id +
                ", nom='" + nom + "'" +
                ", description='" + description + "'" +
            ", nbreHeure='" + nbreHeure + "'" +
            ", tauxHoraire='" + tauxHoraire + "'" +

                ", date='" + date + "'" +
                ", montant='" + montant + "'" +
                '}';
    }
}
