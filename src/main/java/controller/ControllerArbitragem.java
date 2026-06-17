package controller;

import Enums.Funcao;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import services.files.ArbitroFile;
import stadiumAndRefeering.Estadio;
import users.Arbitro;
import users.Sessao;
import users.Usuario;

import java.util.List;
import java.util.stream.Collectors;


public class ControllerArbitragem {
    @FXML private AnchorPane painelPrincipal;
    @FXML private TableView<Arbitro> tabela;
    @FXML private TableColumn<Arbitro, String> colNome;
    @FXML private TableColumn<Arbitro, String> colNacionalidade;
    @FXML private TableColumn<Arbitro, Integer> colExperiencia;
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private TextField txtNome;
    @FXML private TextField txtExperiencia;
    @FXML private TextField txtNacionalidade;
    @FXML private TextField txtBusca;
    @FXML private Label label;
    @FXML private Text botaoUsuario;
    @FXML private Text botaoRelatorio;
    @FXML private Text botaoHistoria;


    private final ArbitroFile arbitroFile = ArbitroFile.getInstance();

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("pais"));
        colExperiencia.setCellValueFactory(new PropertyValueFactory<>("experiencia"));
        atualizarTabela();




        painelPrincipal.setOnMouseClicked(event -> {
            tabela.getSelectionModel().clearSelection();
        });

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
            else if(u.getFuncao()== Funcao.ORGANIZADOR){
                botaoUsuario.setVisible(false);
                botaoRelatorio.setVisible(false);
                botaoHistoria.setVisible(false);
            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }


    }

    @FXML
    private void irParaRelatorio(MouseEvent e){
        SceneController.mudaDeTela("/designAndScreens/telasAdministrador/relatorio.fxml");
    }



    private void atualizarTabela() {
        List<Arbitro> lista = arbitroFile.getListaArbitros();
        tabela.setItems(FXCollections.observableArrayList(lista));

    }

    //add
    @FXML
    private void handleAdicionar(ActionEvent e) {
        try {
            String nome = txtNome.getText().trim();
            String  str = txtExperiencia.getText().trim();
            String nacionalidade = txtNacionalidade.getText().trim();

            if (nome.isEmpty() || str.isEmpty() || nacionalidade.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
            }
            for(char c : nacionalidade.toCharArray() ){
                if(Character.isDigit(c)){
                    throw new IllegalArgumentException("Nacionalidade deve conter apenas letras.");
                }

            }

            for(char c : nome.toCharArray() ){
                if(Character.isDigit(c)){
                    throw new IllegalArgumentException("Nome deve conter apenas letras.");
                }

            }

            if(arbitroFile.arbitroJaExiste(nome)){
                mostrarErro("Arbitro já existe");
                return;
            }

            int experiencia;
            try {

                experiencia= Integer.parseInt(str);
                if (experiencia < 0 || experiencia > 5 ) {
                    throw new IllegalArgumentException("A experiencia deve ser um \n" +
                            "número entre 0 e 5.");
                }


            } catch (NumberFormatException erro) {
                throw new IllegalArgumentException("O campo capacidade deve conter\n " +
                        "apenas números inteiros.");
            }

            // Envia o int corretamente no segundo parâmetro
           Arbitro arbitro = new Arbitro(nome,nacionalidade, experiencia);
            arbitroFile.getListaArbitros().add(arbitro);

            arbitroFile.salvarNoTxt();
            mostrarSucesso("Arbitro cadastrado com sucesso!");

            txtNome.clear();
            txtExperiencia.clear();
            txtNacionalidade.clear();

            atualizarTabela();

        } catch (IllegalArgumentException erro) {
            mostrarErro(erro.getMessage());
        }
    }

    //Remover
    @FXML
    private void handleRemover(ActionEvent e) {
        Arbitro selecionado = tabela.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarErro("Por favor, selecione um arbitro \n " +
                    "na tabela para remover.");
            return;
        }

        arbitroFile.getListaArbitros().remove(selecionado);
        arbitroFile.salvarNoTxt();

        mostrarSucesso("Arbitro removido com sucesso!");
        tabela.getSelectionModel().clearSelection();
        atualizarTabela();
    }

    @FXML
    private void handlePesquisar(MouseEvent event) {
        String termo = txtBusca.getText().toLowerCase().trim();

        List<Arbitro> resultadoFiltrado = arbitroFile.getListaArbitros().stream()
                .filter(arbitro1 -> arbitro1.getNome().toLowerCase().contains(termo) ||
                        arbitro1.getPais().toLowerCase().contains(termo) || String.valueOf(arbitro1.getExperiencia()).equals(termo))
                .collect(Collectors.toList());

        tabela.setItems(FXCollections.observableArrayList(resultadoFiltrado));
    }

    // Mensagens de erro e sucesso :
    private void mostrarErro(String str) {
        label.setText(str);
        label.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 14px; -fx-font-weight: bold;");
        label.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void mostrarSucesso(String str) {
        label.setText(str);
        label.setStyle("-fx-text-fill: #388e3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        label.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    /// Funçao para mensagem aparecer e sumir da tela
    private void tornarMensagemTemporaria() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), label);
        fade.setFromValue(1.0); // Totalmente visível
        fade.setToValue(0.0);   // Totalmente invisível


        fade.setDelay(Duration.seconds(2.0)); // tempo de mensagem na tela


        fade.setOnFinished(event -> label.setText(""));

        fade.play(); // Inicia o temporizador/efeito
    }







    @FXML
    //Passa para tela de login
    private void irParaLogin(ActionEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/login/login.fxml");
    }


    @FXML

    private void irParaEstadios(MouseEvent e) {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u == null || u.getFuncao() == Funcao.ARBITRO){
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioNormal.fxml");
        }
        else{
            SceneController.mudaDeTela( "/designAndScreens/telaEstadios/telaEstadioAdm.fxml");
        }
    }


    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaClassificacao(MouseEvent e) {
        SceneController.mudaDeTela( "/designAndScreens/telaInicial/classificacao.fxml");
    }


    @FXML
    //Muda para tela de inicio
    private void irParaInicio(MouseEvent e) {
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if( u == null) {
            SceneController.mudaDeTela("/designAndScreens/telaInicial/paginaInicial.fxml");
        }else {
            SceneController.mudaDeTela("/designAndScreens/telasAdministrador/telaPrincipalUsuarios.fxml");
        }
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

    @FXML
    private void irParaUsuarios(MouseEvent e){
        SceneController.mudaDeTela( "/designAndScreens/telasAdministrador/usuarios.fxml");
    }

    @FXML
    //Passa do Menu para a tela de grupos da copa 2026
    private void irParaPartidas(MouseEvent e) {

        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if( u == null || u.getFuncao() == Funcao.ARBITRO) {
            SceneController.mudaDeTela("/designAndScreens/telasPartidas/mostraPartida.fxml");
        }else {

            SceneController.mudaDeTela("/designAndScreens/telasPartidas/EscolhaPartida.fxml");
            }

        }



}
