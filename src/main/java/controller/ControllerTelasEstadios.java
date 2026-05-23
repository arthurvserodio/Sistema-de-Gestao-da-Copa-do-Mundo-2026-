package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import stadiumAndRefeering.Estadio;
import users.Sessao;
import users.Usuario;

public class ControllerTelasEstadios {
    @FXML private AnchorPane painelPrincipal;
    @FXML private TableView<Estadio> tabelaEstadios;
    @FXML private TableColumn<Estadio, String> colNome;
    @FXML private TableColumn<Estadio, String> colCapacidade;
    @FXML private TableColumn<Estadio, Integer> colLocal;
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;

// Metodo para pegar valores das classes e mandar para o java fx:
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

        //teste:
        ObservableList<Estadio> lista = FXCollections.observableArrayList(
                new Estadio("Maracanã", 78838, "Rio de Janeiro"),
                new Estadio("Lusail Stadium", 88966, "Catar"),
                new Estadio("Azteca", 87523, "México")
        );
        for (int i = 1; i <= 50; i++) {
            lista.add(new Estadio("Estádio " + i, 50000, "Cidade " + i));
        }


        tabelaEstadios.setItems(lista);

        // Lógica para deselecionar ao clicar na área vazia da tabela
        painelPrincipal.setOnMouseClicked(event -> {
            tabelaEstadios.getSelectionModel().clearSelection();
        });

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
    private void irParaTela1(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaEstadios/tela1.fxml");
    }

    @FXML
    //Passa para tela de login
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }





    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }


    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
    @FXML
    //Passa do Menu para a tela de equipes presentes na copa de 2026
    private void irParaEquipes(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/equipesNaCopa.fxml");
    }

    @FXML
    //Passa para tela de história
    private void irParaHistoria(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telaInicial/historia.fxml");
    }

    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }




}

