package services.matches;

import Enums.StatusPartida;
import matches.ClassificacaoGrupo;
import matches.EstatisticaPartida;
import matches.EstatisticaTime;
import matches.PartidaGrupo;
import nationsAndPlayers.nations.Selecoes;
import stadiumAndRefeering.Estadio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ClassificacaoService {
    public static List<ClassificacaoGrupo> gerarTabelaGrupo(String grupo, List<PartidaGrupo> partidas,List<EstatisticaTime> C,List<EstatisticaTime> V) {
        //Cria um map para facilitar na busca da seleção
        Map<String, ClassificacaoGrupo> tabela = new HashMap<>();
        for(PartidaGrupo p : partidas){
            if(!p.getGrupo().equalsIgnoreCase(grupo)) continue;
            if(p.getStatus() != StatusPartida.FINALIZADA) continue;
            EstatisticaTime estCasa = buscarEstatisticaPorId(p.getId(), C);
            EstatisticaTime estVisitante = buscarEstatisticaPorId(p.getId(), V);
            if(estCasa == null || estVisitante == null) continue;
            Selecoes casa = p.getSelecaoCasa();
            Selecoes visitante = p.getSelecaoVisitante();
            //Se ainda não tem sua ClassificaçãoGrupo no Map
            tabela.putIfAbsent(casa.getNome(), new ClassificacaoGrupo(casa));
            tabela.putIfAbsent(visitante.getNome(), new ClassificacaoGrupo(visitante));
            ClassificacaoGrupo timeCasa = tabela.get(casa.getNome());
            ClassificacaoGrupo timeVisitante = tabela.get(visitante.getNome());
            //Atualiza gols a favor e contra
            int golsCasa = estCasa.getGols();
            int golsVisitante = estVisitante.getGols();
            timeCasa.adicionarGolsPro(golsCasa);
            timeCasa.adicionarGolsContra(golsVisitante);
            //VISITANTE
            timeVisitante.adicionarGolsPro(golsVisitante);
            timeVisitante.adicionarGolsContra(golsCasa);
            //Adicionando Pontuação
            if(golsCasa > golsVisitante){
                timeCasa.adicionarVitoria();
            }
            else if(golsCasa < golsVisitante){
                timeVisitante.adicionarVitoria();
            }
            else{
                timeCasa.adicionarEmpate();
                timeVisitante.adicionarEmpate();
            }
        }
        //Retorna os valores de dentro do map para a lista
        List<ClassificacaoGrupo> classificacao = new ArrayList<>(tabela.values());
        //Ordena de acordo com os critérios
        //1° pontos, 2° saldo de gols e 3° gols pro
        //Primeiro ele ordena por pontos, se tiver a mesma pontuação compara o saldo e se tiver o mesmo saldo, compara gols pro
        classificacao.sort((a, b) -> {
            if (b.getPontos() != a.getPontos()) return b.getPontos() - a.getPontos();
            if (b.getSaldoGols() != a.getSaldoGols()) return b.getSaldoGols() - a.getSaldoGols();
            return b.getGolsPro() - a.getGolsPro();
        });
        return classificacao;
    }
    public static EstatisticaTime buscarEstatisticaPorId(int idPartida, List<EstatisticaTime> estatisticas){
        for(EstatisticaTime e : estatisticas){
            if(e.getId() == idPartida){
                return e;
            }
        }
        return null;
    }
    public static void salvarCampeaoHistorico(String campeao, Estadio estadio, int ano,String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(campeao + ";" + ano +";" + estadio.getNome() + "," + estadio.getLocal());
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
