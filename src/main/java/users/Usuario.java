package users;

import Enums.Funcao;

public class Usuario {
    private String nome;
    private Funcao funcao;
    private String pais;
    private String status;
    private String senha;

    public Usuario(){}


    public Usuario(String nome, String funcao, String pais, String status, String senha) {
        this.nome = nome;
        if(funcao!=null){
            this.funcao = Funcao.valueOf(funcao);
        }
        this.pais = pais;
        this.status = status;
        this.senha = senha;
    }

    public Usuario(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public String getSenha(){ return senha; }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
