import java.util.ArrayList;

public class Produccion {
    NoTerminal ladoIzquierdo;
    ArrayList<Object> ladoDerecho;

    public Produccion(NoTerminal li, ArrayList<Object> ld){
        ladoIzquierdo = li;
        ladoDerecho = ld;
    }

    @Override
    public String toString(){
        String str = NoTerminal.imprimir(ladoIzquierdo);
        str += " \u2192 ";
        for (Object o : ladoDerecho) {
            if(o instanceof NoTerminal){
                str += NoTerminal.imprimir((NoTerminal)o);
            }
            if(o instanceof TipoToken){
                str += "\033[96m"+TipoToken.imprimir((TipoToken)o)+"\033[0m";
            }
            str += " ";
        }
        if(ladoDerecho.isEmpty()){
            str += "\033[31m\u03BB\033[0m";
        }
        return str;
    }
}
