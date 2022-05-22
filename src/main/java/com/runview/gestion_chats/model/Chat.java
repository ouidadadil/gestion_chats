package com.runview.gestion_chats.model;

import com.runview.gestion_chats.enums.Sexe;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "chat")
@Data
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "sexe", columnDefinition = "ENUM('Mâle','Femelle')")
    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Column(name = "date_naissance", nullable = false)
    private Date dateNaissance;

    @Column(name = "race", nullable = false)
    private String race;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "commentaires", nullable = true)
    private String commentaires;

}