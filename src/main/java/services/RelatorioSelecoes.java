package services;

import services.files.SelecoesFile;
import nationsAndPlayers.nations.Selecoes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class RelatorioSelecoes {

    public static void gerar(String nomeArquivo) {
        String pasta   = System.getProperty("user.dir") + "/relatorios/";
        String caminho = pasta + nomeArquivo;
        new File(pasta).mkdirs();

        List<Selecoes> lista = SelecoesFile.getInstance().getListaSelecoes();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {

            pw.println("========================================");
            pw.println("          COPA 26 - SELECOES            ");
            pw.println("========================================");
            pw.println("Gerado em: " + LocalDate.now());
            pw.println();

            String fmt = "%-25s %-10s %-10s %-15s %-10s%n";

            pw.printf(fmt, "Nome", "Grupo", "Ranking", "Participacoes", "Titulos");
            pw.println("------------------------------------------------------------------------");

            for (Selecoes s : lista) {
                pw.printf(fmt,
                        nvl(s.getNome()),
                        nvl(s.getGrupo()),
                        nvl(s.getRanking()),
                        nvl(s.getParticipacao()),
                        nvl(s.getTitulo()));
            }

            pw.println("------------------------------------------------------------------------");
            pw.println("Total: " + lista.size());

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
