package objetos.args;

import objetos.Nomes;

public class NameArg extends Arg{
    private Nomes name, nomes2; //name.name ou name

    public NameArg(Nomes name) {
        this.name = name;
    }

    public NameArg(Nomes name, Nomes nomes2) {
        this.name = name;
        this.nomes2 = nomes2;
    }

}
