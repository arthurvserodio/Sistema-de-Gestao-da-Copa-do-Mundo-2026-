package matches;

public class EstatisticaTime {
    private int gols;
    private int chutes;
    private int chutesAGol;
    private float posseDeBola;
    private int passes;
    private float precisaoDosPasses;
    private int faltas;
    private int cartoesAmarelos;
    private int cartoesVermelhos;
    private int impedimentos;
    private int escanteios;

    public EstatisticaTime(int chutes, int chutesAGol, float posseDeBola, int passes, float precisaoDosPasses, int faltas, int cartoesAmarelos, int cartoesVermelhos, int impedimentos, int escanteios) {
        this.chutes = chutes;
        this.chutesAGol = chutesAGol;
        this.posseDeBola = posseDeBola;
        this.passes = passes;
        this.precisaoDosPasses = precisaoDosPasses;
        this.faltas = faltas;
        this.cartoesAmarelos = cartoesAmarelos;
        this.cartoesVermelhos = cartoesVermelhos;
        this.impedimentos = impedimentos;
        this.escanteios = escanteios;
    }
    public int getChutes() {
        return chutes;
    }
    public void setChutes(int chutes) {
        this.chutes = chutes;
    }

    public int getChutesAGol() {
        return chutesAGol;
    }
    public void setChutesAGol(int chutesAGol) {
        this.chutesAGol = chutesAGol;
    }

    public float getPosseDeBola() {
        return posseDeBola;
    }
    public void setPosseDeBola(float posseDeBola) {
        this.posseDeBola = posseDeBola;
    }

    public int getPasses() {
        return passes;
    }
    public void setPasses(int passes) {
        this.passes = passes;
    }

    public float getPrecisaoDosPasses() {
        return precisaoDosPasses;
    }
    public void setPrecisaoDosPasses(float precisaoDosPasses) {
        this.precisaoDosPasses = precisaoDosPasses;
    }

    public int getFaltas() {
        return faltas;
    }
    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getCartoesAmarelos() {
        return cartoesAmarelos;
    }
    public void setCartoesAmarelos(int cartoesAmarelos) {
        this.cartoesAmarelos = cartoesAmarelos;
    }

    public int getCartoesVermelhos() {
        return cartoesVermelhos;
    }
    public void setCartoesVermelhos(int cartoesVermelhos) {
        this.cartoesVermelhos = cartoesVermelhos;
    }

    public int getImpedimentos() {
        return impedimentos;
    }
    public void setImpedimentos(int impedimentos) {
        this.impedimentos = impedimentos;
    }

    public int getEscanteios() {
        return escanteios;
    }
    public void setEscanteios(int escanteios) {
        this.escanteios = escanteios;
    }
}
