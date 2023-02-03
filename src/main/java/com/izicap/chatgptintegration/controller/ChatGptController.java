package com.izicap.chatgptintegration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izicap.chatgptintegration.service.ChatGptQueryApi;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@RestController
@RequestMapping("/chat-gpt/api")
public class ChatGptController {

    @Autowired
    private ChatGptQueryApi chatGptService;

    @PostMapping("/ask")
    public ResponseEntity<String> ask(@RequestBody String question) throws IOException {

        char SEMICOLON_SEPARATOR = ';';
        String outputFilePath = "csv-data/questions_answers.csv";
        File file = new File(outputFilePath);
        Boolean fileExists = file.exists();


        // Spring wraps the question in a body object, so we need to get the question value
        ObjectMapper mapper = new ObjectMapper();
        String questionValue = mapper.readTree(question).get("question").textValue();

        String answer=chatGptService.ask(questionValue);

        try (

                CSVWriter csvWriter = new CSVWriter(
                    new FileWriter(outputFilePath, true),
                    SEMICOLON_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END
            )
        ){
            if (!fileExists) {
                csvWriter.writeNext(new String[]{"Question", "Answer"});
            }

            String[] row = {questionValue, answer};
            // append row to the CSV file
            csvWriter.writeNext(row);
        }

        return ResponseEntity.ok(answer);
    }

}
