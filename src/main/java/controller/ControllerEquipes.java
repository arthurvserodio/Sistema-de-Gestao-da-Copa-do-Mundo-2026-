package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerEquipes {
    //Logica para pular as paginas das equipes presentes na copa, como são muitos fxml, foi usado um vetor com o caminho destes,para não ser necessario o uso de muitos ifs
    private static int pagina=0;
    private String[] listaFxml=new String[] {"/designAndScreens/telaInicial/equipesNaCopa.fxml","/designAndScreens/telaInicial/equipesNaCopa2.fxml","/designAndScreens/telaInicial/equipesNaCopa3.fxml","/designAndScreens/telaInicial/equipesNaCopa4.fxml"
            ,"/designAndScreens/telaInicial/equipesNaCopa5.fxml","/designAndScreens/telaInicial/equipesNaCopa6.fxml","/designAndScreens/telaInicial/equipesNaCopa7.fxml","/designAndScreens/telaInicial/equipesNaCopa8.fxml","/designAndScreens/telaInicial/equipesNaCopa9.fxml"
            ,"/designAndScreens/telaInicial/equipesNaCopa10.fxml","/designAndScreens/telaInicial/equipesNaCopa11.fxml","/designAndScreens/telaInicial/equipesNaCopa12.fxml"};
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
        pagina=0;
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
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }
    @FXML
    private void avancar(ActionEvent e) {
        //A pagina(flag) tem que ficar dentro das posições de memória do vetor,logo não poderá ultrapassar seu tamanho-1
        if (pagina < listaFxml.length - 1) {
            pagina++;
            SceneController.mudaDeTela(listaFxml[pagina]);
        }
    }
    @FXML
    private void voltar(ActionEvent e){
        //A pagina só pode voltar(ser subtraida) apenas se ela for maior do que 0, se não podemos tentar acessar a posição -1, a qual não existe
        if (pagina > 0) {
            pagina--;
            SceneController.mudaDeTela(listaFxml[pagina]);
        }
    }
}
