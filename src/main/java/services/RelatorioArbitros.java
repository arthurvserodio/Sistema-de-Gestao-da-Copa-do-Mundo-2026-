package services;

import services.files.ArbitroFile;
import users.Arbitro;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class RelatorioArbitros {

    public static void gerar(String nomeArquivo) {
        String pasta   = System.getProperty("user.dir") + "/relatorios/";
        String caminho = pasta + nomeArquivo;
        new File(pasta).mkdirs();

        List<Arbitro> lista = ArbitroFile.getInstance().getListaArbitros();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {

            pw.println("========================================");
            pw.println("          COPA 26 - ARBITROS            ");
            pw.println("========================================");
            pw.println("Gerado em: " + LocalDate.now());
            pw.println();

            String fmt = "%-25s %-20s %-20s%n";

            pw.printf(fmt, "Nome", "Nacionalidade", "Experiencia (anos)");
            pw.println("-----------------------------------------------------------");

            for (Arbitro a : lista) {
                pw.printf(fmt,
                        nvl(a.getNome()),
                        nvl(a.getPais()),
                        String.valueOf(a.getExperiencia()));
            }

            pw.println("-----------------------------------------------------------");

            double media = lista.stream()
                    .mapToInt(Arbitro::getExperiencia)
                    .average()
                    .orElse(0);

            pw.println("Total: " + lista.size());
            pw.println("Media de experiencia: " + String.format("%.1f", media) + " anos");

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
