package com.infinity.omos.api;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RequestGeniusApi {
    public void requestGeniusSearchApi(String keyword) throws IOException {


        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://genius.p.rapidapi.com/search?q=" + keyword;


        URL url = new URL(reqURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        System.out.println(keyword);

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("x-rapidapi-host", "genius.p.rapidapi.com");
        conn.setRequestProperty("x-rapidapi-key", "3601641914mshb1b4a6cad9436eep140592jsn6f3011f78817");
        conn.setDoOutput(false);
        //결과 코드가 200이라면 성공
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);

        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String line = "";
        StringBuilder result = new StringBuilder();

        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        System.out.println("response body : " + result);

        //Gson 라이브러리로 JSON파싱

        JsonElement element = JsonParser.parseString(result.toString());
        JsonObject object = element.getAsJsonObject();

        JsonObject response = object.get("response").getAsJsonObject();
        JsonArray hits = response.get("hits").getAsJsonArray();
        for (int i = 0; i < hits.size(); i++) {
            JsonObject results = hits.get(i).getAsJsonObject().get("result").getAsJsonObject();

            String artist = results.get("artist_names").getAsString();
            long id = results.get("id").getAsLong();
            String thumbnail = results.get("header_image_thumbnail_url").getAsString();
            String title = results.get("title").getAsString();
        }


        br.close();

    }
}
