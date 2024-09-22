package objetos.Classes;

import objetos.AttrsDef;
import objetos.Methods.MethodsDef;
import objetos.Nomes;

public class ClassDef {
    private Nomes name;
    private AttrsDef attrsDef; // opcional
    private MethodsDef methodsDef; // opcional

    public ClassDef(Nomes name, AttrsDef attrsDef, MethodsDef methodsDef) {
        this.name = name;
        this.attrsDef = attrsDef;
        this.methodsDef = methodsDef;
    }

    public ClassDef(Nomes name, AttrsDef attrsDef) {
        this.name = name;
        this.attrsDef = attrsDef;
    }

    public ClassDef(Nomes name, MethodsDef methodsDef) {
        this.name = name;
        this.methodsDef = methodsDef;
    }

    public ClassDef(Nomes name) {
        this.name = name;
    }

    public Nomes getName() {
        return name;
    }

    public void setName(Nomes name) {
        this.name = name;
    }

    public AttrsDef getAttrsDef() {
        return attrsDef;
    }

    public void setAttrsDef(AttrsDef attrsDef) {
        this.attrsDef = attrsDef;
    }

    public MethodsDef getMethodsDef() {
        return methodsDef;
    }

    public void setMethodsDef(MethodsDef methodsDef) {
        this.methodsDef = methodsDef;
    }
}
