package objetos.Ifs;

import objetos.Nomes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.addLinha;

public class ReturnIfStmt extends  IfStmt {
    private Nomes nome;

    public ReturnIfStmt(Nomes nome) {
        this.nome = nome;
    }


    public void append_result() {
        String newLine = "return " + this.nome.getNome() + ";";
        newLine = "load " + this.nome.getNome() + "\nret";
        addLinha(newLine);
    }

}
