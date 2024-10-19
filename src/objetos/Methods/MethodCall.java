package objetos.Methods;

import geradores.Gerador;
import interfaces.CallMethod;
import objetos.Nomes;
import objetos.NomesLista;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static main.Main.addLinha;

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
        String newLine = "";
        if(this.parameters.getNomes().isEmpty()){
            newLine = "load " + this.nomeObjetos.getNome() + "\ncall " + this.nomeMethod.getNome();
            Gerador.linhas += 2;
        } else {
            for(Nomes nome : this.parameters.getNomes()){
                newLine += "load " + nome.getNome() + "\n";
                Gerador.linhas += 1;
            }
            newLine += "load " + this.nomeObjetos.getNome() + "\ncall " + this.nomeMethod.getNome();
            Gerador.linhas += 2;
        }

        addLinha(newLine);
    }

    public Nomes getNomeObjetos() {
        return nomeObjetos;
    }

    public Nomes getNomeMethod() {
        return nomeMethod;
    }

    public NomesLista getParameters() {
        return parameters;
    }
}
