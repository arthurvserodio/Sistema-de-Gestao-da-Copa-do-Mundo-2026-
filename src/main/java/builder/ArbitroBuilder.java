package builder;

import Enums.Funcao;
import users.Arbitro;

public class ArbitroBuilder {
    private String nome;
    private String pais;
    private int experiencia;
    private String funcao;
    private String status;
    private String senha;

    public ArbitroBuilder nome(String nome){
        this.nome=nome;
        return this;
    }
    public ArbitroBuilder pais(String pais){
        this.pais=pais;
        return this;
    }
    public ArbitroBuilder experiencia(int experiencia){
        this.experiencia=experiencia;
        return this;
    }
    public ArbitroBuilder funcao(String funcao){
        this.funcao=funcao;
        return this;
    }
    public ArbitroBuilder status(String status){
        this.status=status;
        return this;
    }
    public ArbitroBuilder senha(String senha){
        this.senha=senha;
        return this;
    }
    public Arbitro build(){
        return new Arbitro(nome,funcao,pais,status,senha,experiencia);
    }
}
