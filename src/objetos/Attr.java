package objetos;

import enums.Op;
import objetos.args.Arg;
import objetos.args.ArgBin;

public class Attr {
    private Lhs lhs;
    private Arg arg; // para o primeiro formato
    private ArgBin leftArgBin;
    private Op op;
    private ArgBin rightArgBin;

    public Attr(Lhs lhs, Arg arg) {
        this.lhs = lhs;
        this.arg = arg;
    }

    public Attr(Lhs lhs, ArgBin leftArgBin, Op op, ArgBin rightArgBin) {
        this.lhs = lhs;
        this.leftArgBin = leftArgBin;
        this.op = op;
        this.rightArgBin = rightArgBin;
    }



    public Attr(Nomes nomeEsquerdo, String op, Nomes nomeDireito) {
    }

    public Attr(Arg arg) {
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

    public ArgBin getLeftArgBin() {
        return leftArgBin;
    }

    public void setLeftArgBin(ArgBin leftArgBin) {
        this.leftArgBin = leftArgBin;
    }

    public Op getOp() {
        return op;
    }

    public void setOp(Op op) {
        this.op = op;
    }

    public ArgBin getRightArgBin() {
        return rightArgBin;
    }

    public void setRightArgBin(ArgBin rightArgBin) {
        this.rightArgBin = rightArgBin;
    }
}
