package com.infinity.omos.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infinity.omos.dto.*;

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


    public static List<AlbumDto> spotifyAlbumSearchApi(String accessToken, String keyword, int offset, int limit) {
        List<AlbumDto> albumDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q=" + keyword + "&type=album" + "&market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7" + "&offset=" + offset + "&limit=" + limit;

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
            albumFrame(object, albumDtos);

            br.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return albumDtos;

    }

    public static List<TrackDto> spotifyTrackSearchApi(String accessToken, String keyword, int offset, int limit, int type) {

        List<TrackDto> trackDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q=" + keyword + "&type=track" + "&market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7" + "&offset=" + offset + "&limit=" + limit;

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

            if(type==1){
                trackFrameOnTrackName(object,trackDtos);
            }
            else {
                trackFrame(object, trackDtos);
            }


            br.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return trackDtos;

    }

    public static List<ArtistDto> spotifyArtistSearchApi(String accessToken, String keyword, int offset, int limit) {
        List<ArtistDto> artistDtos = new ArrayList<>();

        keyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String reqURL = "https://api.spotify.com/v1/search?q=" + keyword + "&type=artist" + "&market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7" + "&offset=" + offset + "&limit=" + limit;

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
            System.out.println(result.toString());

            //Gson 라이브러리로 JSON파싱

            JsonElement element = JsonParser.parseString(result.toString());
            JsonObject object = element.getAsJsonObject();

            artistsFrame(object, artistDtos);
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return artistDtos;

    }

    public static TrackDto getTrackApi(String accessToken, String id) {
        TrackDto trackDto = new TrackDto();

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
            trackDto.setAlbumId(album.get("id").getAsString());
            JsonArray imageList = album.get("images").getAsJsonArray();
            if (imageList.isEmpty()) {
                trackDto.setAlbumImageUrl(null);
            } else {
                trackDto.setAlbumImageUrl(imageList.get(1).getAsJsonObject().get("url").getAsString());
            }
            trackDto.setAlbumTitle(album.get("name").getAsString());
            trackDto.setReleaseDate(album.get("release_date").getAsString());

            JsonArray artists = object.get("artists").getAsJsonArray();
            List<Artists> artists1 = new ArrayList<>();
            for (int j = 0; j < artists.size(); j++) {
                Artists artists2 = new Artists();
                artists2.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                artists2.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                artists1.add(artists2);
            }
            trackDto.setArtists(artists1);

            trackDto.setMusicTitle(object.get("name").getAsString());
            trackDto.setMusicId(object.get("id").getAsString());

            br.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: spotifyAPI오류 " + e.getMessage());
        }

        return trackDto;
    }

    public static List<AlbumTrackDto> getAlbumTrackApi(String accessToken, String id) {
        List<AlbumTrackDto> albumTrackDtos = new ArrayList<>();

        String reqURL = "https://api.spotify.com/v1/albums/" + id + "/tracks?market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

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

            JsonArray items = object.get("items").getAsJsonArray();
            for (int i = 0; i < items.size(); i++) {
                AlbumTrackDto albumTrackDto = new AlbumTrackDto();
                JsonObject item = items.get(i).getAsJsonObject();
                JsonArray artists = item.get("artists").getAsJsonArray();
                List<Artists> artistsList = new ArrayList<>();
                for (int j = 0; j < artists.size(); j++) {
                    Artists artist = new Artists();
                    artist.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                    artist.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                    artistsList.add(artist);
                }
                albumTrackDto.setArtists(artistsList);
                albumTrackDto.setMusicId(item.get("id").getAsString());
                albumTrackDto.setMusicTitle(item.get("name").getAsString());

                albumTrackDtos.add(albumTrackDto);

            }


            br.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: spotifyAPI오류 " + e.getMessage());
        }

        return albumTrackDtos;
    }

    public static List<HotTrackDto> getHotTracksApi(String accessToken, String id) {
        List<HotTrackDto> hotTrackDtos = new ArrayList<>();

        String reqURL = "https://api.spotify.com/v1/artists/" + id + "/top-tracks?market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7";

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

            JsonArray tracks = object.get("tracks").getAsJsonArray();
            for (int i = 0; i < tracks.size(); i++) {
                HotTrackDto hotTrackDto = new HotTrackDto();
                JsonObject item = tracks.get(i).getAsJsonObject();
                JsonObject album = item.getAsJsonObject("album");
                JsonArray images = album.get("images").getAsJsonArray();
                if (images.isEmpty()) {
                    hotTrackDto.setAlbumImageUrl(null);
                } else {
                    hotTrackDto.setAlbumImageUrl(images.get(0).getAsJsonObject().get("url").getAsString());
                }

                JsonArray artists = item.getAsJsonArray("artists");
                List<String> artistName = new ArrayList<>();
                for (int j = 0; j < artists.size(); j++) {
                    artistName.add(artists.get(j).getAsJsonObject().get("name").getAsString());
                }
                hotTrackDto.setArtistName(artistName);

                hotTrackDto.setMusicTitle(item.get("name").getAsString());
                hotTrackDto.setMusicId(item.get("id").getAsString());


                hotTrackDtos.add(hotTrackDto);

            }


            br.close();
        } catch (IOException e) {
            System.out.println("Error: spotifyAPI오류 " + e.getMessage());
        }

        return hotTrackDtos;

    }

    public static List<AlbumDto> getArtistsAlbum(String accessToken, String id, int offset, int limit) {
        List<AlbumDto> albumDtoList = new ArrayList<>();

        String reqURL = "https://api.spotify.com/v1/artists/" + id + "/albums?market=KR" + "&locale=ko-KR%2Cko%3Bq%3D0.9%2Cen-US%3Bq%3D0.8%2Cen%3Bq%3D0.7" + "&offset=" + offset + "&limit=" + limit;

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

            JsonArray items = object.get("items").getAsJsonArray();
            for (int i = 0; i < items.size(); i++) {
                AlbumDto albumDto = new AlbumDto();
                JsonObject item = items.get(i).getAsJsonObject();

                JsonArray images = item.getAsJsonArray("images");
                if (images.isEmpty()) {
                    albumDto.setAlbumImageUrl(null);
                } else {
                    albumDto.setAlbumImageUrl(images.get(0).getAsJsonObject().get("url").getAsString());
                }

                JsonArray artists = item.getAsJsonArray("artists");
                List<Artists> artistName = new ArrayList<>();
                for (int j = 0; j < artists.size(); j++) {
                    Artists artist = new Artists();
                    artist.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                    artist.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                    artistName.add(artist);
                }
                albumDto.setArtists(artistName);

                albumDto.setAlbumTitle(item.get("name").getAsString());
                albumDto.setAlbumId(item.get("id").getAsString());
                albumDto.setReleaseDate(item.get("release_date").getAsString());


                albumDtoList.add(albumDto);

            }


            br.close();
        } catch (IOException e) {
            System.out.println("Error: spotifyAPI오류 " + e.getMessage());
        }

        return albumDtoList;

    }

    public static List<TrackDto> trackFrame(JsonObject object, List<TrackDto> trackDtos) {

        JsonObject tracks = object.get("tracks").getAsJsonObject();
        JsonArray items = tracks.get("items").getAsJsonArray();


        for (int i = 0; i < items.size(); i++) {
            TrackDto trackDto = new TrackDto();
            JsonObject album = items.get(i).getAsJsonObject().get("album").getAsJsonObject();
            JsonArray artists = album.get("artists").getAsJsonArray();
            List<Artists> artists1 = new ArrayList<>();
            for (int j = 0; j < artists.size(); j++) {
                Artists artists2 = new Artists();
                artists2.setArtistName(artists.get(j).getAsJsonObject().get("name").getAsString());
                artists2.setArtistId(artists.get(j).getAsJsonObject().get("id").getAsString());
                artists1.add(artists2);
            }
            trackDto.setArtists(artists1);
            trackDto.setAlbumId(album.get("id").getAsString());

            JsonArray images = album.get("images").getAsJsonArray();
            if (images.isEmpty()) {
                trackDto.setAlbumImageUrl(null);
            } else {
                trackDto.setAlbumImageUrl(images.get(1).getAsJsonObject().get("url").getAsString());
            }

            trackDto.setAlbumTitle(album.get("name").getAsString());
            trackDto.setReleaseDate(album.get("release_date").getAsString());


            trackDto.setMusicId(items.get(i).getAsJsonObject().get("id").getAsString());
            trackDto.setMusicTitle(items.get(i).getAsJsonObject().get("name").getAsString());
            trackDtos.add(trackDto);


        }

        return trackDtos;
    }

    public static List<TrackDto> trackFrameOnTrackName(JsonObject object, List<TrackDto> trackDtos) {

        JsonObject tracks = object.get("tracks").getAsJsonObject();
        JsonArray items = tracks.get("items").getAsJsonArray();


        for (int i = 0; i < items.size(); i++) {
            TrackDto trackDto = new TrackDto();
            trackDto.setMusicTitle(items.get(i).getAsJsonObject().get("name").getAsString());
            trackDtos.add(trackDto);


        }

        return trackDtos;
    }

    public static List<ArtistDto> artistsFrame(JsonObject object, List<ArtistDto> artistDtos) {

        JsonObject artists = object.get("artists").getAsJsonObject();
        JsonArray items = artists.get("items").getAsJsonArray();


        for (int i = 0; i < items.size(); i++) {
            ArtistDto artistDto = new ArtistDto();
            JsonArray genres = items.get(i).getAsJsonObject().get("genres").getAsJsonArray();
            List<String> genre = new ArrayList<>();
            for (int j = 0; j < genres.size(); j++) {
                genre.add(genres.get(j).getAsString());
            }
            artistDto.setGenres(genre);


            JsonArray imagesArray = items.get(i).getAsJsonObject().get("images").getAsJsonArray();
            if (imagesArray.isEmpty()) {
                artistDto.setArtistImageUrl(null);
            } else {
                artistDto.setArtistImageUrl(imagesArray.get(0).getAsJsonObject().get("url").getAsString());
            }


            artistDto.setArtistId(items.get(i).getAsJsonObject().get("id").getAsString());
            artistDto.setArtistName(items.get(i).getAsJsonObject().get("name").getAsString());
            artistDtos.add(artistDto);


        }
        return artistDtos;
    }

    public static List<AlbumDto> albumFrame(JsonObject object, List<AlbumDto> albumDtos) {
        JsonObject albums = object.get("albums").getAsJsonObject();
        JsonArray items = albums.get("items").getAsJsonArray();


        for (int i = 0; i < items.size(); i++) {
            AlbumDto albumDto = new AlbumDto();
            JsonArray artists = items.get(i).getAsJsonObject().get("artists").getAsJsonArray();
            List<Artists> artistsList = new ArrayList<>();
            for (int j = 0; j < artists.size(); j++) {
                JsonObject artist = artists.get(j).getAsJsonObject();
                artistsList.add(
                        Artists.builder()
                                .artistId(artist.get("id").getAsString())
                                .artistName(artist.get("name").getAsString())
                                .build()
                );
            }
            albumDto.setArtists(artistsList);


            JsonArray imagesList = items.get(i).getAsJsonObject().get("images").getAsJsonArray();
            if (imagesList.isEmpty()) {
                albumDto.setAlbumImageUrl(null);
            } else {
                albumDto.setAlbumImageUrl(imagesList.get(1).getAsJsonObject().get("url").getAsString());
            }


            albumDto.setAlbumId(items.get(i).getAsJsonObject().get("id").getAsString());
            albumDto.setAlbumTitle(items.get(i).getAsJsonObject().get("name").getAsString());
            albumDto.setReleaseDate(items.get(i).getAsJsonObject().get("release_date").getAsString());
            albumDtos.add(albumDto);

        }
        return albumDtos;
    }
}
