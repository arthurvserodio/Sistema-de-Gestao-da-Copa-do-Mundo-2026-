package controller;

import Enums.Funcao;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;
import services.files.EstadioFile;
import stadiumAndRefeering.Estadio;
import users.Sessao;
import users.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerTelasEstadios {
    @FXML private AnchorPane painelPrincipal;
    @FXML private TableView<Estadio> tabelaEstadios;
    @FXML private TableColumn<Estadio, String> colNome;
    @FXML private TableColumn<Estadio, String> colLocal;
    @FXML private TableColumn<Estadio, Integer> colCapacidade;
    @FXML private Button botaoLogin;
    @FXML private MenuButton menuUsuario;
    @FXML private Label labelMensagem;
    @FXML private TextField txtNome;
    @FXML private TextField txtLocal;
    @FXML private TextField txtCapacidade;
    @FXML private TextField txtBusca;
    @FXML private Text botaoUsuario;
    @FXML private Text botaoArbitro1;
    @FXML private Text botaoArbitro2;
    private final EstadioFile estadioFile = EstadioFile.getInstance();


    @FXML
    public void initialize() {
        // Vincula as colunas aos métodos da classe Estadio
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

        atualizarTabela();

        // Logica para marcar como selecionado
        painelPrincipal.setOnMouseClicked(event -> {
            tabelaEstadios.getSelectionModel().clearSelection();
        });





/// Parte da Helena
        Usuario u = Sessao.getInstancia().getUsuarioLogado();
        if(u== null){
            botaoArbitro2.setVisible(false);
            botaoArbitro1.setVisible(false);

        }else {
            if (u.getFuncao() == Funcao.ARBITRO) {
                botaoArbitro1.setVisible(false);
                botaoArbitro2.setVisible(true);
            } else if (u.getFuncao() == Funcao.ADMINISTRADOR || u.getFuncao() == Funcao.ORGANIZADOR) {
                botaoArbitro1.setVisible(true);
                botaoArbitro2.setVisible(false);
            } else {
                botaoArbitro2.setVisible(false);
                botaoArbitro1.setVisible(false);
            }
        }

        if (u != null) {
            menuUsuario.setText(u.getNome());
            menuUsuario.setVisible(true);
            botaoLogin.setVisible(false);
            if(u.getFuncao()==Funcao.ADMINISTRADOR){
                botaoUsuario.setVisible(true);

            }
        } else {
            botaoLogin.setVisible(true);
            menuUsuario.setVisible(false);
        }


    }

    // Mostrar tabela
    private void atualizarTabela() {
        List<Estadio> lista = estadioFile.getListaEstadios();
        tabelaEstadios.setItems(FXCollections.observableArrayList(lista));

    }

    //Adicionar
    @FXML
    private void handleAdicionar(ActionEvent e) {
        try {
            String nome = txtNome.getText().trim();
            String local = txtLocal.getText().trim();
            String capacidadeStr = txtCapacidade.getText().trim();



            if (nome.isEmpty() || local.isEmpty() || capacidadeStr.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
            };

            for(char c : local.toCharArray() ){
                if(Character.isDigit(c)){
                    throw new IllegalArgumentException("Local  deve conter apenas letras.");
                }

            }

            for(char c : nome.toCharArray() ){
                if(Character.isDigit(c)){
                    throw new IllegalArgumentException("Nome deve conter apenas letras.");
                }

            }

            if(estadioFile.estadioJaExiste(nome)){
                mostrarErro("Estadio já cadastrado");
                return;
            }

            int capacidade;
            try {

                capacidade = Integer.parseInt(capacidadeStr);
                if (capacidade < 0) {
                    throw new IllegalArgumentException("A capacidade deve ser um número positivo.");
                }
            } catch (NumberFormatException erro) {
                throw new IllegalArgumentException("O campo capacidade deve conter\n " +
                        "apenas números inteiros.");
            }

            // Envia o int corretamente no segundo parâmetro
            Estadio novoEstadio = new Estadio(nome, capacidade, local);
            estadioFile.getListaEstadios().add(novoEstadio);

            estadioFile.salvarNoTxt();
            mostrarSucesso("Estádio cadastrado com sucesso!");

            txtNome.clear();
            txtLocal.clear();
            txtCapacidade.clear();

            atualizarTabela();

        } catch (IllegalArgumentException erro) {
            mostrarErro(erro.getMessage());
        }
    }

    //Remover
    @FXML
    private void handleRemover(ActionEvent e) {
        Estadio selecionado = tabelaEstadios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarErro("Por favor, selecione um estádio \n " +
                    "na tabela para remover.");
            return;
        }

        estadioFile.getListaEstadios().remove(selecionado);
        estadioFile.salvarNoTxt();

        mostrarSucesso("Estádio removido com sucesso!");
        tabelaEstadios.getSelectionModel().clearSelection();
        atualizarTabela();
    }

    @FXML
    private void handlePesquisar(MouseEvent event) {
        String termo = txtBusca.getText().toLowerCase().trim();

        List<Estadio> resultadoFiltrado = estadioFile.getListaEstadios().stream()
                .filter(estadio -> estadio.getNome().toLowerCase().contains(termo) ||
                        estadio.getLocal().toLowerCase().contains(termo) || String.valueOf(estadio.getCapacidade()).equals(termo))
                .collect(Collectors.toList());

        tabelaEstadios.setItems(FXCollections.observableArrayList(resultadoFiltrado));
    }

    // Mensagens de erro e sucesso :
    private void mostrarErro(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #d32f2f; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

    private void mostrarSucesso(String str) {
        labelMensagem.setText(str);
        labelMensagem.setStyle("-fx-text-fill: #388e3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        labelMensagem.setOpacity(1.0);

        tornarMensagemTemporaria();
    }

   /// Funçao para mensagem aparecer e sumir da tela
    private void tornarMensagemTemporaria() {

        FadeTransition fade = new FadeTransition(Duration.seconds(1.0), labelMensagem);
        fade.setFromValue(1.0); // Totalmente visível
        fade.setToValue(0.0);   // Totalmente invisível


        fade.setDelay(Duration.seconds(2.0));


        fade.setOnFinished(event -> labelMensagem.setText(""));

        fade.play(); // Inicia o temporizador/efeito
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



}

