package controller;

import Enums.Funcao;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    @FXML private TextField nome;
    @FXML private TextField funcao;
    @FXML private TextField status;
    @FXML private TextField pais;
    @FXML private PasswordField senha;
    @FXML private PasswordField senha2;
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
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }

    @FXML
    private void irParaArbitros(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/Arbitragem/telaArbitroAdm.fxml");
    }

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

    @FXML
    private void removerUsuario() {
        try {
            Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                throw new NenhumUsuarioSelecionadoException("Nenhum usuário foi selecionado");
            }

            UsuarioService.removerUsuario(selecionado);

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

    @FXML
    private Label labelMensagem;

    private void mostrarErro(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #cc0000;");
        labelMensagem.setText(mensagem);
    }

    private void mostrarSucesso(String mensagem) {
        labelMensagem.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a7a1a;");
        labelMensagem.setText(mensagem);
    }

}
