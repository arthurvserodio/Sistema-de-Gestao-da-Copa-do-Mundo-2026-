package services.files;

import builder.JogadorBuilder;
import nationsAndPlayers.nations.Selecoes;
import nationsAndPlayers.players.Jogadores;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogadoresFile {

    private static JogadoresFile instancia;
    private final List<Jogadores> listaJogadores;

    private JogadoresFile(){
        this.listaJogadores = carregarDadosDoTxt();
    }

    public static synchronized JogadoresFile getInstancia(){
        if(instancia == null){
            instancia = new JogadoresFile();
        }
        return instancia;
    }

    public List<Jogadores> getListaJogadores(){
        return listaJogadores;
    }

    private List<Jogadores> carregarDadosDoTxt(){
        List<Jogadores> jogadores = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/database/Jogadores.txt")))){
            String linha;
            while((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                if(partes.length >= 5){
                    String nome = partes[0];
                    int idade = Integer.parseInt(partes[1]);
                    /*Buscar selecoes na lista de selecoes*/
                    Selecoes selecaoDoJogador = encontrarSelecaoNaListaDeSelecoes(partes[2]);
                    boolean lesionado = Boolean.parseBoolean(partes[3]);
                    boolean suspenso = Boolean.parseBoolean(partes[4]);
                    jogadores.add(new Jogadores(nome, idade, selecaoDoJogador, lesionado, suspenso));
                }
            }
        } catch(IOException e){
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return jogadores;
    }

    private Selecoes encontrarSelecaoNaListaDeSelecoes(String selecaoParaEncontrar){
        for(Selecoes s : SelecoesFile.getInstance().getListaSelecoes()){
            /*se a selecao existe na lista de selecoes, o jogador passa a ser da respectiva selecao*/
            if(s.getNome().equals(selecaoParaEncontrar)){
                return s;
            }
        }
        return null;
    }

    public void salvarNoTxt(){

        File arquivo = new File("src/main/resources/database/Jogadores.txt");
        arquivo.getParentFile().mkdirs();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))){
            for(Jogadores jogadores : listaJogadores){
                bw.write(jogadores.getNome() +";" + jogadores.getIdade() + ";" + jogadores.getSelecao().getNome() + ";" + jogadores.isLesionado() + ";" + jogadores.isSuspenso());
                bw.newLine();

            }
        }catch(IOException e){
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

}
