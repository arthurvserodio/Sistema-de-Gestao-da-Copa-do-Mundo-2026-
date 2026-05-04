package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import controller.SceneController;

public class SiteRodando extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Carregando as fontes(nao presentes no SceneBuilder) baixadas no executavel
        Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter_18pt-Medium.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Inter_18pt-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto_Condensed-Black.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto_Condensed-Bold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto_Condensed-ExtraLight.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto_Condensed-Medium.ttf"), 12);
        //Carregando a tela
        SceneController.setStage(stage);
        SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
