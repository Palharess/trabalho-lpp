package objetos.Ifs;

import objetos.Attr;
import objetos.Lhs;
import objetos.Nomes;

public class AttrIfStmt extends IfStmt {
    private Attr attr;

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
}
