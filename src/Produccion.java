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
        String str = ladoIzquierdo.name();
        str += " "+(char)26+" ";
        for (Object o : ladoDerecho) {
            if(o instanceof NoTerminal){
                str += NoTerminal.imprimir((NoTerminal)o);
            }
            if(o instanceof TipoToken){
                str += TipoToken.imprimir((TipoToken)o);
            }
        }
        return str;
    }
}
