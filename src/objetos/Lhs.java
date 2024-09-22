package objetos;

public class Lhs {
    private Nomes name;
    private Nomes nomePonto; // opcional

    public Lhs(Nomes name) {
        this.name = name;
    }

    public Lhs(Nomes name, Nomes nomePonto) {
        this.name = name;
        this.nomePonto = nomePonto;
    }
}