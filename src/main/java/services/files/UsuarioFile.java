package services.files;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import users.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class UsuarioFile {
    private static UsuarioFile instancia;       // Singleton (so vai permitir uma instancia da lista para nao criar toda vez que usar)
    private ObservableList<Usuario> cache;               // lista em memória

    private UsuarioFile() {}   //ser private o construtor garante que nao vai criar mais de uma

    public static UsuarioFile getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioFile();  //cria a primeira vez a instancia
        }
        return instancia;
    }

    public ObservableList<Usuario> listarTodos() {
        if (cache == null) {               // só lê o arquivo na primeira vez
            cache = lerDoCSV();
        }
        return cache;
    }


    private ObservableList<Usuario> lerDoCSV() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();

        try (
                InputStream is = getClass().getResourceAsStream("/database/usuarios.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is))
        ) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] colunas = linha.split(",");
                // colunas[0] = nome, colunas[1] = funcao, colunas[2] = pais, colunas[3]=status, colunas[4]=senha
                lista.add(new Usuario(colunas[0], colunas[1], colunas[2], colunas[3], colunas[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarCSV(Usuario u) {
        // adiciona no cache
        listarTodos().add(u);

        // adiciona no arquivo
        try {
            java.net.URL url = getClass().getResource("/database/usuarios.csv"); //pega o caminnho
            java.io.File arquivo = new java.io.File(url.toURI()); //transforma para File

            try (java.io.FileWriter fw = new java.io.FileWriter(arquivo, true); //o true faz ele escrever no final e nao apagar o que tinha antes
                 java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
                bw.newLine();
                bw.write(u.getNome() + "," + u.getFuncao() + "," + u.getPais() + "," + u.getStatus() + "," + u.getSenha());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removerCSV(Usuario u) {
        // remove do cache
        listarTodos().removeIf(usuario -> usuario.getNome().equals(u.getNome()));

        // reescreve o arquivo inteiro sem o usuario removido
        try {
            java.net.URL url = getClass().getResource("/database/usuarios.csv");
            java.io.File arquivo = new java.io.File(url.toURI());

            try (java.io.FileWriter fw = new java.io.FileWriter(arquivo, false); // false apaga e reescreve tudo
                 java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
                for (int i = 0; i < cache.size(); i++) {
                    Usuario atual = cache.get(i);
                    bw.write(atual.getNome() + "," + atual.getFuncao() + "," + atual.getPais() + "," + atual.getStatus() + "," + atual.getSenha());
                    if (i < cache.size() - 1) {
                        bw.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarCSV(){
        // reescreve o arquivo inteiro com o usuario editado
        try {
            java.net.URL url = getClass().getResource("/database/usuarios.csv");
            java.io.File arquivo = new java.io.File(url.toURI());

            try (java.io.FileWriter fw = new java.io.FileWriter(arquivo, false); // false apaga e reescreve tudo
                 java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
                for (int i = 0; i < cache.size(); i++) {
                    Usuario atual = cache.get(i);
                    bw.write(atual.getNome() + "," + atual.getFuncao() + "," + atual.getPais() + "," + atual.getStatus() + "," + atual.getSenha());
                    if (i < cache.size() - 1) {
                        bw.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}