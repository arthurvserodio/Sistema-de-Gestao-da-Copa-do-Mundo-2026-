package controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import nationsAndPlayers.nations.Selecoes;
import services.files.SelecoesFile;

public class ControllerCadastroSelecoes {

    @FXML private TextField nomeSelecaoCadastrada;
    @FXML private TextField rankingSelecaoCadastrada;
    @FXML private TextField grupoSelecaoCadastrada;
    @FXML private TextField participacaoSelecaoCadastrada;
    @FXML private TextField titulosSelecaoCadastrada;
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

            nomeSelecaoCadastrada.clear();
            rankingSelecaoCadastrada.clear();
            grupoSelecaoCadastrada.clear();
            participacaoSelecaoCadastrada.clear();
            titulosSelecaoCadastrada.clear();
        }catch(IllegalArgumentException e){
            mostrarErro(e.getMessage());
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
