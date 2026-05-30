package services.files;

import nationsAndPlayers.nations.Selecoes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SelecoesFile {

    private static SelecoesFile instancia;
    private final List<Selecoes> listaSelecoes;

    private SelecoesFile(){
        this.listaSelecoes = carregarDadosDoTxt();
    }

    public static synchronized SelecoesFile getInstance() {
        if(instancia == null){
            instancia = new SelecoesFile();
        }
        return instancia;
    }
    public List<Selecoes> getListaSelecoes() {
        return listaSelecoes;
    }

    private List<Selecoes> carregarDadosDoTxt(){
        List<Selecoes> selecoes = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/database/SelecoesNaCopa.txt")))){

            String linha;
            while((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                if(partes.length >= 5){
                    String nomeSelecao = partes[0];
                    String grupoSelecao = partes[1];
                    String ranking = partes[2];
                    String participacao = partes[3];
                    String tituloSelecao = partes[4];

                    selecoes.add(new Selecoes(nomeSelecao,grupoSelecao, ranking,participacao, tituloSelecao));
                }
            }
        } catch (IOException e){
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return selecoes;
    }

    public void salvarNoTxt(){

        File arquivo = new File("src/main/resources/database/SelecoesNaCopa.txt");
        arquivo.getParentFile().mkdirs();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))){
            for(Selecoes selecoes : listaSelecoes){
                bw.write(selecoes.getNome() +";" + selecoes.getGrupo() + ";" + selecoes.getRanking() + ";" + selecoes.getParticipacao() + ";" + selecoes.getTitulo());
                bw.newLine();
            }
        }catch(IOException e){
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

}
