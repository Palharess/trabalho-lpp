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


}
