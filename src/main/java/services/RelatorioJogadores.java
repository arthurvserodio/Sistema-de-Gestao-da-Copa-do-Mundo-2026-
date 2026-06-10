package services;

import services.files.JogadoresFile;
import nationsAndPlayers.players.Jogadores;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class RelatorioJogadores {

    public static void gerar(String nomeArquivo) {
        String pasta   = System.getProperty("user.dir") + "/relatorios/";
        String caminho = pasta + nomeArquivo;
        new File(pasta).mkdirs();

        List<Jogadores> lista = JogadoresFile.getInstancia().getListaJogadores();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {

            pw.println("========================================");
            pw.println("          COPA 26 - JOGADORES           ");
            pw.println("========================================");
            pw.println("Gerado em: " + LocalDate.now());
            pw.println();

            String fmt = "%-25s %-6s %-15s %-5s %-20s %-10s %-10s%n";

            pw.printf(fmt, "Nome", "Idade", "Posicao", "Num", "Selecao", "Lesionado", "Suspenso");
            pw.println("-------------------------------------------------------------------------------------");

            for (Jogadores j : lista) {
                String selecao = (j.getSelecao() != null) ? j.getSelecao().getNome() : "-";
                pw.printf(fmt,
                        nvl(j.getNome()),
                        String.valueOf(j.getIdade()),
                        nvl(j.getPosicao()),
                        String.valueOf(j.getNumeracao()),
                        selecao,
                        j.isLesionado() ? "Sim" : "Nao",
                        j.isSuspenso()  ? "Sim" : "Nao");
            }

            pw.println("-------------------------------------------------------------------------------------");

            long lesionados = lista.stream().filter(Jogadores::isLesionado).count();
            long suspensos  = lista.stream().filter(Jogadores::isSuspenso).count();

            pw.println("Total     : " + lista.size());
            pw.println("Lesionados: " + lesionados + "  |  Suspensos: " + suspensos);

            System.out.println("Relatorio gerado em: " + caminho);
            Desktop.getDesktop().open(new File(caminho));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String nvl(String valor) {
        return valor != null ? valor : "-";
    }
}
