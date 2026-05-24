package services.matches;

import nationsAndPlayers.nations.Campeoes;
import nationsAndPlayers.nations.Selecoes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CarregaArquivoService {
    //Leitura Genérica de um arquivo, a qual recebe o caminho para o arquivo
    public static <T> List<T> carregaArquivo(String caminho, Function<String[], T> conversor){
        List <T> lista=new ArrayList<>(); //Cria uma lista genérica
        try {
            //Leitura do arquivo
            BufferedReader br = new BufferedReader(new InputStreamReader(CarregaArquivoService.class.getResourceAsStream(caminho)));
            String linha;
            while ((linha = br.readLine()) != null) {
                //Como asinformações contidas no arquivo estão separadas por ; usamos ele como separador no split
                String[] partes = linha.split(";");
                T objetoGenerico=conversor.apply(partes); //Pega as partes e coloca no obj genérico
                lista.add(objetoGenerico); //Adiciona na lista
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
