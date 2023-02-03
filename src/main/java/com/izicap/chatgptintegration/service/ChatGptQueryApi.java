package com.izicap.chatgptintegration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Service
public class ChatGptQueryApi {

    private String userApiKey = "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";

    private OkHttpClient client = new OkHttpClient();

    public String ask(String question) throws IOException {

        // TODO : mettre ces valeurs dans application.properties
        String apiUrl = "https://api.openai.com/v1/completions";
        String contentType = "application/json";
        String token = "Bearer " + userApiKey;

        JSONObject contentObj = new JSONObject();
        contentObj.put("model", "text-davinci-003");
        // pour échapper les caractère spéciaux
        contentObj.put("prompt", question);
        contentObj.put("max_tokens", 4000);
        contentObj.put("temperature", 1.0);

        RequestBody body = RequestBody.create(
                MediaType.parse(contentType),
                contentObj.toString()
        );

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Content-Type", contentType)
                .addHeader("Authorization", token)
                .build();

        try(Response response = client.newCall(request).execute()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootJson = mapper.readTree(response.body().string());
            String answer = rootJson.get("choices").get(0).get("text").textValue().trim();
            return answer;
        }
    }
}
