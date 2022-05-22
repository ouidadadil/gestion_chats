package com.runview.gestion_chats.helper;

import com.runview.gestion_chats.enums.Sexe;
import com.runview.gestion_chats.mappers.ChatMapper;
import com.runview.gestion_chats.model.Chat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";

    /**
     * verifie le type de fichier
     * @param file
     * @return
     */
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    /**
     * écris le foichier csv en base
     * @param is
     * @return
     */
    public static List<Chat> csvToChats(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withDelimiter(';').withQuote('"').withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Chat> chats = new ArrayList<Chat>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            ChatMapper.cSVRecordToChats(chats, csvRecords);
            return chats;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


}
