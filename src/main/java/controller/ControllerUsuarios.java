package controller;

import Enums.Funcao;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.UsuarioService;
import services.files.UsuarioFile;
import users.Sessao;
import users.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControllerUsuarios {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, String> colNome;
    @FXML private TableColumn<Usuario, String> colFuncao;
    @FXML private TableColumn<Usuario, String> colStatus;
    @FXML private TableColumn<Usuario, String> colPais;
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private CheckMenuItem checkNome;
    @FXML private CheckMenuItem checkFuncao;
    @FXML private CheckMenuItem checkPais;
    @FXML private CheckMenuItem checkStatus;
    @FXML private TextField campoPesquisa;






    @FXML
    public void initialize() {
        // Vincula cada coluna ao atributo correspondente da classe Usuario
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Carrega os dados do CSV na tabela
        tabelaUsuarios.setItems(UsuarioFile.getInstancia().listarTodos());

        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if (u != null) {
            menuUsuario.setText(u.getNome() );
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }

        //o listener fica sempre verificando para ver se tem alguma mudanca, se houver, executa filtrar
        campoPesquisa.textProperty().addListener((obs, antigo, novo) -> filtrar());
        checkNome.selectedProperty().addListener((obs, antigo, novo) -> filtrar());
        checkFuncao.selectedProperty().addListener((obs, antigo, novo) -> filtrar());
        checkPais.selectedProperty().addListener((obs, antigo, novo) -> filtrar());
        checkStatus.selectedProperty().addListener((obs, antigo, novo) -> filtrar());



    }

    private void filtrar() {
        String texto = campoPesquisa.getText().toLowerCase();
        ObservableList<Usuario> listaFiltrada = FXCollections.observableArrayList(); //cria lista vazia

        for (Usuario u : UsuarioFile.getInstancia().listarTodos()) {
            boolean encontrou = false;

            if (checkNome.isSelected() && u.getNome().toLowerCase().contains(texto)) encontrou = true;
            if (checkFuncao.isSelected() && u.getFuncao().toString().toLowerCase().contains(texto)) encontrou = true;
            if (checkPais.isSelected() && u.getPais().toLowerCase().contains(texto)) encontrou = true;
            if (checkStatus.isSelected() && u.getStatus().toLowerCase().contains(texto)) encontrou = true;

            // se nenhum criterio selecionado, pesquisa em todos
            if (!checkNome.isSelected() && !checkFuncao.isSelected() && !checkPais.isSelected() && !checkStatus.isSelected()) {
                if (u.getNome().toLowerCase().contains(texto) ||
                        u.getFuncao().toString().toLowerCase().contains(texto) ||
                        u.getPais().toLowerCase().contains(texto) ||
                        u.getStatus().toLowerCase().contains(texto)) {
                    encontrou = true;
                }
            }

            if (encontrou) listaFiltrada.add(u);
        }

        tabelaUsuarios.setItems(listaFiltrada);
    }



    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }
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
    private void irParaRelatorio(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telasAdministrador/relatorio.fxml");
    }

    @FXML
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }

    @FXML
    private void irParaArbitros(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
    }

    @FXML
    private void irParaCadastrarUsuario(ActionEvent e){
        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasAdministrador/CadastroUsuarios.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }catch(IOException ex){
            System.err.println("Falha ao abrir o telaCadastroUsuario: " + ex.getMessage());
        }
    }

    @FXML
    private void irParaEditarUsuario(ActionEvent e){
        try{

            Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

            if(selecionado==null){
                throw new NenhumUsuarioSelecionadoException("Nenhum usuário foi selecionado");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/designAndScreens/telasAdministrador/EditarUsuario.fxml"));
            Parent root = loader.load(); //carrega o fxml

            ControllerEditarUsuario controller = loader.getController();
            controller.setUsuario(selecionado);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); //trava a janela anterior enquanto essa nao fechar
            stage.setScene(new Scene(root)); //coloca no novo stage o fxml carregado
            stage.showAndWait(); //mostra a cena e trava o codigo ate sair daqui
        }catch(IOException ex){
            System.err.println("Falha ao abrir o telaEditarUsuario: " + ex.getMessage());
        }catch (NenhumUsuarioSelecionadoException a) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText(a.getMessage());
            alert.showAndWait();

        }
    }



    @FXML
    private void removerUsuario() {
        try {
            Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                throw new NenhumUsuarioSelecionadoException("Nenhum usuário foi selecionado");
            }

            UsuarioService.removerUsuario(selecionado);
            campoPesquisa.clear();
            tabelaUsuarios.setItems(UsuarioFile.getInstancia().listarTodos());

        } catch (NenhumUsuarioSelecionadoException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch(RemoveSiMesmoException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }





    @FXML
    private void onLogout(ActionEvent e){
        Sessao.getInstancia().logout();
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/paginaInicial.fxml");
    }




}
