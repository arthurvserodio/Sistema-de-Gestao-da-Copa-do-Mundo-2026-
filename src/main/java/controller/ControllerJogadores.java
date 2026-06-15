package controller;

import Enums.Funcao;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import services.files.JogadoresFile;
import services.files.SelecoesFile;
import users.Sessao;
import users.Usuario;

import java.io.IOException;
import java.io.InputStream;

public class ControllerJogadores {

    private Selecoes selecao;
    private final JogadoresFile jogadoresFile = JogadoresFile.getInstancia();
    private Jogadores jogadorSelecionado;

    @FXML private Button botaoAdicionarJogador;
    @FXML private Button botaoRemoverJogador;
    @FXML private ImageView logo;
    @FXML private TableView <Jogadores> tabelaJogadores;
    @FXML private TableColumn<Jogadores,String> jogadorColuna;
    @FXML private TableColumn<Jogadores,Integer> idadeColuna;
    @FXML private TableColumn<Jogadores,String> posicaoColuna;
    @FXML private Label labelMensagem;

    public void initialize() {
        SelecoesFile.getInstance().getListaSelecoes();
        //Mostra as seleções na tela
        //Toda vez que for escrito alguma coisa no textField ele chama a função de pesquisa
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            if(u.getFuncao() == Funcao.ADMINISTRADOR || u.getFuncao() == Funcao.ORGANIZADOR){
                botaoAdicionarJogador.setVisible(true);
                botaoRemoverJogador.setVisible(true);
            }
        }

        tabelaJogadores.setOnMouseClicked(eventoDeClique -> {
           if(eventoDeClique.getClickCount() == 1){
               jogadorSelecionado = tabelaJogadores.getSelectionModel().getSelectedItem();
           }
        });
/*
        jogadorColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idadeColuna.setCellValueFactory(new PropertyValueFactory<>("idade"));
        posicaoColuna.setCellValueFactory(new PropertyValueFactory<>("posicao"));

        ObservableList<Jogadores> listaJogadores = FXCollections.observableArrayList(JogadoresFile.getInstancia().getListaJogadores());
        tabelaJogadores.setItems(listaJogadores);
        */
    }

    public void setSelecoes(Selecoes selecao){
        this.selecao = selecao;
        carregarDados(selecao);
    }
    private void carregarDados(Selecoes selecao){
        carregarImagem();
        carregarJogadores(selecao);
    }

    private void carregarJogadores(Selecoes selecao){
        jogadorColuna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idadeColuna.setCellValueFactory(new PropertyValueFactory<>("idade"));
        posicaoColuna.setCellValueFactory(new PropertyValueFactory<>("posicao"));

        ObservableList<Jogadores> listaJogadoresFiltrada = FXCollections.observableArrayList();
        for(Jogadores jogadores: JogadoresFile.getInstancia().getListaJogadores()){
            if(jogadores.getSelecao() != null){
                if(jogadores.getSelecao().getNome().equals(selecao.getNome())){
                listaJogadoresFiltrada.add(jogadores);
                }
            }
        }
        tabelaJogadores.setItems(listaJogadoresFiltrada);
    }

    private void carregarImagem(){
        String caminhoImagem = "/images/Logos/" + selecao.getNome().toLowerCase().replace(" ", "_") + ".png";
        InputStream imagem = getClass().getResourceAsStream(caminhoImagem);
        if(imagem != null){
            logo.setImage(new Image(imagem));
        }
    }

    private void atualizarTabela(){
        ObservableList<Jogadores> listaJogadoresFiltrada = FXCollections.observableArrayList();
        for(Jogadores jogadores: JogadoresFile.getInstancia().getListaJogadores()){
            if(jogadores.getSelecao().getNome().equals(selecao.getNome())){
                listaJogadoresFiltrada.add(jogadores);
            }
        }
        tabelaJogadores.setItems(listaJogadoresFiltrada);
    }

    @FXML
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }

    @FXML
    private void irParaCadastrarJogadores(ActionEvent e) {
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasAdministrador/telaCadastrarJogadores.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            atualizarTabela();
        }catch(IOException ex){
            System.err.println("Falha ao abrir o telaCadastrarJogadores: " + ex.getMessage());
        }
    }

    @FXML
    private void removerJogadores(){

        if(jogadorSelecionado == null){
            mostrarErro("Por favor, selecione um jogador \n " +
                    "na tabela para remover.");
            return;
        }

        jogadoresFile.getListaJogadores().remove(jogadorSelecionado);
        jogadoresFile.salvarNoTxt();

        mostrarErro("Jogador removido com sucesso!");
        tabelaJogadores.getSelectionModel().clearSelection();
        atualizarTabela();

    }

    private void mostrarErro(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void mostrarSucesso(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #388e3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void tornarMensagemTemporaria() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), labelMensagem);
        fade.setFromValue(1.0); // Totalmente visível
        fade.setToValue(0.0);   // Totalmente invisível


        fade.setDelay(Duration.seconds(2.0));


        fade.setOnFinished(event -> labelMensagem.setText(""));

        fade.play(); // Inicia o temporizador/efeito
    }

}
