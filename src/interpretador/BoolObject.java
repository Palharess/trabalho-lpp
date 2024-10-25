package interpretador;

import java.util.*;

public class BoolObject {
    private BoolClasse classe;
    Map<String, Object> atributos;
    private BoolObject prototype;
    private String markColor; // Para o coletor de lixo

    public BoolObject(BoolClasse classe) {
        this.classe = classe;
        this.atributos = new HashMap<>();
        this.prototype = null;
        this.markColor = "gray"; // Objetos novos são marcados como cinza
    }

    public BoolClasse getClasse() {
        return classe;
    }

    public Object getAtributo(String nome) {
        if (atributos.containsKey(nome)) {
            return atributos.get(nome);
        } else if (prototype != null) {
            return prototype.getAtributo(nome);
        } else {
            return null;
        }
    }

    public void setAtributo(String nome, Object valor) {
        if (classe.possuiAtributo(nome) || atributos.containsKey(nome)) {
            atributos.put(nome, valor);
        } else if (prototype != null) {
            prototype.setAtributo(nome, valor);
        } else {
            atributos.put(nome, valor); // Se não existe, adiciona ao objeto atual
        }
    }

    public BoolObject getPrototype() {
        return prototype;
    }

    public void setPrototype(BoolObject prototype) {
        this.prototype = prototype;
    }

    public String getMarkColor() {
        return markColor;
    }

    public void setMarkColor(String markColor) {
        this.markColor = markColor;
    }

    public Map<String, Object> getAtributos() {
        return atributos;
    }
}
