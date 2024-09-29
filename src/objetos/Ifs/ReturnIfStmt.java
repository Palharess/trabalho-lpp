package objetos.Ifs;

import objetos.Nomes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReturnIfStmt extends  IfStmt {
    private Nomes nome;

    public ReturnIfStmt(Nomes nome) {
        this.nome = nome;
    }


    public void append_result() {
        String newLine = "return " + this.nome.getNome() + ";";
        try (FileWriter fw = new FileWriter("src/resultado.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(newLine);
            bw.newLine();
            System.out.println("Linha adicionada com sucesso!");

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

}
