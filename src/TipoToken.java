public enum TipoToken {
    IDENTIFICADOR,

    // Palabras reservadas
    SELECT, FROM, DISTINCT,

    // Caracteres
    COMA, PUNTO, ASTERISCO,

    // Final de cadena
    EOF,

    SUMA, PAREN_IZQ, PAREN_DER;

    public static String imprimir(TipoToken tt){
        String str = "";
        switch (tt) {
            case IDENTIFICADOR:
                str = "id";
                break;
            case SELECT:
                str = "select";
                break;
            case FROM:
                str = "from";
                break;
            case DISTINCT:
                str = "distinct";
                break;
            case COMA:
                str = ",";
                break;
            case PUNTO:
                str = ".";
                break;
            case ASTERISCO:
                str = "*";
                break;
            case EOF:
                str = "$";
                break;
            case SUMA:
                str = "+";
                break;
            case PAREN_IZQ:
                str = "(";
                break;
            case PAREN_DER:
                str = ")";
                break;
            default:
                str = tt.name();
                break;
        }
        return str;
    }
}
