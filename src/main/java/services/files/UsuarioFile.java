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

    private void salvarCSV() {
        // reescreve o arquivo inteiro com o cache atual
    }
}