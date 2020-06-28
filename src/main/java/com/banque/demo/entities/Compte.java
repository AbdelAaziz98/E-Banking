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
public class Compte implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long num;
    private long solde;
    @Column(length = 40)
    private String type;
    @ManyToOne
    @JsonIgnore
    private Client client;
    @OneToMany(mappedBy = "compte")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Virement> virements;

    public Compte findByNum(long NumCompte){
        Compte compte=new Compte();
        compte.setNum(NumCompte);
        return compte;
    }





}
