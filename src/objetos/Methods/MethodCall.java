package objetos.Methods;

import objetos.Nomes;
import objetos.NomesLista;

public class MethodCall {
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

}
