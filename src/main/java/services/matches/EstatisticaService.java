package services.matches;

import matches.EstatisticaPartida;
import matches.EstatisticaTime;
import matches.JogadorPartida;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstatisticaService {
    public static EstatisticaPartida buscarPorPartida(List<EstatisticaTime> Casa,List<EstatisticaTime> Visitante, int id ){
        EstatisticaTime guardaCasa = null;
        EstatisticaTime guardaVisitante = null;
        for(EstatisticaTime e : Casa){
            if(e.getId()==id){
                guardaCasa=e;
            }
        }
        for(EstatisticaTime e : Visitante){
            if(e.getId()==id){
                guardaVisitante=e;
            }
        }
        return new EstatisticaPartida(guardaCasa,guardaVisitante);
    }
    public static Map<String, Double> carregarNotasPartida(int idPartida){
        Map<String, Double> notas = new HashMap<>();
        List<JogadorPartida> jp = CarregaArquivoService.carregaArquivo("/database/notas_jogadores.txt",parte -> {
            if(Integer.parseInt(parte[0])==idPartida){
                String[] jogadores = parte[1].split(",");
                for(String j : jogadores){
                    String[] info = j.split(":");
                    String nome = info[0];
                    double nota = Double.parseDouble(info[1]);
                    //adiciona no map
                    notas.put(nome, nota);
                }
            }
            return null;
        });
        return notas;
    }
}
