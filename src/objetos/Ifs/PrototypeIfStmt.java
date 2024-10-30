package objetos.Ifs;

import geradores.Gerador;
import objetos.Nomes;

import static compilador.BoolCompiler.addLinha;

public class PrototypeIfStmt extends IfStmt {
    private Nomes nome;
    private Nomes valor;

    public PrototypeIfStmt(Nomes prototype, Nomes valor) {
        this.nome = prototype;
        this.valor = valor;
    }

    public void append_result() {
        String newLine = "load " + this.nome.getNome() + "\nload " + valor.getNome() + "\nset _.prototype";
        Gerador.linhas += 3;

        addLinha(newLine);
    }

    public Nomes getNome() {
        return nome;
    }

    public void setNome(Nomes nome) {
        this.nome = nome;
    }

    public Nomes getValor() {
        return valor;
    }

    public void setValor(Nomes valor) {
        this.valor = valor;
    }
}
