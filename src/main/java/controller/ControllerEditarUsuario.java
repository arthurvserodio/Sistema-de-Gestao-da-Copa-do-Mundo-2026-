package controller;

import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.UsuarioService;
import users.Usuario;

public class ControllerEditarUsuario {



    @FXML private TextField nomeEdit;
    @FXML private TextField funcaoEdit;
    @FXML private TextField statusEdit;
    @FXML private TextField paisEdit;
    @FXML private PasswordField senhaEdit;
    @FXML private PasswordField senha2Edit;

    private Usuario u;

    public void setUsuario(Usuario u){
        this.u = u;
        nomeEdit.setText(u.getNome());
        funcaoEdit.setText(u.getFuncao().toString());
        paisEdit.setText(u.getPais());
        statusEdit.setText(u.getStatus());
        senhaEdit.setText(u.getSenha());
        senha2Edit.setText(u.getSenha());
    }


    @FXML
    private void editarUsuario(){

        String nomeEdit_s = nomeEdit.getText().trim();
        String funcaoEdit_s = funcaoEdit.getText().trim();
        String statusEdit_s = statusEdit.getText().trim();
        String paisEdit_s = paisEdit.getText().trim();
        String senhaEdit_s = senhaEdit.getText().trim();
        String senha2Edit_s = senha2Edit.getText().trim();


        try{

            UsuarioService.editarUsuario(u,nomeEdit_s, funcaoEdit_s, statusEdit_s, paisEdit_s, senhaEdit_s, senha2Edit_s);
            mostrarSucessoEdit("Usuário editado!");
            SceneController.mudaDeTela("/designAndScreens/telasAdministrador/usuarios.fxml");
        } catch(CamposVaziosException a ){
            mostrarErroEdit("Preencha todos os campos");
        }
        catch(UsuarioExisteException a){
            mostrarErroEdit("O usuario inserido já existe");
        }
        catch(SenhasDiferemException a){
            mostrarErroEdit("As senhas diferem");
        }
        catch(FuncaoInvalidaException a){
            mostrarErroEdit("A função é inexistente");

        }
        catch(StatusInvalidoException a){
            mostrarErroEdit("O status é inválido");
        }



    }

    @FXML
    private Label labelMensagemEdit;



    private void mostrarErroEdit(String mensagem) {
        labelMensagemEdit.setStyle("-fx-font-size: 13px; -fx-text-fill: #cc0000;");
        labelMensagemEdit.setText(mensagem);
    }

    private void mostrarSucessoEdit(String mensagem) {
        labelMensagemEdit.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a7a1a;");
        labelMensagemEdit.setText(mensagem);
    }

}
