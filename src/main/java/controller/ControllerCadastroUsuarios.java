package controller;

import exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.UsuarioService;
import services.files.UsuarioFile;

public class ControllerCadastroUsuarios {

    @FXML private TextField nome;
    @FXML private TextField funcao;
    @FXML private TextField status;
    @FXML private TextField pais;
    @FXML private PasswordField senha;
    @FXML private PasswordField senha2;
    @FXML private Label labelMensagem;


    @FXML
    private void adicionarUsuario(ActionEvent e){

        String nome_s = nome.getText().trim();
        String funcao_s = funcao.getText().trim();
        String status_s = status.getText().trim();
        String pais_s = pais.getText().trim();
        String senha_s = senha.getText().trim();
        String senha2_s = senha2.getText().trim();
        try{
            UsuarioService.adicionarUsuario(nome_s, funcao_s, status_s, pais_s, senha_s, senha2_s);
            mostrarSucesso("Usuário cadastrado!");
            SceneController.mudaDeTela("/designAndScreens/telasAdministrador/usuarios.fxml");
        }
        catch(CamposVaziosException a ){
            mostrarErro("Preencha todos os campos");
            return;
        }
        catch(UsuarioExisteException a){
            mostrarErro("O usuario inserido já existe");
            return;
        }
        catch(SenhasDiferemException a){
            mostrarErro("As senhas diferem");
        }
        catch(FuncaoInvalidaException a){
            mostrarErro("A função é inexistente");

        }
        catch(StatusInvalidoException a){
            mostrarErro("O status é inválido");
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
