package controller;

import Enums.Funcao;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import matches.Partida;
import services.DesignacaoService;
import stadiumAndRefeering.DesignacaoArbitragem;
import stadiumAndRefeering.Estadio;
import users.Arbitro;
import users.Sessao;
import users.Usuario;

import java.util.List;
import java.util.stream.Collectors;


public class ControllerDesignacaoArbitro {

    @FXML private TableView<DesignacaoArbitragem> tabelaDesignacao;
    @FXML private Label labelMensagem;
    @FXML private TableColumn<DesignacaoArbitragem, Partida> colPartida;
    @FXML private TableColumn<DesignacaoArbitragem, Arbitro> colArbitros;
    @FXML private TextField buscar;
    @FXML private MenuButton menuUsuario;
    @FXML private Text botaoUsuario;
    @FXML private Button botaoLogin;



    final DesignacaoService designacaoService =  new DesignacaoService();

    public void initialize(){
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome());
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao()==Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }


        colArbitros.setCellValueFactory(new PropertyValueFactory<>("nomeArbitro"));
        colPartida.setCellValueFactory(new PropertyValueFactory<>("nomePartida"));
        tabelaDesignacao.setItems(designacaoService.listaDesignacoes());

    }







    @FXML
    //Passa para tela de login
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }





    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }


    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/telaPrincipalUsuarios.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }


    @FXML
    //Passa para tela de história
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }

    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }


    @FXML
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }

    @FXML
    private void irParaEstadios(MouseEvent e) {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u.getFuncao() != Funcao.ARBITRO){
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
        }
        else{
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioNormal.fxml");
        }
    }


    @FXML
    private void handlePesquisar(MouseEvent event) {
        String termo = buscar.getText().toLowerCase().trim();

        // se o campo estiver vazio reseta a tabela
        if (termo.isEmpty()) {
            tabelaDesignacao.setItems(designacaoService.listaDesignacoes());
            return;
        }
        List<DesignacaoArbitragem> resultadoFiltrado = designacaoService.listaDesignacoes().stream()
                .filter(d -> d.getNomeArbitro().toLowerCase().contains(termo) ||
                        d.getNomePartida().toLowerCase().contains(termo))
                .collect(Collectors.toList());

        tabelaDesignacao.setItems(FXCollections.observableArrayList(resultadoFiltrado));
    }



}
