package objetos.Ifs;

import geradores.Gerador;
import objetos.Nomes;

import static geradores.Gerador.gerarArg;
import static compilador.BoolCompiler.addLinha;

public class ReturnIfStmt extends  IfStmt {
    private Nomes nome;

    public ReturnIfStmt(Nomes nome) {
        this.nome = nome;
    }


    public void append_result() {
        String newLine;
//        newLine = "load " + this.nome.getNome() + "\nret";
        gerarArg(this.nome.getNome());
        addLinha("ret");
        Gerador.linhas += 1;
    }

}
