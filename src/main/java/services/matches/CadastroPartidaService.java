package services.matches;

import Enums.Fase;
import exceptions.IllegalIntervaloEntrePartidaException;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import matches.JogadorPartida;
import matches.Partida;
import matches.PartidaGrupo;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    public void arbitroDisponivel(List<Arbitro> arbitros, LocalDate dataPartida, ComboBox<Arbitro> disponiveis){
        disponiveis.getItems().clear();
        for (Arbitro a : arbitros){
            //Se ao chamar o metodo estaDisponivel do arbitro e estiver true, então tem disponibilidade
            if(a.estaDisponivel(dataPartida)){
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
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public int gerarProximoId(List<Partida> partidas){
        if(partidas.isEmpty()){
            return 1;
        }
        int maior=partidas.stream().mapToInt(Partida::getId).max().orElse(0); //Pega o maior Id presente na lista
        return maior+1;
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
    //Coloca a VBox criada para o jogador e a coloca no campo
    public static VBox adicionandoNoCampo(Jogadores j, AnchorPane campo,double x,double y){
        VBox visual=criarJogadorVisual(j);
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
    public static void atualizaStatusDaPartida(int id_partida){
        try{
            List<String> todasAsLinhas=Files.readAllLines(Paths.get("src/main/resources/database/partida_grupo.txt"));
            List<String> novaLinha=new ArrayList<>();
            for(String l:todasAsLinhas){
                String[] parte=l.split(";");
                int id= Integer.parseInt(parte[0]);
                if(id==id_partida){
                    parte[10]="EM_ANDAMENTO;";
                    novaLinha.add(String.join(";",parte));
                }
                else{
                    novaLinha.add(l);
                }
            }
            Files.write(Paths.get("src/main/resources/database/partida_grupo.txt"),novaLinha);
            Files.write(Paths.get("target/classes/database/partida_grupo.txt"),novaLinha);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
