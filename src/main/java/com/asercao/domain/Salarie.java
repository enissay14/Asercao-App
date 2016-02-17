package com.asercao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Salarie.
 */
@Entity
@Table(name = "SALARIE")
public class Salarie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "tel")
    private Integer tel;

    @Column(name = "adresse")
    private String adresse;

	@Column(name = "cout_revient")
    private Long coutRevient;

    @ManyToOne
    private Statussalaire statussalaire;

    @OneToMany(mappedBy = "salarie")
    @JsonIgnore
    private Set<Intervention> interventions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

	public Long getCoutRevient() {
        return coutRevient;
    }

    public void setCoutRevient(Long coutRevient) {
        this.coutRevient = coutRevient;
    }

    public Statussalaire getStatussalaire() {
        return statussalaire;
    }

    public void setStatussalaire(Statussalaire statussalaire) {
        this.statussalaire = statussalaire;
    }

    public Set<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(Set<Intervention> interventions) {
        this.interventions = interventions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Salarie salarie = (Salarie) o;

        if ( ! Objects.equals(id, salarie.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Salarie{" +
                "id=" + id +
                ", nom='" + nom + "'" +
                ", prenom='" + prenom + "'" +
                ", email='" + email + "'" +
                ", tel='" + tel + "'" +
                ", adresse='" + adresse + "'" +
                '}';
    }
}
