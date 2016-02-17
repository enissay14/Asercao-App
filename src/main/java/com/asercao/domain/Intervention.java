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
 * A Intervention.
 */
@Entity
@Table(name = "INTERVENTION")
public class Intervention implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nbre_heure", nullable = false)
    private Long nbreHeure;

    @NotNull
    @Column(name = "nbre_jour", nullable = false)
    private Long nbreJour;

    @Column(name = "taux_horaire")
    private Long tauxHoraire;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Long montant;

    @Column(name = "montant_deplacement")
    private Long montantDeplacement;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private Salarie salarie;

    @ManyToOne
    private Affaire affaire;

    @ManyToOne
    private Typeintervention typeintervention;

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

    public Long getNbreJour() {
        return nbreJour;
    }

    public void setNbreJour(Long nbreJour) {
        this.nbreJour = nbreJour;
    }

    public Long getTauxHoraire() {return tauxHoraire;}

    public void setTauxHoraire(Long tauxHoraire) { this.tauxHoraire = tauxHoraire;}

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Long getMontantDeplacement() {
        return montantDeplacement;
    }

    public void setMontantDeplacement(Long montantDeplacement) {
        this.montantDeplacement = montantDeplacement;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Salarie getSalarie() {
        return salarie;
    }

    public void setSalarie(Salarie salarie) {
        this.salarie = salarie;
    }

    public Affaire getAffaire() {
        return affaire;
    }

    public void setAffaire(Affaire affaire) {
        this.affaire = affaire;
    }

    public Typeintervention getTypeintervention() {
        return typeintervention;
    }

    public void setTypeintervention(Typeintervention typeintervention) {
        this.typeintervention = typeintervention;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Intervention intervention = (Intervention) o;

        if ( ! Objects.equals(id, intervention.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", nbreHeure='" + nbreHeure + "'" +
                ", nbreJour='" + nbreJour + "'" +
                ", tauxHoraire='" + tauxHoraire + "'" +
                ", montant='" + montant + "'" +
                ", montantDeplacement='" + montantDeplacement + "'" +
                ", dateDebut='" + date + "'" +
                '}';
    }
}
