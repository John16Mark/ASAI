import java.util.ArrayList;

public class Produccion {
    NoTerminal ladoIzquierdo;
    ArrayList<Object> ladoDerecho;

    public Produccion(NoTerminal li, ArrayList<Object> ld){
        ladoIzquierdo = li;
        ladoDerecho = ld;
    }
}
