package interpretador;

import java.util.*;

public class BoolMethod {
    private String nome;
    private List<String> parametros;
    private Set<String> variaveisLocais;
    private List<String> instrucoes;

    public BoolMethod(String nome) {
        this.nome = nome;
        this.parametros = new ArrayList<>();
        this.variaveisLocais = new HashSet<>();
        this.instrucoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarParametro(String parametro) {
        parametros.add(parametro);
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void adicionarVariavelLocal(String variavel) {
        variaveisLocais.add(variavel);
    }

    public Set<String> getVariaveisLocais() {
        return variaveisLocais;
    }

    public void adicionarInstrucao(String instrucao) {
        instrucoes.add(instrucao);
    }

    public List<String> getInstrucoes() {
        return instrucoes;
    }
}

