package objetos.Ifs;

import objetos.Nomes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static main.Main.addLinha;

public class PrototypeIfStmt extends IfStmt {
    private Nomes nome;
    private Nomes valor;

    public PrototypeIfStmt(Nomes prototype, Nomes valor) {
        this.nome = prototype;
        this.valor = valor;
    }

    public void append_result() {
        String newLine = "load " + this.nome.getNome() + "\nload " + valor.getNome() + "\nset _.prototype";

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
