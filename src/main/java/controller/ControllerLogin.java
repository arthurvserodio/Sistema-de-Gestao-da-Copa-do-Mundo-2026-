package controller;

import exceptions.LoginInvalidoException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ValidaLogin;
import users.Administrador;
import users.Sessao;

import java.io.*;

import Enums.Funcao;

public class ControllerLogin {
    //Volta para a pagina inicial de menu clicando no logo da copa
    @FXML
    private void irPaginaInicial(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    //Passa do Menu para a página que conta a historia da copa
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
    //Gustavo ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaEstadios(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
    }
    @FXML
    //Helena ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaLogin(ActionEvent e) {SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }

    @FXML
    private TextField campoUsuario;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelMensagem;

    @FXML
    private Button botaoEntrar;

    @FXML
    private Button botaoCadastro;

    // -------------------------------------------------------
    // Chamado ao clicar em "Entrar"
    // -------------------------------------------------------
    @FXML
    private void handleEntrar() {
        String usuario = campoUsuario.getText().trim();
        String senha   = campoSenha.getText().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarErro("Preencha todos os campos.");
            return;
        }

        try {
            ValidaLogin.validar(usuario,senha);
            mostrarSucesso("Login feito com sucesso");

            if(Sessao.getInstancia().getFuncaoLogado()==Funcao.ADMINISTRADOR){
                SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/telaPrincipalAdministrador.fxml");
            }
            else{
                SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
            }
        } catch (LoginInvalidoException e) {
            mostrarErro("Login ou senha inválidas");
        }


    }




    private void mostrarErro(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #cc0000;");
        labelMensagem.setText(mensagem);
    }

    private void mostrarSucesso(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a7a1a;");
        labelMensagem.setText(mensagem);
    }


}
