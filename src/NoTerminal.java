public enum NoTerminal {
    E_, E, T, F;

    public static String imprimir(NoTerminal nt){
        String str = "";
        switch (nt) {
            case E_:
                str = "E'";
                break;
                default:
                str = nt.name();
                break;
        }
        return str;
    }
}
