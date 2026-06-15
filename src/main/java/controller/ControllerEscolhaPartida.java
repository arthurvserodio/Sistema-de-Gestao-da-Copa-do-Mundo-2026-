package controller;

import Enums.Fase;
import Enums.Funcao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import matches.EstadoDaCopa;
import services.matches.CarregaArquivoService;
import users.Sessao;
import users.Usuario;

import java.time.LocalDate;
import java.util.List;

public class ControllerEscolhaPartida {
    @FXML
    private void irPaginaInicial(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telasAdministrador/telaPrincipalUsuarios.fxml");
    }

    @FXML
    private void irParaRelatorio(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telasAdministrador/relatorio.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }
    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaPartida(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telasPartidas/EscolhaPartida.fxml");
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

    @FXML
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }
    //Botões para acessar as partidas
    @FXML private Button btnEstados;
    @FXML private Button btnPartidas;
    private EstadoDaCopa faseAtual; //Verificação de qual fase está a copa
    //Botões login
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Text botaoUsuario;
    @FXML private Text botaoRelatorio;
    @FXML private Text botaoHistoria;

    public void initialize() {
        //Leitura da fae atual da copa
        List<EstadoDaCopa> estadosDaCopa = CarregaArquivoService.carregaArquivo("/database/estado_copa.txt", parte -> new EstadoDaCopa(Fase.valueOf(parte[0]), LocalDate.parse(parte[1]), LocalDate.parse(parte[2])));
        faseAtual = estadosDaCopa.get(0);
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao()== Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);
                botaoRelatorio.setVisible(true);
                botaoHistoria.setVisible(false);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }
        btnPartidas.setOnAction(s->{
            if(faseAtual.getFaseAtual().equals(Fase.NAO_COMECOU) || faseAtual.getFaseAtual().equals(Fase.FINALIZADO)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Atenção");
                alert.setHeaderText(null);
                alert.setContentText("Você não pode cadastrar partidas nessa fase.");
                alert.showAndWait();
            }
            else{
                SceneController.mudaDeTela("/designAndScreens/telasPartidas/cadastroDePartida.fxml");
            }
        });
        btnEstados.setOnAction(s->{
            SceneController.mudaDeTela("/designAndScreens/telasPartidas/maquinaDeEstados.fxml");
        });
    }
}
