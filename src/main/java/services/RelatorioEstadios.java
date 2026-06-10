package services;

import services.files.EstadioFile;
import stadiumAndRefeering.Estadio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class RelatorioEstadios {

    public static void gerar(String nomeArquivo) {
        String pasta   = System.getProperty("user.dir") + "/relatorios/";
        String caminho = pasta + nomeArquivo;
        new File(pasta).mkdirs();

        List<Estadio> lista = EstadioFile.getInstance().getListaEstadios();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {

            pw.println("========================================");
            pw.println("          COPA 26 - ESTADIOS            ");
            pw.println("========================================");
            pw.println("Gerado em: " + LocalDate.now());
            pw.println();

            String fmt = "%-30s %-25s %-15s%n";

            pw.printf(fmt, "Nome", "Local", "Capacidade");
            pw.println("--------------------------------------------------------------------");

            for (Estadio e : lista) {
                pw.printf(fmt,
                        nvl(e.getNome()),
                        nvl(e.getLocal()),
                        String.format("%,d", e.getCapacidade()));
            }

            pw.println("--------------------------------------------------------------------");

            int maior = lista.stream()
                    .mapToInt(Estadio::getCapacidade)
                    .max().orElse(0);

            pw.println("Total            : " + lista.size());
            pw.println("Maior capacidade : " + String.format("%,d", maior));

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
