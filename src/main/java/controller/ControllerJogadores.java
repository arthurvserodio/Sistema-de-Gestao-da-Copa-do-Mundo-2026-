package controller;

import Enums.Funcao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import nationsAndPlayers.nations.Selecoes;
import services.files.SelecoesFile;
import users.Sessao;
import users.Usuario;

import java.io.InputStream;

public class ControllerJogadores {

    private Selecoes selecao;

    @FXML private Button botaoAdicionarJogador;
    @FXML private Button botaoRemoverJogador;
    @FXML private ImageView logo;
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
    }

    public void setSelecoes(Selecoes selecao){
        this.selecao = selecao;
        carregarDados();
    }
    private void carregarDados(){
        carregarImagem();
        //carregarJogadores(); //trabalhar depois
    }

    private void carregarImagem(){
        String caminhoImagem = "/images/" + selecao.getNome().toLowerCase().replace(" ", "_") + ".png";
        InputStream imagem = getClass().getResourceAsStream(caminhoImagem);
        if(imagem != null){
            logo.setImage(new Image(imagem));
        }
    }

    @FXML
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela("/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }


}
