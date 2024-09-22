package objetos;

import java.util.ArrayList;
import java.util.List;

public class NomesLista {
    private List<Nomes> nomes;

    public NomesLista() {
        this.nomes = new ArrayList<>();
    }

    public NomesLista(List<Nomes> nomes) {
        this.nomes = nomes;
    }

    public void addNomes(Nomes nomes) {
       this.nomes.add(nomes);
    }

    public List<Nomes> getNomes() {
        return nomes;
    }

    public void setNomes(List<Nomes> nomes) {
        this.nomes = nomes;
    }
}
