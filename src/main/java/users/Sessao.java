package users;

import Enums.Funcao;

public class Sessao{//singleton (permite so uma instancia) para guardar o usuario logado {
    private static Sessao instancia;
    private Usuario usuarioLogado;

    private Sessao() {}  // construtor privado = ninguém instancia de fora

    public static Sessao getInstancia() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public void login(Usuario u) { this.usuarioLogado = u; }
    public void logout() { this.usuarioLogado = null; }
    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public Funcao getFuncaoLogado() { return usuarioLogado.getFuncao(); }
}
