package controller;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
//Lógica de implementação da troca de tela; Criado para evitar repetição de código nos controllers(reuso)
public class SceneController {
    private static Stage stage;

    public static void setStage(Stage s){
        stage=s;
        stage.setTitle("Copa do Mundo");
        stage.initStyle(StageStyle.UNDECORATED);
    }
    //Método responsavel pela troca de tela
    //Carrega um novo arquivo FXML,atráves do seu caminho,o qual foi envido como parametro do metodo, e coloca ele na tela principal(a que está sendo visualizada)
    public static void mudaDeTela(String FXMLpath){
        try{
            //Carrega o FXML na tela principal
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(FXMLpath));
            Parent root = loader.load();
            //Se não existir uma cena no stage, ele cria uma com o tamanho padrão de 1600x900
            if(stage.getScene()==null){
                stage.setScene(new Scene(root, 1600, 900));
            }
            //Se já existir uma cena,ele apenas troca o conteudo
            else{stage.getScene().setRoot(root);}
            stage.show();
        } catch (IOException e) {
            //tratamento de erro caso o FXML não seja encontrado ou deu falha no carregamento
            System.err.println("Falha ao trocar a cena para: " + FXMLpath);
            e.printStackTrace();
        }
    }
}