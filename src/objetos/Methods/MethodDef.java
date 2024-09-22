package objetos.Methods;

import objetos.VarsDef;

public class MethodDef {
    private MethodHeader methodHeader;
    private VarsDef varsDef; // opcional
    private MethodBody methodBody;


    public MethodDef(MethodHeader methodHeader, VarsDef varsDef, MethodBody methodBody) {
        this.methodHeader = methodHeader;
        this.varsDef = varsDef;
        this.methodBody = methodBody;
    }

    public MethodDef(MethodHeader methodHeader, MethodBody methodBody) {
        this.methodHeader = methodHeader;
        this.methodBody = methodBody;
    }
}
