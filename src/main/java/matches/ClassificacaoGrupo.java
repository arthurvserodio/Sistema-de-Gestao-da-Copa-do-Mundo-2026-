package matches;

import nationsAndPlayers.nations.Selecoes;

public class ClassificacaoGrupo {

    private Selecoes selecao;
    private int pontos;
    private int golsPro;
    private int golsContra;

    public ClassificacaoGrupo(Selecoes selecao) {
        this.selecao = selecao;
        this.pontos = 0;
        this.golsPro = 0;
        this.golsContra = 0;
    }

    public Selecoes getSelecao() {
        return selecao;
    }

    public int getPontos() {
        return pontos;
    }

    public int getGolsPro() {
        return golsPro;
    }

    public int getGolsContra() {
        return golsContra;
    }

    public int getSaldoGols() {
        return golsPro - golsContra;
    }

    public void adicionarVitoria() {
        pontos += 3;
    }

    public void adicionarEmpate() {
        pontos += 1;
    }

    public void adicionarGolsPro(int gols) {
        golsPro += gols;
    }

    public void adicionarGolsContra(int gols) {
        golsContra += gols;
    }
}
