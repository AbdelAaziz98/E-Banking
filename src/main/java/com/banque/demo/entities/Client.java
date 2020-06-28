package com.banque.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Client extends Utilisateur implements Serializable {
    @Column(length = 40)
    private String nom;
    @Column(length = 40)
    private String prenom;
    @Column(unique = true,length = 40)
    private String cin;
    @Column(length = 40)
    private String phone;
    @Column(length = 40)
    private String status="desactive";
    @OneToMany(mappedBy="client")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Compte> comptes;
    @OneToMany(mappedBy = "client")
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Virement> virements;
    @ManyToOne
    @JsonIgnore
    private Agence agence;
    @OneToMany(mappedBy = "client")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Message> messages;

}
