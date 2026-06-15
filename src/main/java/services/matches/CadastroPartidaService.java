package services.matches;

import Enums.Fase;
import Enums.StatusPartida;
import builder.PartidaEliminatoriaBuilder;
import builder.PartidaGrupoBuilder;
import exceptions.IllegalIntervaloEntrePartidaException;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import matches.*;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;
import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

public class CadastroPartidaService {
    //O metodo atualizaComboBox faz com que não haja uma exceção em relação a fase de grupos, visto que seleções de grupos diferentes não podem se enfrentar nessa fase
    //Assim, caso o usuario selecione uma seleção em um dos ComboBox destinado a escolha da seleção, a outra ComboBox atualiza e só dá opções de seleções que estão no mesmo grupo da selecionada
    public void atualizarComboBox(ComboBox<Selecoes> origem, ComboBox<Selecoes> destino, List<Selecoes> ListSelecoes, Fase atual){
        Selecoes selecionada= origem.getValue(); //Pega a seleção da comboBox
        if(selecionada==null) return;
        if(atual==Fase.FASE_DE_GRUPOS){
            String grupo= selecionada.getGrupo();
            //Filtra a lista de todas as seleções para ver os países que estão no mesmo grupo da selecionada
            List<Selecoes> mesmoGrupo=ListSelecoes.stream().filter(s -> s.getGrupo().equals(grupo) && !s.toString().equals(selecionada.toString())).toList();
            destino.getItems().clear(); //Limpa o que tava na comboBox anteriormente
            destino.getItems().addAll(mesmoGrupo); //Adiciona a nova lista
        }
    }
    public void atualizarComboBoxEliminatoria(ComboBox<Selecoes> origem, ComboBox<Selecoes> destino, Fase atual,List<Selecoes> classificados){
        Selecoes selecionada= origem.getValue(); //Pega a seleção da comboBox
        if(selecionada==null) return;
        List<Selecoes> adversarios = classificados.stream().filter(s -> !s.equals(selecionada)).toList();
        destino.getItems().clear(); //Limpa o que tava na comboBox anteriormente
        destino.getItems().addAll(adversarios); //Adiciona a nova lista

    }
    //Isso obtem a classificação da fase de grupos
    public static List<Selecoes> obterClassificados(List<PartidaGrupo> partidas, List<EstatisticaTime> casa, List<EstatisticaTime> visitante) {
        List<Selecoes> classificados = new ArrayList<>();
        //Ajuda para descobrir os melhores 3° colocados
        List<ClassificacaoGrupo> terceiros = new ArrayList<>();
        for(char grupo = 'A'; grupo <= 'L'; grupo++) {
            List<ClassificacaoGrupo> tabela = ClassificacaoService.gerarTabelaGrupo(String.valueOf(grupo), partidas, casa, visitante);
            classificados.add(tabela.get(0).getSelecao());
            classificados.add(tabela.get(1).getSelecao());
            terceiros.add(tabela.get(2));
        }
        //Ordena os 3° colocados por pontos,saldo e gols pro
        terceiros.sort(Comparator.comparingInt(ClassificacaoGrupo::getPontos).thenComparingInt(ClassificacaoGrupo::getSaldoGols).thenComparingInt(ClassificacaoGrupo::getGolsPro).reversed());
        for(int i =0 ;i<8;i++){
            classificados.add(terceiros.get(i).getSelecao());
        }
        return classificados;
    }
    //Obtem classificados para fases depois dos playoffs
    public static List<Selecoes> obterVencedores(Fase faseAnterior, List<PartidaEliminatoria> partidas, List<EstatisticaTime> casa, List<EstatisticaTime> visitante){
        List<Selecoes> vencedores = new ArrayList<>();
        for(PartidaEliminatoria p : partidas){
            if(p.getFase() != faseAnterior) continue;
            if(p.getStatus() != StatusPartida.FINALIZADA) continue;
            EstatisticaTime estCasa = ClassificacaoService.buscarEstatisticaPorId(p.getId(), casa);
            EstatisticaTime estVisitante = ClassificacaoService.buscarEstatisticaPorId(p.getId(), visitante);
            if(estCasa == null || estVisitante == null) continue;
            int golsCasa = estCasa.getGols();
            int golsVisitante = estVisitante.getGols();
            if(golsCasa > golsVisitante){
                vencedores.add(p.getSelecaoCasa());
            }
            else if(golsVisitante > golsCasa){
                vencedores.add(p.getSelecaoVisitante());
            }
        }
        return vencedores;
    }
    //Pega a lista com todos os estadios cadastrados e recebe a data para verificar disponibilidade
    public void estadiosDisponivel(List<Estadio> estadios, LocalDate dataPartida, ComboBox<Estadio> disponiveis){
        disponiveis.getItems().clear();
        for (Estadio e : estadios){
            //Se a data não foi encontrada no set, então é pq tem disponibilidade
            if(!e.getDatasOcupadas().contains(dataPartida)){
                disponiveis.getItems().add(e);
            }
        }
    }
    //Pega a lista com todos os arbitros cadastrados e recebe a data para verificar disponibilidade
    public void arbitroDisponivel(List<Arbitro> arbitros, LocalDate dataPartida, ComboBox<Arbitro> disponiveis,String paisSelecao1,String paisSelecao2){
        disponiveis.getItems().clear();
        for (Arbitro a : arbitros){
            //Se ao chamar o metodo estaDisponivel do arbitro e estiver true, então tem disponibilidade
            boolean estaDisponivel = a.estaDisponivel(dataPartida);
            boolean neutro = !a.getPais().equalsIgnoreCase(paisSelecao1) &&
                    !a.getPais().equalsIgnoreCase(paisSelecao2);
            if (estaDisponivel && neutro) {
                disponiveis.getItems().add(a);
            }
        }
    }
    //Verifica se a partida que está sendo cadastrada já existe
    public boolean partidaJaExiste(Selecoes s1, Selecoes s2, List<Partida> jogos){
        for(Partida p : jogos){
            boolean mesmaOrdem= p.getSelecaoCasa().equals(s1) && p.getSelecaoVisitante().equals(s2); //Verifica se o usuario colocou as seleções na mesma Ordem
            boolean inverteOrdem= p.getSelecaoCasa().equals(s2) && p.getSelecaoVisitante().equals(s1); //Verifica se colocou as seleções em ordem invertida
            if(mesmaOrdem || inverteOrdem){
                return true; //A partida já existe e não podera ser cadastrada outra
            }
        }
        return false; //A partida não existe, pode cadastrar
    }
    //Verifica se o cadastro respeita o intervalo de 72H para uma nova partida de uma seleção estabelecido pela FIFA
    public void validarIntervalo(Selecoes s, LocalDate novaData, List<Partida> jogos) throws IllegalIntervaloEntrePartidaException {
        for(Partida p : jogos){
            if(p.getSelecaoCasa().equals(s) || p.getSelecaoVisitante().equals(s)){
                long dias = Math.abs(ChronoUnit.DAYS.between(p.getData(),novaData));
                if(dias<3){
                    throw new IllegalIntervaloEntrePartidaException(s.getNome() + " jogou no dia " + p.getData() + ". O jogo não poderá ser realizado na data: " + novaData + ", pois a seleção precisa de 3 dias de descanso");
                }
            }
        }
    }
    public static void salvarPartida(Partida partida, String path){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            if(partida instanceof PartidaGrupo){
                //id;tipo;data;horario;estadio;arbitro;grupo;casa;visitante;fase;status;
                bw.write(partida.getId() + ";" + "GRUPO;" + partida.getData() + ";" + partida.getHorario() + ";" + partida.getEstadio() + ";" + partida.getArbitro() + ";" + ((PartidaGrupo) partida).getGrupo() + ";" + partida.getSelecaoCasa()+ ";" + partida.getSelecaoVisitante() + ";" + partida.getFase() +";"+  "AGENDADA;");
                bw.newLine();
                bw.close();
            }
            else if(partida instanceof PartidaEliminatoria){
                //id;tipo;data;horario;estadio;arbitro;casa;visitante;fase;status;
                bw.write(partida.getId() + ";" + "ELIMINATORIA;" + partida.getData() + ";" + partida.getHorario() + ";" + partida.getEstadio() + ";" + partida.getArbitro()  + ";" + partida.getSelecaoCasa()+ ";" + partida.getSelecaoVisitante() + ";" + partida.getFase() +";"+  "AGENDADA;");
                bw.newLine();
                bw.close();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public int gerarProximoIdGlobal() {

        List<PartidaGrupo> grupos = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte -> new PartidaGrupoBuilder().id(Integer.parseInt(parte[0])).build());
        List<PartidaEliminatoria> eliminatorias = CarregaArquivoService.carregaArquivo("/database/partida_eliminatoria.txt", parte -> new PartidaEliminatoriaBuilder().id(Integer.parseInt(parte[0])).build());
        int maiorGrupo = grupos.stream().mapToInt(Partida::getId).max().orElse(0);
        int maiorEliminatoria = eliminatorias.stream().mapToInt(Partida::getId).max().orElse(0);
        return Math.max(maiorGrupo, maiorEliminatoria) + 1;
    }
    public static <T> T buscaPeloNome(List<T> lista, Function<T, String> extratorNome,String nome){
        //Percorre a lista até encontrar o objeto
        for(T obj : lista){
            if(extratorNome.apply(obj).equalsIgnoreCase(nome)){
                return obj;
            }
        }
        return null;
    }
    public static void removePartidaDoTXT(int idDaPartida,String path){
        try {
            List<String> linhas = Files.readAllLines(Paths.get(path));
            linhas.removeIf(linha -> {
                String[] dados = linha.split(";");
                return Integer.parseInt(dados[0]) == idDaPartida;
            });
            Files.write(Paths.get(path),linhas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static VBox criarJogadorVisual(Jogadores j){
        Circle circulo=new Circle(18);
        circulo.setStyle("""
        -fx-fill: white;
        -fx-stroke: black;
        -fx-stroke-width: 2;""");
        //Numeração
        Label num=new Label(String.valueOf(j.getNumeracao()));
        num.setStyle("""
        -fx-font-size: 14;
        -fx-font-weight: bold;""");
        //Montando a camisa
        StackPane camisa=new StackPane(circulo,num);
        //Nome do jogador
        Label nome=new Label(j.getNome());
        nome.setStyle("""
            -fx-background-color: rgba(0,0,0,0.65);
            -fx-text-fill: white;
            -fx-background-radius: 8;
            -fx-padding: 4 10 4 10;
            -fx-font-size: 10px;
            -fx-font-family: "Roboto Condensed Black";""");
        //Cria o elemento
        VBox vbox = new VBox(3);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(camisa,nome);
        return vbox;
    }
    public static VBox criarJogadorVisualPartida(JogadorPartida jp){
        Circle circulo=new Circle(12);
        circulo.setStyle("""
        -fx-fill: white;
        -fx-stroke: black;
        -fx-stroke-width: 2;""");
        //Numeração
        Label num=new Label(String.valueOf(jp.getJogador().getNumeracao()));
        num.setStyle("""
        -fx-font-size: 9;
        -fx-font-weight: bold;""");
        //Montando a camisa
        StackPane camisa=new StackPane(circulo,num);
        //Nome do jogador
        Label nome=new Label(jp.getJogador().getNome());
        nome.setStyle("""
            -fx-background-color: rgba(0,0,0,0.65);
            -fx-text-fill: white;
            -fx-background-radius: 8;
            -fx-padding: 4 10 4 10;
            -fx-font-size: 8px;
            -fx-font-family: "Roboto Condensed Black";""");
        //Nota do jogador
        Label nota = new Label(String.format("%.1f", jp.getNota()));
        nota.setStyle("""
        -fx-background-color: #F5EBD3;
        -fx-background-radius: 12;
        -fx-padding: 2 8 2 8;
        -fx-font-size: 8;
        -fx-font-weight: bold;
    """);
        //Cria o elemento
        VBox vbox = new VBox(2);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(camisa,nome,nota);
        return vbox;
    }
    //Coloca a VBox criada para o jogador e a coloca no campo
    public static VBox adicionandoNoCampo(Jogadores j, AnchorPane campo,double x,double y){
        VBox visual=criarJogadorVisual(j);
        visual.setLayoutX(x);
        visual.setLayoutY(y);
        campo.getChildren().add(visual);
        return visual;
    }
    //Coloca a VBox criada para o jogador e a coloca no campo
    public static VBox adicionandoNoCampoPartida(JogadorPartida jp, AnchorPane campo,double x,double y){
        VBox visual=criarJogadorVisualPartida(jp);
        visual.setLayoutX(x);
        visual.setLayoutY(y);
        campo.getChildren().add(visual);
        return visual;
    }
    //Remove o jogador do campo
    public static void removeJogadorDoCampo(VBox visual,AnchorPane campo){
        campo.getChildren().remove(visual);
    }
    //Salva a escalação após escalar as duas seleções para a partida
    public static void salvarEscalacao(Partida partida, String path){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            //ID_PARTIDA;CASA/VISITANTE;FORMAÇÃO;JOGADORES(NOME|TITULAR OU RESERVA)
            bw.write(partida.getId() + ";" + "CASA;" + partida.getEscalacaoCasa().getFormacao() + ";");
            for(JogadorPartida j : partida.getEscalacaoCasa().getTitulares()){
                bw.write(j.getJogador().getNome()+"|"+"T"+";");
            }
            for(JogadorPartida j : partida.getEscalacaoCasa().getReservas()){
                bw.write(j.getJogador().getNome()+"|"+"R"+";");
            }
            bw.newLine();
            bw.write(partida.getId() + ";" + "VISITANTE;" + partida.getEscalacaoVisitante().getFormacao() + ";");
            for(JogadorPartida j : partida.getEscalacaoVisitante().getTitulares()){
                bw.write(j.getJogador().getNome()+"|"+"T"+";");
            }
            for(JogadorPartida j : partida.getEscalacaoVisitante().getReservas()){
                bw.write(j.getJogador().getNome()+"|"+"R"+";");
            }
            bw.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    //Após as eslações das duas partidas, o status da partida deve mudar de AGENDADA -> EM_ANDAMENTO
    public static void atualizaStatusDaPartida(int id_partida, Partida partida, EstadoDaCopa fase){
        try{
            List<String> todasAsLinhas;
            if(fase.getFaseAtual().equals(Fase.FASE_DE_GRUPOS)){
                todasAsLinhas=Files.readAllLines(Paths.get("src/main/resources/database/partida_grupo.txt"));
            }
            else{
                todasAsLinhas=Files.readAllLines(Paths.get("src/main/resources/database/partida_eliminatoria.txt"));
            }
            List<String> novaLinha=new ArrayList<>();
            for(String l:todasAsLinhas){
                String[] parte=l.split(";");
                int id= Integer.parseInt(parte[0]);
                if(id==id_partida && partida.getStatus().equals(StatusPartida.AGENDADA)){
                    if(fase.getFaseAtual().equals(Fase.FASE_DE_GRUPOS)){
                        parte[10]="EM_ANDAMENTO";
                    }
                    else{
                        parte[9]="EM_ANDAMENTO";
                    }
                    novaLinha.add(String.join(";",parte));
                }
                else if(id==id_partida && partida.getStatus().equals(StatusPartida.EM_ANDAMENTO)){
                    if(fase.getFaseAtual().equals(Fase.FASE_DE_GRUPOS)){
                        parte[10]="FINALIZADA";
                    }
                    else{
                        parte[9]="FINALIZADA";
                    }
                    novaLinha.add(String.join(";",parte));
                }
                else{
                    novaLinha.add(l);
                }
            }
            if(fase.getFaseAtual().equals(Fase.FASE_DE_GRUPOS)){
                Files.write(Paths.get("src/main/resources/database/partida_grupo.txt"),novaLinha);
                Files.write(Paths.get("target/classes/database/partida_grupo.txt"),novaLinha);
            }
            else{
                Files.write(Paths.get("src/main/resources/database/partida_eliminatoria.txt"),novaLinha);
                Files.write(Paths.get("target/classes/database/partida_eliminatoria.txt"),novaLinha);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //Salavando eventos ocorridos na partida no arquivo
    public static void salvarEvento(EventosOcorridos evento, int id, Selecoes selecao,String path){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            //id;tipo;tipoDeEvento;minuto;seleção;jogador
            bw.write(id + ";" + "GRUPO;" + evento.getTipo() + ";" + evento.getMinuto() + ";" + selecao + ";" + evento.getJogador());
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    //Apaga o Evento e reescreve o arquivo
    public static void reescreverArquivo(List <String[]> TodosOsEventos, String path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for(String[] e : TodosOsEventos){
                bw.write(String.join(";", e));
                bw.newLine();
            }

        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public static void salvarEstatisticaPartida(Partida partida, String path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){

            EstatisticaTime casa = partida.getEstatistica().getEstatisticaCasa();
            EstatisticaTime visitante = partida.getEstatistica().getEstatisticaVisitante();

            bw.write(
                    partida.getId() + ";" +
                            casa.getGols() + ";" +
                            visitante.getGols() + ";" +
                            casa.getChutes() + ";" +
                            visitante.getChutes() + ";" +
                            casa.getChutesAGol() + ";" +
                            visitante.getChutesAGol() + ";" +
                            casa.getPosseDeBola() + ";" +
                            visitante.getPosseDeBola() + ";" +
                            casa.getPasses() + ";" +
                            visitante.getPasses() + ";" +
                            casa.getPrecisaoDosPasses() + ";" +
                            visitante.getPrecisaoDosPasses() + ";" +
                            casa.getFaltas() + ";" +
                            visitante.getFaltas() + ";" +
                            casa.getCartoesAmarelos() + ";" +
                            visitante.getCartoesAmarelos() + ";" +
                            casa.getCartoesVermelhos() + ";" +
                            visitante.getCartoesVermelhos() + ";" +
                            casa.getImpedimentos() + ";" +
                            visitante.getImpedimentos() + ";" +
                            casa.getEscanteios() + ";" +
                            visitante.getEscanteios()
            );

            bw.newLine();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //Salvando as notas dos jogadores
    public static void salvarNotasJogadores(int idPartida, List<JogadorPartida> notasJogadores, String path){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){
            StringBuilder sb = new StringBuilder();
            sb.append(idPartida).append(";");
            for(int i = 0; i < notasJogadores.size(); i++){
                JogadorPartida jp = notasJogadores.get(i);
                sb.append(jp.getJogador().getNome()).append(":").append(jp.getNota());
                if(i < notasJogadores.size() - 1){
                    sb.append(",");
                }
            }
            bw.write(sb.toString());
            bw.newLine();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //Salvando a Fase em que a Copa DO mundo esta, no arquivo Estado da Copa
    public static void salvarEstadoDaCopa(EstadoDaCopa estado, String path){
        //Só vai ter uma linha de arquivo, visto que cada novo estado sobreescreve o anterior
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            //FASE;DATA INICIO;DATA FIM;
            bw.write(estado.getFaseAtual() + ";" + estado.getInicio() + ";" + estado.getFim());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //Conta as partidas finalizadas para ver se pode passar para a próxima fase da copa do mundo
    public static int contarPartidasFinalizadas(Fase fase) {
        int contador = 0;
        List<? extends Partida> partidas;
        if(fase==Fase.FASE_DE_GRUPOS){
            partidas = CarregaArquivoService.carregaArquivo("/database/partida_grupo.txt", parte-> new PartidaGrupoBuilder().fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        }
        else{
            partidas = CarregaArquivoService.carregaArquivo("/database/partida_eliminatoria.txt", parte-> new PartidaEliminatoriaBuilder().fase(Fase.valueOf(parte[9])).status(StatusPartida.valueOf(parte[10])).build());
        }
        for (Partida p : partidas) {
            //Verifica se finalizou a partida e aumenta o contador
            if (p.getFase() == fase && p.getStatus() == StatusPartida.FINALIZADA) {
                contador++;
            }
        }
        return contador;
    }
}
