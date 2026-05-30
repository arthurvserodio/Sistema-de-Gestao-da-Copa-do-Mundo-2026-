package controller;

import Enums.Funcao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    @FXML private Button botaoAdicionarJogador;
    @FXML private Button botaoRemoverJogador;
    @FXML private ImageView logo;
    @FXML private TableView <Jogadores> tabelaJogadores;
    @FXML private TableColumn<Jogadores,String> jogadorColuna;
    @FXML private TableColumn<Jogadores,Integer> idadeColuna;
    @FXML private TableColumn<Jogadores,String> posicaoColuna;
    @FXML private VBox listaJogadores; // trabalhar nisso depois

    public void initialize() {
        SelecoesFile.getInstance().getListaSelecoes();
        //Mostra as seleções na tela
        //Toda vez que for escrito alguma coisa no textField ele chama a função de pesquisa
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            if(u.getFuncao() == Funcao.ADMINISTRADOR){
                botaoAdicionarJogador.setVisible(true);
                botaoRemoverJogador.setVisible(true);
            }
        }
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
            if(jogadores.getSelecao().getNome().equals(selecao.getNome())){
                listaJogadoresFiltrada.add(jogadores);
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

}
