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
 * A Affaire.
 */
@Entity
@Table(name = "AFFAIRE")
public class Affaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code_affaire", nullable = false)
    private String codeAffaire;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "resp_client", nullable = false)
    private String respClient;

    @NotNull
    @Column(name = "respAffaire", nullable = false)
    private String respAffaire;

    @Column(name = "taux_horaire")
    private Long tauxHoraire;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "sauvegarde")
    private String sauvegarde;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Typeaffaire typeaffaire;

    @ManyToOne
    private Statusaffaire statusaffaire;

    @OneToMany(mappedBy = "affaire")
    @JsonIgnore
    private Set<Bonfacture> bonfactures = new HashSet<>();

    @OneToMany(mappedBy = "affaire")
    @JsonIgnore
    private Set<Intervention> interventions = new HashSet<>();

    @OneToMany(mappedBy = "affaire")
    @JsonIgnore
    private Set<Achat> achats = new HashSet<>();

    @OneToMany(mappedBy = "affaire")
    @JsonIgnore
    private Set<Commande> commandes = new HashSet<>();

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getCodeAffaire() {return codeAffaire;}

    public void setCodeAffaire(String codeAffaire) {this.codeAffaire = codeAffaire;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRespClient() {
        return respClient;
    }

    public void setRespClient(String respClient) {
        this.respClient = respClient;
    }

    public String getRespAffaire() {return respAffaire;}

    public void setRespAffaire(String respAffaire) {this.respAffaire = respAffaire;}

    public Long getTauxHoraire() {return tauxHoraire;}

    public void setTauxHoraire(Long tauxHoraire) { this.tauxHoraire = tauxHoraire;}

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getsauvegarde() {return sauvegarde;}

    public void setsauvegarde(String sauvegarde) {this.sauvegarde = sauvegarde;}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Typeaffaire getTypeaffaire() {
        return typeaffaire;
    }

    public void setTypeaffaire(Typeaffaire typeaffaire) {
        this.typeaffaire = typeaffaire;
    }

    public Statusaffaire getStatusaffaire() {
        return statusaffaire;
    }

    public void setStatusaffaire(Statusaffaire statusaffaire) {
        this.statusaffaire = statusaffaire;
    }

    public Set<Bonfacture> getBonfactures() {
        return bonfactures;
    }

    public void setBonfactures(Set<Bonfacture> bonfactures) {
        this.bonfactures = bonfactures;
    }

    public Set<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }

    public Set<Achat> getAchats() {
        return achats;
    }

    public void setAchats(Set<Achat> achats) {
        this.achats = achats;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Affaire affaire = (Affaire) o;

        if ( ! Objects.equals(id, affaire.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Affaire{" +
                "id=" + id +
                ", codeAffaire='" + codeAffaire + "'" +
                ", description='" + description + "'" +
                ", respClient='" + respClient + "'" +
            ", respAffaire='" + respAffaire + "'" +
                ", dateDebut='" + dateDebut + "'" +
                ", dateFin='" + dateFin + "'" +
                ", tauxHoraire='"+ tauxHoraire + "'" +
                '}';
    }
}
