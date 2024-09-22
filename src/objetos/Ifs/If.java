package objetos.Ifs;

import enums.Cmp;
import objetos.Nomes;

import java.util.List;

public class If {
    private Nomes nomeEsquerda, nomeDireita;
    private Cmp cmp;
    private List<IfStmt> thenStmts;
    private List<IfStmt> elseStmts; //opcional

    public If(Nomes nomeEsquerda, Cmp cmp, Nomes nomeDireita, List<IfStmt> thenStmts) {
        this.nomeEsquerda = nomeEsquerda;
        this.nomeDireita = nomeDireita;
        this.cmp = cmp;
        this.thenStmts = thenStmts;
    }
    public If(Nomes nomeEsquerda, Cmp cmp, Nomes nomeDireita, List<IfStmt> thenStmts, List<IfStmt> elseStmts) {
        this.nomeEsquerda = nomeEsquerda;
        this.cmp = cmp;
        this.nomeDireita = nomeDireita;
        this.thenStmts = thenStmts;
        this.elseStmts = elseStmts;
    }

}
