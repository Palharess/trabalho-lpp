package objetos.Ifs;

import objetos.Nomes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PrototypeIfStmt extends IfStmt {
    private Nomes nome;
    private Nomes valor;

    public PrototypeIfStmt(Nomes prototype, Nomes valor) {
        this.nome = prototype;
        this.valor = valor;
    }

    public void append_result() {
        String newLine = this.nome.getNome() + "_.prototype " + "= " + valor.getNome() + "\n";
//        System.out.println(newLine);
//        try {
//            Files.write(Paths.get("./resultado.txt"), newLine.getBytes(), StandardOpenOption.APPEND);
//        } catch ( IOException e) {
//            System.err.println("Error appending to file: " + e.getMessage());
//        }

        try (FileWriter fw = new FileWriter("src/resultado.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(newLine);
            bw.newLine();
            System.out.println("Linha adicionada com sucesso!");

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
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
