package com.infinity.omos.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infinity.omos.dto.AlbumsDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAllSearchApi {

    public static List<AlbumsDto> requestGeniusSearchApi(String accessToken, String type, String keyword) {

        List<AlbumsDto> albumsDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q="+keyword+"&type=" + type + "&market=KR"+"&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(keyword);

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);


            conn.setDoOutput(true);
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

            JsonObject tracks = object.get("tracks").getAsJsonObject();
            JsonArray items = tracks.get("items").getAsJsonArray();


            for (int i = 0; i < items.size(); i++) {
                AlbumsDto albumsDto = new AlbumsDto();
                JsonObject album = items.get(i).getAsJsonObject().get("album").getAsJsonObject();
                JsonObject artists = album.get("artists").getAsJsonArray().get(0).getAsJsonObject();
                albumsDto.setArtist(artists.get("name").getAsString());
                albumsDto.setArtistId(artists.get("id").getAsString());
                albumsDto.setAlbumId(album.get("id").getAsString());

                JsonObject images = album.get("images").getAsJsonArray().get(1).getAsJsonObject();
                albumsDto.setAlbumImageUrl(images.get("url").getAsString());

                albumsDto.setAlbumName(album.get("name").getAsString());
                albumsDto.setReleaseDate(album.get("release_date").getAsString());


                albumsDto.setMusicId(items.get(i).getAsJsonObject().get("id").getAsString());
                albumsDto.setTitle(items.get(i).getAsJsonObject().get("name").getAsString());
                albumsDtos.add(albumsDto);


                br.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return albumsDtos;

    }
}
