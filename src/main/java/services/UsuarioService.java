package services;

import Enums.Funcao;
import exceptions.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.files.UsuarioFile;
import users.Sessao;
import users.Usuario;

public class UsuarioService {
    public static void adicionarUsuario(String nome, String funcao, String status, String pais, String senha, String senha2) throws CamposVaziosException, UsuarioExisteException, SenhasDiferemException, FuncaoInvalidaException, StatusInvalidoException{
        if(nome.isEmpty() || funcao.isEmpty() || status.isEmpty() || pais.isEmpty() || senha.isEmpty() || senha2.isEmpty()){
            throw new CamposVaziosException("Preencha todos os campos");
        }

        for (Usuario existente : UsuarioFile.getInstancia().listarTodos()) {
            if (existente.getNome().equals(nome)) {
                throw new UsuarioExisteException("Usuário já existe.");
            }
        }

        if(!senha.equals(senha2)){
            throw new SenhasDiferemException("As senhas são diferentes");
        }

        if(!funcao.equalsIgnoreCase("ORGANIZADOR") && !funcao.equalsIgnoreCase("ADMINISTRADOR") && !funcao.equalsIgnoreCase("ARBITRO")){
            throw new FuncaoInvalidaException("Funcao invalida");
        }

        if(!status.equalsIgnoreCase("ATIVO") && !status.equalsIgnoreCase("INATIVO")){
            throw new StatusInvalidoException("Status invalido");
        }


        Usuario u = new Usuario(nome, funcao.toUpperCase(), pais, status, senha);

        UsuarioFile.getInstancia().adicionarCSV(u);
    }

    public static void removerUsuario(Usuario u) throws RemoveSiMesmoException{
        if(u.getNome()== Sessao.getInstancia().getUsuarioLogado().getNome()){
            throw new RemoveSiMesmoException("Não é possível remover a própria conta por aqui");
        }
        UsuarioFile.getInstancia().removerCSV(u);
    }


}
