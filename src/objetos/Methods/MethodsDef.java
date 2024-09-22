package objetos.Methods;

import objetos.Methods.MethodDef;

import java.util.ArrayList;
import java.util.List;

public class MethodsDef {
    private List<MethodDef> methodsDefs;

    public MethodsDef() {
        this.methodsDefs = new ArrayList<>();
    }

    public MethodsDef(List<MethodDef> methodsDefs) {
        this.methodsDefs = methodsDefs;
    }
}
