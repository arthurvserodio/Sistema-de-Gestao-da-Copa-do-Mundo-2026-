package services;
import controller.SceneController;
import exceptions.LoginInvalidoException;
import javafx.collections.ObservableList;
import services.files.UsuarioFile;
import users.Sessao;
import users.Usuario;




public class ValidaLogin {

    public static void validar(String login, String senha) throws LoginInvalidoException {
        ObservableList<Usuario> usuarios = UsuarioFile.getInstancia().listarTodos();

        for (Usuario u : usuarios) {
            if (u.getNome().equals(login) && u.getSenha().equals(senha)) {
                Sessao.getInstancia().login(u); //cria a instancia de primeira
                return;
            }
        }
        throw new LoginInvalidoException("Login ou senha inválidos.");
    }



}
