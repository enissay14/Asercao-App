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
 * A Montantcorrige.
 */
@Entity
@Table(name = "MONTANTCORRIGE")
public class Montantcorrige implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "montant_inter_corr", nullable = false)
    private Long montantInterCorr;

    @NotNull
    @Column(name = "montant_achat_corr", nullable = false)
    private Long montantAchatCorr;

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

    public Long getMontantInterCorr() {
        return montantInterCorr;
    }

    public void setMontantInterCorr(Long montantInterCorr) {
        this.montantInterCorr = montantInterCorr;
    }

    public Long getMontantAchatCorr() {
        return montantAchatCorr;
    }

    public void setMontantAchatCorr(Long montantAchatCorr) {
        this.montantAchatCorr = montantAchatCorr;
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

        Montantcorrige montantcorrige = (Montantcorrige) o;

        if ( ! Objects.equals(id, montantcorrige.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Montantcorrige{" +
                "id=" + id +
                ", montantInterCorr='" + montantInterCorr + "'" +
                ", montantAchatCorr='" + montantAchatCorr + "'" +
                ", date='" + date + "'" +
                '}';
    }
}
