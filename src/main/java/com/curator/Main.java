package com.curator;

import com.wrapper.spotify.*;
import com.curator.controllers.MainController;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    static String clientId = "f9c190003d09495d9915681495281934";
    static String clientSecret = "e254b4a83ce949a0b235ff45601ae6fc";
    static String accessToken = "QAJck-yRaqAqLTA-mlkLiQ5CkgJ8grYNxwTPnXyBGzIegJa_IiWbIUvLJ0ukclzX6Cpq3tseTQP-HYUHGU";
    public static SpotifyApi api =  new SpotifyApi.Builder()
            .setAccessToken(getAccessToken(clientId, clientSecret))
//            .setAccessToken(accessToken)
            .build();

    @java.lang.Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setApi(api);

        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();

        //prevent window becoming too small
        stage.setMinHeight(600);
        stage.setMinWidth(1200);
    }

    private static String getAccessToken(String clientId, String clientSecret){
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials() .build();

        try{
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            return clientCredentials.getAccessToken();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}
