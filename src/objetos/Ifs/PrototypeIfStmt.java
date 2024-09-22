package objetos.Ifs;

import objetos.Nomes;

public class PrototypeIfStmt extends IfStmt {
    private Nomes prototype;

    public PrototypeIfStmt(Nomes prototype) {
        this.prototype = prototype;
    }
}
