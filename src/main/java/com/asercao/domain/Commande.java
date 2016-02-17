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
 * A Commande.
 */
@Entity
@Table(name = "COMMANDE")
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code_commande", nullable = false)
    private String codeCommande;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Long montant;

    @NotNull
    @Column(name = "montant_achat", nullable = false)
    private Long montantAchat;

    @NotNull
    @Column(name = "nbre_heure", nullable = false)
    private Long nbreHeure;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    private Affaire affaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCommande() {
        return codeCommande;
    }

    public void setCodeCommande(String codeCommande) {
        this.codeCommande = codeCommande;
    }

    public Long getMontant() {return montant;}

    public void setMontantAchat(Long montantAchat) {
        this.montantAchat = montantAchat;
    }

    public Long getMontantAchat() {
        return montantAchat;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
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

        Commande commande = (Commande) o;

        if ( ! Objects.equals(id, commande.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", codeCommande='" + codeCommande + "'" +
                ", montant='" + montant + "'" +
                ", montantAchat='" + montantAchat +"'"+
                ", nbreHeure='" + nbreHeure +"'"+
                ", date='" + date + "'" +
                '}';
    }
}
