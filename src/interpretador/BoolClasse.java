package interpretador;

import java.util.*;

public class BoolClasse {
    private String nome;
    private Set<String> atributos;
    private Map<String, BoolMethod> metodos;

    public BoolClasse(String nome) {
        this.nome = nome;
        this.atributos = new HashSet<>();
        this.metodos = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarAtributo(String atributo) {
        atributos.add(atributo);
    }

    public boolean possuiAtributo(String atributo) {
        return atributos.contains(atributo);
    }

    public void adicionarMetodo(BoolMethod metodo) {
        metodos.put(metodo.getNome(), metodo);
    }

    public BoolMethod getMetodo(String nomeMetodo) {
        return metodos.get(nomeMetodo);
    }

    public Set<String> getAtributos() {
        return atributos;
    }

    public Map<String, BoolMethod> getMetodos() {
        return metodos;
    }
}
