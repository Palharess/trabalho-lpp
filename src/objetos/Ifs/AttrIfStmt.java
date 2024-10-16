package objetos.Ifs;

import objetos.Attr;
import objetos.Lhs;
import objetos.Nomes;
import objetos.args.MethodCallArg;

import static main.Main.addLinha;

public class AttrIfStmt extends IfStmt {
    private Attr attr;
    int tipo; // 1 = com objeto, 2 = com operacao, 3 = return

    public AttrIfStmt(Attr attr) {
        this.attr = attr;
    }

    public AttrIfStmt(Nomes nome, Nomes atributo, Nomes valor) {
        super();
    }

    public AttrIfStmt(Attr attr, Lhs lhs) {
        super();
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public void append_result() {
        if(this.tipo == 1){
            Attr attr = this.attr;



        }

    }
}
