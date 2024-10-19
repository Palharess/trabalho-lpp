package objetos.args;

import geradores.Gerador;
import objetos.Numeros;

import static main.Main.addLinha;

public class NumberArg extends Arg{
    private Numeros number;

    public NumberArg(Numeros number) {
        this.number = number;
        append_result();
    }


    public Numeros getNumber() {
        return number;
    }


    public void append_result(){
        String newLine = "const " + this.number.getNumero();
        Gerador.linhas += 1;
        addLinha(newLine);
    }
}
