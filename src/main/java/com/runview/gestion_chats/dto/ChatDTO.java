package com.runview.gestion_chats.dto;

import com.runview.gestion_chats.enums.Sexe;
import lombok.Data;

import java.sql.Date;
@Data
public class ChatDTO {

    private Integer id;

    private String nom;

    private Sexe sexe;

    private Date dateNaissance;

    private String race;

    private Double prix;

    private String commentaires;
}
