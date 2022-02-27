package com.infinity.omos.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infinity.omos.dto.AlbumsDto;
import com.infinity.omos.dto.ArtistDto;
import com.infinity.omos.dto.Artists;

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

    public static List<AlbumsDto> spotifyTrackSearchApi(String accessToken, String keyword) {

        List<AlbumsDto> albumsDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q=" + keyword + "&type=track" + "&market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

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
                JsonArray artists = album.get("artists").getAsJsonArray();
                List<Artists> artists1 = new ArrayList<>();
                for (int j = 0; j < artists.size(); j++) {
                    Artists artists2 = new Artists();
                    artists2.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                    artists2.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                    artists1.add(artists2);
                }
                albumsDto.setArtists(artists1);
                albumsDto.setAlbumId(album.get("id").getAsString());

                JsonObject images = album.get("images").getAsJsonArray().get(1).getAsJsonObject();
                albumsDto.setAlbumImageUrl(images.get("url").getAsString());

                albumsDto.setAlbumTitle(album.get("name").getAsString());
                albumsDto.setReleaseDate(album.get("release_date").getAsString());


                albumsDto.setMusicId(items.get(i).getAsJsonObject().get("id").getAsString());
                albumsDto.setMusicTitle(items.get(i).getAsJsonObject().get("name").getAsString());
                albumsDtos.add(albumsDto);


            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return albumsDtos;

    }

    public static List<ArtistDto> spotifyArtistSearchApi(String accessToken, String keyword) {
        List<ArtistDto> artistDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q=" + keyword + "&type=artist" + "&market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

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

            JsonObject artists = object.get("artists").getAsJsonObject();
            JsonArray items = artists.get("items").getAsJsonArray();


            for (int i = 0; i < items.size(); i++) {
                ArtistDto artistDto = new ArtistDto();
                JsonArray genres = items.get(i).getAsJsonObject().get("genres").getAsJsonArray();
                List<String> genre = new ArrayList<>();
                for (int j = 0; i < genres.size(); j++) {
                    genre.add(genres.get(j).getAsString());
                }
                artistDto.setGenres(genre);


                JsonObject images = items.get(i).getAsJsonObject().get("images").getAsJsonArray().get(1).getAsJsonObject();
                artistDto.setArtistImageUrl(images.get("url").getAsString());


                artistDto.setArtistId(items.get(i).getAsJsonObject().get("id").getAsString());
                artistDto.setArtistName(items.get(i).getAsJsonObject().get("name").getAsString());
                artistDtos.add(artistDto);


            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return artistDtos;

    }

    public static AlbumsDto getTrackApi(String accessToken, String id) {
        AlbumsDto albumsDto = new AlbumsDto();

        String reqURL = "https://api.spotify.com/v1/tracks/" + id + "?market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

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

            //Gson 라이브러리로 JSON파싱


            JsonElement element = JsonParser.parseString(result.toString());
            JsonObject object = element.getAsJsonObject();

            JsonObject album = object.get("album").getAsJsonObject();
            albumsDto.setAlbumId(album.get("id").getAsString());
            albumsDto.setAlbumImageUrl(album.get("images").getAsJsonArray().get(1).getAsJsonObject().get("url").getAsString());
            albumsDto.setAlbumTitle(album.get("name").getAsString());
            albumsDto.setReleaseDate(album.get("release_date").getAsString());

            JsonArray artists = object.get("artists").getAsJsonArray();
            List<Artists> artists1 = new ArrayList<>();
            for (int j = 0; j < artists.size(); j++) {
                Artists artists2 = new Artists();
                artists2.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                artists2.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                artists1.add(artists2);
            }
            albumsDto.setArtists(artists1);

            albumsDto.setMusicTitle(object.get("name").getAsString());
            albumsDto.setMusicId(object.get("id").getAsString());

            br.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: spotifyAPI오류 " + e.getMessage());
        }

        return albumsDto;
    }
}
