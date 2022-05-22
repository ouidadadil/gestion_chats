package com.runview.gestion_chats.mappers;

import com.runview.gestion_chats.dto.ChatDTO;
import com.runview.gestion_chats.enums.Sexe;
import com.runview.gestion_chats.model.Chat;
import org.apache.commons.csv.CSVRecord;

import java.sql.Date;
import java.util.List;

public class ChatMapper {
    /**
     * transforme une liste de csvRecord en liste de chats
     * @param chats
     * @param csvRecords
     */
    public static void cSVRecordToChats(List<Chat> chats, Iterable<CSVRecord> csvRecords) {
        for (CSVRecord csvRecord : csvRecords) {
            Chat chat = new Chat();
            chat.setNom(sanitize(csvRecord.get(0)));
            chat.setSexe(Sexe.valueOf(sanitize(csvRecord.get(1))));
            chat.setDateNaissance(Date.valueOf(sanitize(csvRecord.get(2))));
            chat.setRace(sanitize(csvRecord.get(3)));
            chat.setPrix(Double.parseDouble(sanitize(csvRecord.get(4).replace(",","."))));
            chat.setCommentaires(csvRecord.get(5));
            chats.add(chat);
        }
    }

    /**
     * transforme un  entity en dto
     * @param chat
     * @return
     */
    public static ChatDTO entityToDto(Chat chat){
        ChatDTO dto = new ChatDTO();
        dto.setNom(chat.getNom());
        dto.setSexe(chat.getSexe());
        dto.setDateNaissance(chat.getDateNaissance());
        dto.setRace(chat.getRace());
        dto.setPrix(chat.getPrix());
        dto.setCommentaires(chat.getCommentaires());
        return dto;
    }

    /**
     * transforme un dto en entity
     * @param dto
     * @return
     */
    public static Chat dtoToEntity(ChatDTO dto){
        Chat chat = new Chat();
        chat.setNom(dto.getNom());
        chat.setSexe(dto.getSexe());
        chat.setDateNaissance(dto.getDateNaissance());
        chat.setRace(dto.getRace());
        chat.setPrix(dto.getPrix());
        chat.setCommentaires(dto.getCommentaires());
        return chat;
    }

    private static String sanitize(String str) {
        return str.replace("\"", "");
    }
}
