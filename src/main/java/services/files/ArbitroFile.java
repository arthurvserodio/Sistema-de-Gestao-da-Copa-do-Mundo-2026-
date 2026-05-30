package services.files;


import stadiumAndRefeering.Estadio;
import users.Arbitro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArbitroFile {
    private static ArbitroFile instancia;
    private final List<Arbitro> listaArbitros;

    /// /// Instancia para abrir só uma vez
    public static synchronized ArbitroFile getInstance() {
        if (instancia == null) {
            instancia = new ArbitroFile();
        }
        return instancia;
    }

    private ArbitroFile() {
        this.listaArbitros = carregaDados();
    }

    public List<Arbitro> getListaArbitros() {
        return listaArbitros;
    }

    private List<Arbitro> carregaDados(){
        List<Arbitro> arbitros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                // Metodo para fazer a leitura do arquivo
                new InputStreamReader(getClass().getResourceAsStream("/database/arbitrosNaCopa.txt")))) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    String nome = partes[0];
                    int experiencia = Integer.parseInt(partes[2].trim());
                    String nacionalidade = partes[1];
                    arbitros.add(new Arbitro(nome,nacionalidade, experiencia));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler: " + e.getMessage());
        }
        return arbitros;

    }
    public void salvarNoTxt() {
        File arquivo = new File("src/main/resources/database/arbitrosNaCopa.txt");
        arquivo.getParentFile().mkdirs(); // cria a pasta se não existir

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (Arbitro arbitro : listaArbitros) {
                bw.write(arbitro.getNome() + ";" +arbitro.getPais()  + ";" + arbitro.getExperiencia());
                bw.newLine();// pula uma linha para não ficar tudo junto
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }




}
