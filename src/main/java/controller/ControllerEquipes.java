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
    //Lógica para a troca de tela
    private void trocarTela(Event e, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            // 👇 em vez de criar nova Scene, só troca o conteúdo
            stage.getScene().setRoot(root);

        } catch (Exception d) {
            d.printStackTrace();
        }
    }
    private static int pagina=0;
    private String[] listaFxml=new String[] {"/designAndScreens/telaInicial/equipesNaCopa.fxml","/designAndScreens/telaInicial/equipesNaCopa2.fxml","/designAndScreens/telaInicial/equipesNaCopa3.fxml","/designAndScreens/telaInicial/equipesNaCopa4.fxml"
            ,"/designAndScreens/telaInicial/equipesNaCopa5.fxml","/designAndScreens/telaInicial/equipesNaCopa6.fxml","/designAndScreens/telaInicial/equipesNaCopa7.fxml","/designAndScreens/telaInicial/equipesNaCopa8.fxml","/designAndScreens/telaInicial/equipesNaCopa9.fxml"
            ,"/designAndScreens/telaInicial/equipesNaCopa10.fxml","/designAndScreens/telaInicial/equipesNaCopa11.fxml","/designAndScreens/telaInicial/equipesNaCopa12.fxml"};
    //Volta para a pagina inicial de menu clicando no logo da copa
    @FXML
    private void irPaginaInicial(MouseEvent e){
        trocarTela(e,"/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    //Passa do Menu para a página que conta a historia da copa
    @FXML
    private void irParaHistoria(MouseEvent e){
        trocarTela(e,"/designAndScreens/telaInicial/historia.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        pagina=0;
        trocarTela(e, "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }
    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        trocarTela(e, "/designAndScreens/telaInicial/classificacao.fxml");
    }
    @FXML
    //Gustavo ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaEstadios(MouseEvent e) {
        System.out.println("Página Estádios ainda não pronta");
    }
    @FXML
    //Helena ta fazendo, depois adiciona o trocaTela + fxml
    private void irParaLogin(ActionEvent e) {
        System.out.println("Página Login ainda não pronta");
    }
    @FXML
    private void avancar(ActionEvent e) {
        if (pagina < listaFxml.length - 1) {
            pagina++;
            trocarTela(e, listaFxml[pagina]);
        }
    }
    @FXML
    private void voltar(ActionEvent e){
        if (pagina > 0) {
            pagina--;
            trocarTela(e, listaFxml[pagina]);
        }
    }
}
