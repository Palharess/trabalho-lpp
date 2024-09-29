package objetos.Methods;

import interfaces.CallMethod;
import objetos.Nomes;
import objetos.NomesLista;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MethodCall implements CallMethod {
    private Nomes nomeObjetos;
    private Nomes nomeMethod;
    private NomesLista parameters;

    public MethodCall(Nomes nomeObjetos, Nomes nomeMethod) {
        this.nomeObjetos = nomeObjetos;
        this.nomeMethod = nomeMethod;
        this.parameters = new NomesLista();
    }

    public MethodCall(Nomes nomeObjetos, Nomes nomeMethod, NomesLista parameters) {
        this.nomeObjetos = nomeObjetos;
        this.nomeMethod = nomeMethod;
        this.parameters = parameters;
    }

    public void append_result() {
        String newLine;
        if(this.parameters.getNomes().isEmpty()){
            newLine = this.nomeObjetos.getNome() + "." + this.nomeMethod.getNome() + "()" + "\n";
        } else {
            newLine = this.nomeObjetos.getNome() + "." + this.nomeMethod.getNome() + "(";
            for (Nomes nome : this.parameters.getNomes()) {
                newLine += nome.getNome() + ", ";
            }
            newLine = newLine.substring(0, newLine.length() - 2);
            newLine += ");";
        }

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
