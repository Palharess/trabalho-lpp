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

//    public void append_result() {
//        String newLine = "if (" + this.nomeEsquerda.getNome() + " " + this.cmp + " " + this.nomeDireita.getNome() + ") {";
//        for (IfStmt stmt : thenStmts) {
//            stmt.append_result();
//        }
//        System.out.println("}");
//        if (elseStmts != null) {
//            System.out.println("else {");
//            for (IfStmt stmt : elseStmts) {
//                stmt.append_result();
//            }
//            System.out.println("}");
//        }
//    }

    public Nomes getNomeEsquerda() {
        return nomeEsquerda;
    }

    public void setNomeEsquerda(Nomes nomeEsquerda) {
        this.nomeEsquerda = nomeEsquerda;
    }

    public Nomes getNomeDireita() {
        return nomeDireita;
    }

    public void setNomeDireita(Nomes nomeDireita) {
        this.nomeDireita = nomeDireita;
    }

    public Cmp getCmp() {
        return cmp;
    }

    public void setCmp(Cmp cmp) {
        this.cmp = cmp;
    }

    public List<IfStmt> getThenStmts() {
        return thenStmts;
    }

    public void setThenStmts(List<IfStmt> thenStmts) {
        this.thenStmts = thenStmts;
    }

    public List<IfStmt> getElseStmts() {
        return elseStmts;
    }

    public void setElseStmts(List<IfStmt> elseStmts) {
        this.elseStmts = elseStmts;
    }
}
