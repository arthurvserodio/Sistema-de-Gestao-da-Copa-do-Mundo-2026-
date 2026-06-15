package builder;

import matches.EstatisticaTime;

public class EstatisticaTimeBuilder {
    private int id;
    private int gols;
    private int chutes;
    private int chutesAGol;
    private int posseDeBola;
    private int passes;
    private int precisaoDosPasses;
    private int faltas;
    private int cartoesAmarelos;
    private int cartoesVermelhos;
    private int impedimentos;
    private int escanteios;

    public EstatisticaTimeBuilder id(int id) {
        this.id= id;
        return this;
    }

    public EstatisticaTimeBuilder gols(int gols) {
        this.gols = gols;
        return this;
    }

    public EstatisticaTimeBuilder chutes(int chutes) {
        this.chutes = chutes;
        return this;
    }

    public EstatisticaTimeBuilder chutesAGol(int chutesAGol) {
        this.chutesAGol = chutesAGol;
        return this;
    }

    public EstatisticaTimeBuilder posseDeBola(int posseDeBola) {
        this.posseDeBola = posseDeBola;
        return this;
    }

    public EstatisticaTimeBuilder passes(int passes) {
        this.passes = passes;
        return this;
    }

    public EstatisticaTimeBuilder precisaoDosPasses(int precisaoDosPasses) {
        this.precisaoDosPasses = precisaoDosPasses;
        return this;
    }

    public EstatisticaTimeBuilder faltas(int faltas) {
        this.faltas = faltas;
        return this;
    }

    public EstatisticaTimeBuilder cartoesAmarelos(int cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
        return this;
    }

    public EstatisticaTimeBuilder cartoesVermelhos(int cartoesVermelhos) {
        this.cartoesVermelhos = cartoesVermelhos;
        return this;
    }

    public EstatisticaTimeBuilder impedimentos(int impedimentos) {
        this.impedimentos = impedimentos;
        return this;
    }

    public EstatisticaTimeBuilder escanteios(int escanteios) {
        this.escanteios = escanteios;
        return this;
    }

    public EstatisticaTime build() {
        return new EstatisticaTime(id,gols, chutes, chutesAGol, posseDeBola, passes, precisaoDosPasses, faltas, cartoesAmarelos, cartoesVermelhos, impedimentos, escanteios);
    }
}
