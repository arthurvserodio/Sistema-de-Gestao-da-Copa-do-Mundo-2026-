package services;

import services.files.UsuarioFile;
import users.Usuario;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class RelatorioUsuarios {

    public static void gerar(String nomeArquivo) {
        String pasta   = System.getProperty("user.dir") + "/relatorios/";
        String caminho = pasta + nomeArquivo;
        new File(pasta).mkdirs(); //cria pasta se nao existir

        List<Usuario> lista = UsuarioFile.getInstancia().listarTodos();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {


            pw.println("========================================");
            pw.println("           COPA 26 - USUARIOS           ");
            pw.println("========================================");
            pw.println("Gerado em: " + LocalDate.now());
            pw.println();


            String fmt = "%-25s %-15s %-15s %-10s%n"; //da alinhamento do printf

            pw.printf(fmt, "Nome", "Funcao", "Pais", "Status");
            pw.println("------------------------------------------------------------------");

            for (Usuario u : lista) {
                String funcao = u.getFuncao() != null ? u.getFuncao().toString() : "-";
                pw.printf(fmt,
                        nvl(u.getNome()),
                        funcao,
                        nvl(u.getPais()),
                        nvl(u.getStatus()));
            }

            pw.println("------------------------------------------------------------------");


            long ativos = lista.stream()
                    .filter(u -> "ativo".equalsIgnoreCase(u.getStatus()))
                    .count();

            pw.println("Total : " + lista.size());
            pw.println("Ativos: " + ativos + "  |  Inativos: " + (lista.size() - ativos));

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
