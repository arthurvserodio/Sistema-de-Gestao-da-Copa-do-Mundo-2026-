package controller;

import Enums.Fase;
import builder.SelecaoBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import matches.EstadoDaCopa;
import nationsAndPlayers.nations.Selecoes;
import services.matches.CadastroPartidaService;
import services.matches.CarregaArquivoService;

import java.time.LocalDate;
import java.util.List;

public class ControllerMaquinaDeEstados {
        private EstadoDaCopa estadoAtual;

        @FXML
        private Label labelFaseAtual;

        @FXML
        private Label labelProximaFase;

        @FXML
        private Label podeAvancar;

        @FXML
        private DatePicker dataInicio;

        @FXML
        private DatePicker dataFim;

        @FXML
        private Button btnAvancar;

        @FXML
        private void voltaParaEscolha(MouseEvent e) {
            SceneController.mudaDeTela( "/designAndScreens/telasPartidas/EscolhaPartida.fxml");
        }

        @FXML
        public void initialize() {
            carregarEstado();
            mostrarDados();
            btnAvancar.setOnAction(e -> avancarFase());
        }
        //Pega o estado atual presente no arquivo
        private void carregarEstado(){
            List<EstadoDaCopa> estadosDaCopa = CarregaArquivoService.carregaArquivo("/database/estado_copa.txt", parte -> new EstadoDaCopa(Fase.valueOf(parte[0]), LocalDate.parse(parte[1]), LocalDate.parse(parte[2])));
            estadoAtual = estadosDaCopa.get(0);
        }
        //Avança para a próxima fase
        private void avancarFase(){
            //Verifica se falta partida para ser finalizada naquela fase
            if(!podeAvancarFase()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Ainda existem partidas pendentes nesta fase.");
                alert.showAndWait();
                return;
            }
            //Se não for selecionada nenhuma data, então não vai
            if(dataInicio.getValue() == null || dataFim.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Selecione as datas.");
                alert.showAndWait();
                return;
            }
            //Se a data final for antes da inicial, então dá erro
            if(dataFim.getValue().isBefore(dataInicio.getValue())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Data final inválida.");
                alert.showAndWait();
                return;
            }
            // Verifica se a nova fase começa depois da fase atual terminar
            if(dataInicio.getValue().isBefore(estadoAtual.getFim())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("A próxima fase não pode começar antes do término da fase atual.");
                alert.showAndWait();
                return;
            }
            //Chama o metodo da classe
            estadoAtual.proximoEstado();
            estadoAtual.setInicio(dataInicio.getValue());
            estadoAtual.setFim(dataFim.getValue());
            salvarEstado(); //Salva no arquivo
            mostrarDados(); //Muda as labeis na tela
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Fase atualizada com sucesso!");
            alert.showAndWait();
            if(estadoAtual.getFaseAtual()==Fase.FINALIZADO) {
                CarregaArquivoService.limparArquivos("src/main/resources/database/arbitrosNaCopa.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/escalacao.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/Jogadores.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/Estadios.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/estatisticas_partida.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/eventos.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/notas_jogadores.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/partida_grupo.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/partida_eliminatoria.txt");
                CarregaArquivoService.limparArquivos("src/main/resources/database/SelecoesNaCopa.txt");
            }
        }
        private void salvarEstado(){
            CadastroPartidaService.salvarEstadoDaCopa(estadoAtual, "src/main/resources/database/estado_copa.txt");
            CadastroPartidaService.salvarEstadoDaCopa(estadoAtual, "target/classes/database/estado_copa.txt");
        }
        private void mostrarDados(){
            labelFaseAtual.setText(estadoAtual.getFaseAtual().toString());
            labelProximaFase.setText(proximaFase(estadoAtual.getFaseAtual()).toString());
            //Label para indicar se pode ou não avançar para próxima fase
            if(podeAvancarFase()){
                podeAvancar.setText("✔ Todas as partidas desta fase foram concluídas.");
                podeAvancar.setStyle("-fx-text-fill: #4CAF50;");
            }
            else{
                podeAvancar.setText("✖ Ainda existem partidas pendentes nesta fase.");
                podeAvancar.setStyle("-fx-text-fill: #D32F2F;");
            }
        }

        //Faz a iteração para ter o que colocar na label de próximo estado
        private Fase proximaFase(Fase atual){
            Fase[] fases = Fase.values();
            int indice = atual.ordinal() + 1;
            if(indice < fases.length){
                return fases[indice];
            }
            return Fase.NAO_COMECOU;
        }
        private boolean podeAvancarFase() {
            switch (estadoAtual.getFaseAtual()) {

                case NAO_COMECOU:
                    List<Selecoes> selecoes = CarregaArquivoService.carregaArquivo("/database/SelecoesNaCopa.txt", parte -> new SelecaoBuilder().nome(parte[0]).build());
                    return selecoes.size() == 48;
                case FASE_DE_GRUPOS:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.FASE_DE_GRUPOS) == 72;
                case PLAYOFFS:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.PLAYOFFS) == 16;
                case OITAVAS:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.OITAVAS) == 8;
                case QUARTAS:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.QUARTAS) == 4;
                case SEMIFINAL:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.SEMIFINAL) == 2;
                case FINAL:
                    return CadastroPartidaService.contarPartidasFinalizadas(Fase.FINAL) == 1;
                default:
                    return false;
            }
        }
    }