package controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import nationsAndPlayers.nations.Selecoes;
import services.files.SelecoesFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ControllerCadastroSelecoes {

    @FXML private TextField nomeSelecaoCadastrada;
    @FXML private TextField rankingSelecaoCadastrada;
    @FXML private TextField grupoSelecaoCadastrada;
    @FXML private TextField participacaoSelecaoCadastrada;
    @FXML private TextField titulosSelecaoCadastrada;
    @FXML private TextField tecnicoSelecaoCadastrada;
    @FXML private Circle visualizacaoImagemDaSelecaoCadastrada;
    @FXML private Label mensagemCamposIncompletos;

    private final SelecoesFile selecaoFile = SelecoesFile.getInstance();

    @FXML
    private void adicionarSelecao(){

        try{
            String nomeSelecaoCadastradaString = nomeSelecaoCadastrada.getText();
            String rankingSelecaoCadastradaString = rankingSelecaoCadastrada.getText();
            String grupoSelecaoCadastradaString = grupoSelecaoCadastrada.getText();
            String participacaoSelecaoCadastradaString = participacaoSelecaoCadastrada.getText();
            String titulosSelecaoCadastradaString = titulosSelecaoCadastrada.getText();

            if(nomeSelecaoCadastradaString.isEmpty() || rankingSelecaoCadastradaString.isEmpty() || grupoSelecaoCadastradaString.isEmpty() || participacaoSelecaoCadastradaString.isEmpty() || titulosSelecaoCadastradaString.isEmpty()){
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
            }
            Selecoes novaSelecao = new Selecoes(nomeSelecaoCadastradaString, grupoSelecaoCadastradaString, rankingSelecaoCadastradaString, participacaoSelecaoCadastradaString, titulosSelecaoCadastradaString);
            selecaoFile.getListaSelecoes().add(novaSelecao);
            selecaoFile.salvarNoTxt();

            /*chama o metodo do controller de mudar a tela para chamar o iniciatilize que chama o mostraSelecao para atualizar a lista*/
            SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");

            nomeSelecaoCadastrada.clear();
            rankingSelecaoCadastrada.clear();
            grupoSelecaoCadastrada.clear();
            participacaoSelecaoCadastrada.clear();
            titulosSelecaoCadastrada.clear();
            tecnicoSelecaoCadastrada.clear();
        }catch(IllegalArgumentException e){
            mostrarErro(e.getMessage());
        }
    }

    @FXML
    private void adicionarImagemDaSelecaoCadastrada(){
        FileChooser arquivoAdicionado = new FileChooser();

        arquivoAdicionado.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png")
        );

        Stage stage = (Stage) visualizacaoImagemDaSelecaoCadastrada.getScene().getWindow();
        File file = arquivoAdicionado.showOpenDialog(stage);
        if(file != null){
            Image imagem = new Image(file.toURI().toString());
            visualizacaoImagemDaSelecaoCadastrada.setFill(new ImagePattern(imagem));
            /*logica de salvar a imagem no diretorio*/
                try{
                    File destino = new File("target/classes/images/Logos/" + nomeSelecaoCadastrada.getText().toLowerCase() + ".png");
                    System.out.println(destino.getAbsolutePath());
                    Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }catch(IOException e){
                    e.printStackTrace();
                }

                /*printando para debugacao*/
                for (Selecoes s : SelecoesFile.getInstance().getListaSelecoes()) { //alteracao para usar a lista final do SelecoesFile
                    System.out.println(s.getNome());
                }
            }
        }


    private void mostrarErro(String str) {
        mensagemCamposIncompletos.setText(str);
        mensagemCamposIncompletos.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 14px; -fx-font-weight: bold;");
        mensagemCamposIncompletos.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void tornarMensagemTemporaria() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), mensagemCamposIncompletos);
        fade.setFromValue(1.0); // Totalmente visível
        fade.setToValue(0.0);   // Totalmente invisível


        fade.setDelay(Duration.seconds(2.0));


        fade.setOnFinished(event -> mensagemCamposIncompletos.setText(""));

        fade.play(); // Inicia o temporizador/efeito
    }

}
