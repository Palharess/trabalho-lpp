package objetos.Methods;

import objetos.Nomes;
import objetos.NomesLista;

public class MethodHeader {
    private Nomes nome;
    private NomesLista parameters;

    public MethodHeader(Nomes nome) {
        this.nome = nome;
        this.parameters = new NomesLista();
    }

    public MethodHeader(Nomes nome, NomesLista parameters) {
        this.nome = nome;
        this.parameters = parameters;
    }
}
