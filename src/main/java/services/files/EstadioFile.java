package services.files;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import stadiumAndRefeering.Estadio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


    public class EstadioFile {

        private static EstadioFile instancia;
        private final List<Estadio> listaEstadios;


      // private para new uma classe iinstanciar um objeto
        private EstadioFile() {
            this.listaEstadios = carregarDadosDoTxt();
        }

        /// Instancia para abrir só uma vez
        public static synchronized EstadioFile getInstance() {
            if (instancia == null) {
                instancia = new EstadioFile();
            }
            return instancia;
        }

        public List<Estadio> getListaEstadios() {
            return listaEstadios;
        }

/// Leitura de dados
        private List<Estadio> carregarDadosDoTxt() {
            List<Estadio> estadios = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(
                    // Metodo para fazer a leitura do arquivo
                    new InputStreamReader(getClass().getResourceAsStream("/database/Estadios.txt")))) {

                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(";");
                            if (partes.length >= 3) {
                String nome = partes[0];
                        int capacidade = Integer.parseInt(partes[1].trim());
                        String local = partes[2];
                        estadios.add(new Estadio(nome, capacidade, local));
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler: " + e.getMessage());
            }
            return estadios;


        }

/// Salva no disco :
        public void salvarNoTxt() {
            File arquivo = new File("src/main/resources/database/Estadios.txt");
            arquivo.getParentFile().mkdirs(); // cria a pasta se não existir

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                for (Estadio e : listaEstadios) {
                    bw.write(e.getNome() + ";" + e.getCapacidade() + ";" + e.getLocal());
                    bw.newLine();// pula uma linha para não ficar tudo junto
                }
            } catch (IOException e) {
                System.err.println("Erro ao salvar: " + e.getMessage());
            }
        }
    }



