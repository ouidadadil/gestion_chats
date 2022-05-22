package com.runview.gestion_chats.controller;

import com.runview.gestion_chats.helper.CSVHelper;
import com.runview.gestion_chats.model.Chat;
import com.runview.gestion_chats.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ChatController {

    private static final int ELEMENT_PER_PAGE = 5;

    @Autowired
    ChatService chatService;

    /**
     * upload un fichier csv contant la liste de chats
     * @param file
     * @return
     */
    @PostMapping("/chats/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                chatService.saveCSVFile(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    /**
     * recherche les chats par page
     * @param page
     * @return
     */
    @GetMapping("/chats/{page}")
    public ResponseEntity<List<Chat>> getAllChats(@PathVariable(required = true) int page) {
        try {
            Pageable pageable = PageRequest.of(page, ELEMENT_PER_PAGE);
            Page<Chat> pchats = chatService.rechercheParPage(pageable);
            List<Chat> chats = pchats.stream().collect(Collectors.toList());

            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * recherche les chats par race
     * @param race
     * @return
     */
    @GetMapping("/chats/race/{race}")
    public ResponseEntity<List<Chat>> getAllByRace(@PathVariable(required = true) String race) {
        try {
            List<Chat> chats = new ArrayList<>();
            if (race != null)
                chats = chatService.rechercherParRace(race);

            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * recherche les chats par prix
     * @param priceDu
     * @param priceAu
     * @return
     */
    @GetMapping("/chats/price")
    public ResponseEntity<List<Chat>> getAllByPrice(@RequestParam(required = false) Double priceDu, @RequestParam(required = false) Double priceAu) {
        try {
            List<Chat> chats = chatService.rechercherParPrix(priceDu, priceAu);

            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * recherche les chats par age
     * @param ageDu
     * @param ageAu
     * @return
     */
    @GetMapping("/chats/age")
    public ResponseEntity<List<Chat>> getAllByAge(@RequestParam(required = false) Integer ageDu, @RequestParam(required = false) Integer ageAu) {
        try {
            List<Chat>  chats = chatService.rechercherParAge(ageDu, ageAu);

            if (chats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Ajout chat
     * @param chat
     * @return
     */
    @PostMapping("/chats/add")
    public ResponseEntity<Chat> addChat(@RequestBody Chat chat) {
        try {
            chatService.addChat(chat);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * mise à jour chat
     * @param chat
     * @return
     */
    @PostMapping("/chats/update")
    public ResponseEntity<Chat> updateChat(@RequestBody Chat chat) {
        try {
            chatService.updateChat(chat);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Suppression chat
     * @param id
     * @return
     */
    @PostMapping("/chats/delete/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable(required = true) Integer id) {
        try {
            chatService.deleteChat(id);
            return new ResponseEntity<>("Suppression OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
