package objetos.args;

import objetos.Methods.MethodCall;

public class MethodCallArg extends Arg {
    private MethodCall methodCall;

    public MethodCallArg(MethodCall methodCall) {
        this.methodCall = methodCall;
    }
}
