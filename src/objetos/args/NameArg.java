package objetos.args;

import objetos.Nomes;

public class NameArg extends Arg{
    private Nomes nome, nome2; //name.name ou name

    public NameArg(Nomes name) {
        this.nome = name;
    }

    public NameArg(Nomes name, Nomes nomes2) {
        this.nome = name;
        this.nome2 = nomes2;
    }

    public Nomes getNome() {
        return nome;
    }

    public void setNome(Nomes name) {
        this.nome = name;
    }

    public Nomes getNome2() {
        return nome2;
    }

    public void setNome2(Nomes nomes2) {
        this.nome2 = nomes2;
    }
}
