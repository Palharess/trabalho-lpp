package interpretador;

import java.util.*;

public class ExecutionContext {
    private Map<String, Object> variaveis;
    private Object retorno;
    private boolean retornar;

    public ExecutionContext() {
        variaveis = new HashMap<>();
        retorno = null;
        retornar = false;
    }

    public ExecutionContext(Map<String, Object> variaveisGlobais) {
        this();
        variaveis.putAll(variaveisGlobais);
    }

    public void setVariavel(String nome, Object valor) {
        variaveis.put(nome, valor);
    }

    public Object getVariavel(String nome) {
        return variaveis.get(nome);
    }

    public boolean possuiVariavel(String nome) {
        return variaveis.containsKey(nome);
    }

    public void setRetorno(Object valor) {
            this.retorno = valor;
    }

    public Object getRetorno() {
        return retorno;
    }
    public void setRetornar(boolean retornar) {
        this.retornar = retornar;
    }
    public boolean deveRetornar() {
        return retornar;
    }

    public Map<String, Object> getVariaveis() {
        return variaveis;
    }
}
