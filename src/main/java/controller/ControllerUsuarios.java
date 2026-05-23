package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.files.UsuarioFile;
import users.Sessao;
import users.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControllerUsuarios {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, String> colNome;
    @FXML private TableColumn<Usuario, String> colFuncao;
    @FXML private TableColumn<Usuario, String> colStatus;
    @FXML private TableColumn<Usuario, String> colPais;
    @FXML private TextField nome;
    @FXML private TextField funcao;
    @FXML private TextField status;
    @FXML private TextField pais;
    @FXML private PasswordField senha;
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;


    @FXML
    public void initialize() {
        // Vincula cada coluna ao atributo correspondente da classe Usuario
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Carrega os dados do CSV na tabela
        tabelaUsuarios.setItems(UsuarioFile.getInstancia().listarTodos());

        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }
    }



    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    @FXML
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }
    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }
    @FXML
    private void irParaEstadios(MouseEvent e) { SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.0.fxml");
    }
    @FXML
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }

    @FXML
    private void irParaArbitros(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitro.fxml");
    }

    @FXML
    private void adicionarUsuario(ActionEvent e){
        //implementar chamar metodo adicionar que pertencera a classe do administrador

    }

    @FXML
    private void removerUsuario(ActionEvent e){
        //implementar chamar metodo remover que pertencera a classe do administrador
    }



    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }



}
