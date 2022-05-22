package com.runview.gestion_chats.services;

import com.runview.gestion_chats.helper.CSVHelper;
import com.runview.gestion_chats.model.Chat;
import com.runview.gestion_chats.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    /**
     * recherche les chats par Race
     * @param race
     * @return
     */
    public List<Chat> rechercherParRace(String race){
        return chatRepository.findAllByRace(race);
    }

    /**
     * recherche les chats par prix
     * @param prixDu
     * @param prixAu
     * @return
     */
    public List<Chat> rechercherParPrix(Double prixDu, Double prixAu){
        if(prixDu != null && prixAu != null){
            return chatRepository.findAllByPrixBetween(prixDu, prixAu);
        }else if(prixDu != null){
            return chatRepository.findAllByPrixGreaterThanEqual(prixDu);
        }else if(prixAu != null) {
            return chatRepository.findAllByPrixLessThanEqual(prixAu);
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * recherche les chats par age
     * @param ageDebut
     * @param ageFin
     * @return
     */
    public List<Chat> rechercherParAge(Integer ageDebut, Integer ageFin){

        if(ageDebut != null && ageFin != null){
            return chatRepository.findAllByDateNaissanceBetween(getDateByAge(ageFin), getDateByAge(ageDebut));
        }else if(ageDebut != null){
            return chatRepository.findAllByDateNaissanceLessThanEqual(getDateByAge(ageDebut));
        }else if(ageFin != null) {
            return chatRepository.findAllByDateNaissanceGreaterThanEqual(getDateByAge(ageFin));
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * recherche les chats par page
     * @param pageable
     * @return
     */
    public Page<Chat> rechercheParPage(Pageable pageable) {
        return chatRepository.findAll(pageable);
    }


    /**
     * Ajout chat
     * @param chat
     * @return
     */
    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    /**
     * mise à jour chat
     * @param chat
     * @return
     */
    public Chat updateChat(Chat chat) {
        return chatRepository.save(chat);
    }

    /**
     * suppression chat par id
     * @param id
     */
    public void deleteChat(Integer id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if(chat.isPresent()) {
            chatRepository.delete(chat.get());
        }
    }

    /**
     * import d'un fichier csv en base
     * @param file
     */
    public void saveCSVFile(MultipartFile file) {
        try {
            List<Chat> chats = CSVHelper.csvToChats(file.getInputStream());
            chatRepository.saveAll(chats);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /**
     * recupere la date de naissance à partir de l'age
     * @param age
     * @return
     */
    private Date getDateByAge(int age) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, age *-1);
        return new Date(cal.getTimeInMillis());
    }
}
