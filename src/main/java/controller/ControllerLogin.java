package controller;

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
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.fxml");
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

        // Validação básica de campos vazios
        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarErro("Preencha todos os campos.");
            return;
        }

        // TODO: substitua pela sua lógica real de autenticação
        // Exemplo estático para teste:
        if (usuario.equals("admin") && senha.equals("1234")) {
            mostrarSucesso("Login realizado com sucesso!");
            // Aqui você pode trocar de cena:
            // trocarCena("/com/seuapp/home.fxml");
        } else {
            mostrarErro("Usuário ou senha inválidos.");
        }
    }

    // -------------------------------------------------------
    // Chamado ao clicar em "Realizar Cadastro"
    // -------------------------------------------------------
    @FXML
    private void handleCadastro() {
        // TODO: abrir tela de cadastro
        // Exemplo:
        // trocarCena("/com/seuapp/cadastro.fxml");
        mostrarSucesso("Redirecionando para o cadastro...");
    }

    // -------------------------------------------------------
    // Helpers de feedback visual
    // -------------------------------------------------------
    private void mostrarErro(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #cc0000;");
        labelMensagem.setText(mensagem);
    }

    private void mostrarSucesso(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a7a1a;");
        labelMensagem.setText(mensagem);
    }

    // -------------------------------------------------------
    // Utilitário para troca de cena (descomente se precisar)
    // -------------------------------------------------------
    /*
    private void trocarCena(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) botaoEntrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
