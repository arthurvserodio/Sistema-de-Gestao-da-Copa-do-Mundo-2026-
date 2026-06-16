package controller;

import Enums.Funcao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import services.*;
import users.Sessao;
import users.Usuario;

public class ControllerRelatorio {
    @FXML
    private void irPaginaInicial(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telasAdministrador/telaPrincipalUsuarios.fxml");

        }

    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaPartidas(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telasPartidas/EscolhaPartida.fxml");
    }

    @FXML
    private void irParaRelatorio(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/relatorio.fxml");
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

    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }

    @FXML
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }
    @FXML
    private void irParaArbitros(MouseEvent e){
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u.getFuncao() != Funcao.ARBITRO){
            SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
        }
        else{
            SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaDesignacao.fxml");
        }
    }

    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }

    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Text botaoUsuario;
    @FXML private Text botaoRelatorio;


    public void initialize() {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();



        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao()== Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
                botaoRelatorio.setVisible(true);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }
    }

    public void gerarRelatorioSelecoes(ActionEvent e){
        RelatorioSelecoes.gerar("Relatorio_Seleções.txt");
    }

    public void gerarRelatorioJogadores(ActionEvent e){
        RelatorioJogadores.gerar("Relatorio_Jorgadores.txt");
    }

    public void gerarRelatorioArbitros(ActionEvent e){
        RelatorioArbitros.gerar("Relatorio_Arbitros.txt");
    }

    public void gerarRelatorioEstadios(ActionEvent e){
        RelatorioEstadios.gerar("Relatorio_Estádios.txt");
    }

    public void gerarRelatorioUsuarios(ActionEvent e){
        RelatorioUsuarios.gerar("Relatorio_Usuários.txt");
    }
}
