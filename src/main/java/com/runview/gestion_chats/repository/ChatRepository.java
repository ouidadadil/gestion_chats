package com.runview.gestion_chats.repository;

import com.runview.gestion_chats.model.Chat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ChatRepository extends PagingAndSortingRepository<Chat, Integer> {
    List<Chat> findAllByRace(String race);
    List<Chat> findAllByPrixGreaterThanEqual(Double prix);
    List<Chat> findAllByPrixBetween(Double prixinf, Double prixsupp);
    List<Chat> findAllByPrixLessThanEqual(Double prix);

    List<Chat> findAllByDateNaissanceGreaterThanEqual(Date dateNaissance);
    List<Chat> findAllByDateNaissanceBetween(Date dateNaissance1, Date dateNaissance2);
    List<Chat> findAllByDateNaissanceLessThanEqual(Date dateNaissance);
}
