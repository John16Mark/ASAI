public enum NoTerminal {
    E_, E, F,
    Q_, Q, D, P,
    A, A1, A2,
    T, T1, T2;

    public static String imprimir(NoTerminal nt){
        String str = "";
        switch (nt) {
            case E_:
                str = "E'";
                break;
            case A1:
                str = "A\u2081";
                break;
            case A2:
                str = "A\u2082";
                break;
            default:
                str = nt.name();
                break;
        }
        return str;
    }
}
