package controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.files.JogadoresFile;
import services.files.SelecoesFile;

public class ControllerCadastroJogadores {

    @FXML private TextField nomeJogadorCadastrado;
    @FXML private TextField idadeJogadorCadastrado;
    @FXML private ComboBox<Selecoes> selecaoJogadorCadastrado;
    @FXML private CheckBox lesionadoJogadorCadastrado;
    @FXML private CheckBox suspensoJogadorCadastrado;
    @FXML private Label mensagemCamposIncompletos;

    private final JogadoresFile jogadoresFile = JogadoresFile.getInstancia();

    @FXML
    public void initialize(){
        /*Inicializando a lista de selecoes no ComboBox*/
        ObservableList<Selecoes> listaSelecoes = FXCollections.observableArrayList(SelecoesFile.getInstance().getListaSelecoes());
        selecaoJogadorCadastrado.setItems(listaSelecoes);
    }

    @FXML
    private void adicionarJogador(){

        try{
            String nomeJogadorCadastradaString = nomeJogadorCadastrado.getText();
            String idadeJogadorCadastradoString = idadeJogadorCadastrado.getText();
            Selecoes selecaoJogadorCadastradaSelecoes = selecaoJogadorCadastrado.getValue();
            boolean lesionadoJogadorCadastradoBoolean = lesionadoJogadorCadastrado.isSelected();
            boolean suspensoJogadorCadastradoBoolean = suspensoJogadorCadastrado.isSelected();

            if(nomeJogadorCadastradaString.isEmpty() || selecaoJogadorCadastradaSelecoes == null || idadeJogadorCadastradoString.isEmpty()){
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
            }

            /*checando se a idade digitada eh um numero*/
            int idadeJogadorCadastradoInt;
            try{
                idadeJogadorCadastradoInt = Integer.parseInt(idadeJogadorCadastradoString);
            }catch (NumberFormatException i){
                throw new IllegalArgumentException("Idade deve ser um numero");
            }

            Jogadores novoJogador = new Jogadores(nomeJogadorCadastradaString, idadeJogadorCadastradoInt, selecaoJogadorCadastradaSelecoes, lesionadoJogadorCadastradoBoolean, suspensoJogadorCadastradoBoolean);
            jogadoresFile.getListaJogadores().add(novoJogador);
            jogadoresFile.salvarNoTxt();

           /*
            SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");
            */
            nomeJogadorCadastrado.clear();
            idadeJogadorCadastrado.clear();
            selecaoJogadorCadastrado.setValue(null);
            lesionadoJogadorCadastrado.setSelected(false);
            suspensoJogadorCadastrado.setSelected(false);
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
