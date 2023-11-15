import java.util.ArrayList;

public class Estado {
    ArrayList<Produccion> Prod;
    NoTerminal llegada;

    public Estado(Object ll, ArrayList<Produccion> p){
        Prod = p;
    }
}
