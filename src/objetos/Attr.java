package objetos;

import enums.Op;
import objetos.args.Arg;
import objetos.args.ArgBin;

import static main.Main.addLinha;

public class Attr {
    private Lhs lhs;
    private Arg arg; // para o primeiro formato
    private Arg leftArgBin;
    private String op;
    private Arg rightArgBin;

    public Attr(Lhs lhs, Arg arg) {
        this.lhs = lhs;
        this.arg = arg;
    }

    public Attr(Lhs lhs, ArgBin leftArgBin, String op, ArgBin rightArgBin) {
        this.lhs = lhs;
        this.leftArgBin = leftArgBin;
        this.op = op;
        this.rightArgBin = rightArgBin;
    }

    public Attr( Arg leftArgBin, String op, Arg rightArgBin) {
        this.leftArgBin = leftArgBin;
        this.op = op;
        this.rightArgBin = rightArgBin;
    }



    public Attr(Nomes nomeEsquerdo, String op, Nomes nomeDireito) {
        this.lhs = new Lhs(nomeEsquerdo);
        this.leftArgBin = new ArgBin(nomeEsquerdo);
        this.op = op;
        this.rightArgBin = new ArgBin(nomeDireito);

    }

    public Attr(Arg arg) {
        this.arg = arg;
    }

    public Lhs getLhs() {
        return lhs;
    }

    public void setLhs(Lhs lhs) {
        this.lhs = lhs;
    }

    public Arg getArg() {
        return arg;
    }

    public void setArg(Arg arg) {
        this.arg = arg;
    }



    public void setLeftArgBin(ArgBin leftArgBin) {
        this.leftArgBin = leftArgBin;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }



    public void setRightArgBin(ArgBin rightArgBin) {
        this.rightArgBin = rightArgBin;
    }

    public void append_result() {
        if(this.op.equals("+")){
            addLinha("add");
        }
        else if(this.op.equals("-")){
            addLinha("sub");
        }
        else if(this.op.equals("*")){
            addLinha("mul");
        }
        else if(this.op.equals("/")){
            addLinha("div");
        }
    }

    public void append_result_store(int tipo) {
        //
        if(tipo == 1){
            addLinha("load " + this.lhs.getName().getNome());
            addLinha("set " + this.lhs.getNomePonto().getNome());

        }
    }
}
