import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ASAI implements Parser{
    /*
    private int i = 0;
    private final List<Token> tokens;
    private final Stack<Object> pila = new Stack<>();
    */

    // Producciones
    /*
    private ArrayList<Object> Q = new ArrayList<>(Arrays.asList(TipoToken.SELECT, NoTerminal.D, TipoToken.FROM, NoTerminal.T));
    private ArrayList<Object> D_1 = new ArrayList<>(Arrays.asList(TipoToken.DISTINCT, NoTerminal.P));
    private ArrayList<Object> D_2 = new ArrayList<>(Arrays.asList(NoTerminal.P));
    private ArrayList<Object> P_1 = new ArrayList<>(Arrays.asList(TipoToken.ASTERISCO));
    private ArrayList<Object> P_2 = new ArrayList<>(Arrays.asList(NoTerminal.A));
    
    private ArrayList<Object> A = new ArrayList<>(Arrays.asList(NoTerminal.A2, NoTerminal.A1));
    private ArrayList<Object> A1_1 = new ArrayList<>(Arrays.asList(TipoToken.COMA, NoTerminal.A));
    private ArrayList<Object> A1_2 = new ArrayList<>();
    private ArrayList<Object> A2 = new ArrayList<>(Arrays.asList(TipoToken.IDENTIFICADOR, NoTerminal.A3));
    private ArrayList<Object> A3_1 = new ArrayList<>(Arrays.asList(TipoToken.PUNTO, TipoToken.IDENTIFICADOR));
    private ArrayList<Object> A3_2 = new ArrayList<>();
    
    private ArrayList<Object> T = new ArrayList<>(Arrays.asList(NoTerminal.T2, NoTerminal.T1));
    private ArrayList<Object> T1_1 = new ArrayList<>(Arrays.asList(TipoToken.COMA, NoTerminal.T));
    private ArrayList<Object> T1_2 = new ArrayList<>();
    private ArrayList<Object> T2 = new ArrayList<>(Arrays.asList(TipoToken.IDENTIFICADOR, NoTerminal.T3));
    private ArrayList<Object> T3_1 = new ArrayList<>(Arrays.asList(TipoToken.IDENTIFICADOR));
    private ArrayList<Object> T3_2 = new ArrayList<>();

    // Tabla, filas son no terminales, columnas son tokens
    private final ArrayList<ArrayList<Object>> tabla = new ArrayList<>();
    ArrayList<NoTerminal> filas = new ArrayList<>(Arrays.asList(NoTerminal.Q,NoTerminal.D,NoTerminal.P,NoTerminal.A,NoTerminal.A1,NoTerminal.A2,NoTerminal.A3,NoTerminal.T,NoTerminal.T1,NoTerminal.T2,NoTerminal.T3));
    ArrayList<TipoToken> columnas = new ArrayList<>(Arrays.asList(TipoToken.SELECT,TipoToken.FROM,TipoToken.DISTINCT,TipoToken.ASTERISCO,TipoToken.PUNTO,TipoToken.COMA,TipoToken.IDENTIFICADOR, TipoToken.EOF));
    */

    private int i = 0;
    private final List<Token> tokens;
    private final Stack<Integer> pila = new Stack<>();

    // Producciones
    private Produccion E_ = new Produccion(NoTerminal.E_, new ArrayList<Object>(Arrays.asList(NoTerminal.E)));
    private Produccion E_1 = new Produccion(NoTerminal.E, new ArrayList<Object>(Arrays.asList(NoTerminal.E, TipoToken.SUMA, NoTerminal.T)));
    private Produccion E_2 = new Produccion(NoTerminal.E, new ArrayList<Object>(Arrays.asList(NoTerminal.T)));
    private Produccion T_1 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.T, TipoToken.ASTERISCO, NoTerminal.F)));
    private Produccion T_2 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.F)));
    private Produccion F_1 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(TipoToken.PAREN_IZQ, NoTerminal.E, TipoToken.PAREN_DER)));
    private Produccion F_2 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR)));

    // Estados
    private final ArrayList<Produccion> I0 = new ArrayList<>(Arrays.asList(E_, E_1, E_2, T_1, T_2, F_1, F_2));
    private final ArrayList<Produccion> I1 = new ArrayList<>(Arrays.asList(E_, E_1));
    private final ArrayList<Produccion> I2 = new ArrayList<>(Arrays.asList(E_2, T_1));
    private final ArrayList<Produccion> I3 = new ArrayList<>(Arrays.asList(T_2));
    private final ArrayList<Produccion> I4 = new ArrayList<>(Arrays.asList(F_1, E_1, E_2, T_1, T_2, F_1, F_2));
    private final ArrayList<Produccion> I5 = new ArrayList<>(Arrays.asList(F_2));
    private final ArrayList<Produccion> I6 = new ArrayList<>(Arrays.asList(E_1, T_1, T_2, F_1, F_2));
    private final ArrayList<Produccion> I7 = new ArrayList<>(Arrays.asList(T_1, F_1, F_2));
    private final ArrayList<Produccion> I8 = new ArrayList<>(Arrays.asList(E_1, F_1));
    private final ArrayList<Produccion> I9 = new ArrayList<>(Arrays.asList(E_1, T_1));
    private final ArrayList<Produccion> I10 = new ArrayList<>(Arrays.asList(T_1));
    private final ArrayList<Produccion> I11 = new ArrayList<>(Arrays.asList(F_1));

    private final ArrayList<ArrayList<ArrayList<Object>>> tablaAccion = new ArrayList<>();
    private final ArrayList<Integer> filas = new ArrayList<>();
    private final ArrayList<TipoToken> columnas = new ArrayList<>(Arrays.asList(TipoToken.IDENTIFICADOR, TipoToken.SUMA, TipoToken.ASTERISCO, TipoToken.PAREN_IZQ, TipoToken.PAREN_DER, TipoToken.EOF));

    private final ArrayList<ArrayList<Integer>> tablaIrA = new ArrayList<>();

    public ASAI(List<Token> tokens){
        this.tokens = tokens;

        // Creación de la tabla
        for(int k=1; k<=11; k++){
            tablaAccion.add(new ArrayList<ArrayList<Object>>(Arrays.asList(null,null,null,null,null,null)));
            tablaIrA.add(new ArrayList<Integer>(Arrays.asList(null,null,null)));
        }

        tablaAccion.get(0).set(0, new ArrayList<>(Arrays.asList('s', 5)));
        tablaAccion.get(0).set(3, new ArrayList<>(Arrays.asList('s', 4)));

        tablaAccion.get(1).set(2, new ArrayList<>(Arrays.asList('s', 6)));
        tablaAccion.get(1).set(5, new ArrayList<>(Arrays.asList("acc")));

        tablaAccion.get(2).set(1, new ArrayList<>(Arrays.asList('r', 2)));
        tablaAccion.get(2).set(2, new ArrayList<>(Arrays.asList('s', 7)));
        tablaAccion.get(2).set(4, new ArrayList<>(Arrays.asList('r', 2)));
        tablaAccion.get(2).set(5, new ArrayList<>(Arrays.asList('r', 2)));

        tablaAccion.get(3).set(1, new ArrayList<>(Arrays.asList('r', 4)));
        tablaAccion.get(3).set(2, new ArrayList<>(Arrays.asList('r', 4)));
        tablaAccion.get(3).set(4, new ArrayList<>(Arrays.asList('r', 4)));
        tablaAccion.get(3).set(5, new ArrayList<>(Arrays.asList('r', 4)));

        tablaAccion.get(4).set(0, new ArrayList<>(Arrays.asList('s', 5)));
        tablaAccion.get(4).set(3, new ArrayList<>(Arrays.asList('s', 4)));

        tablaAccion.get(5).set(1, new ArrayList<>(Arrays.asList('r', 6)));
        tablaAccion.get(5).set(2, new ArrayList<>(Arrays.asList('r', 6)));
        tablaAccion.get(5).set(4, new ArrayList<>(Arrays.asList('r', 6)));
        tablaAccion.get(5).set(5, new ArrayList<>(Arrays.asList('r', 6)));

        tablaAccion.get(6).set(0, new ArrayList<>(Arrays.asList('s', 5)));
        tablaAccion.get(6).set(3, new ArrayList<>(Arrays.asList('s', 4)));

        tablaAccion.get(7).set(0, new ArrayList<>(Arrays.asList('s', 5)));
        tablaAccion.get(7).set(3, new ArrayList<>(Arrays.asList('s', 4)));

        tablaAccion.get(8).set(1, new ArrayList<>(Arrays.asList('s', 6)));
        tablaAccion.get(8).set(4, new ArrayList<>(Arrays.asList('s', 11)));

        tablaAccion.get(9).set(1, new ArrayList<>(Arrays.asList('r', 1)));
        tablaAccion.get(9).set(2, new ArrayList<>(Arrays.asList('s', 7)));
        tablaAccion.get(9).set(4, new ArrayList<>(Arrays.asList('r', 1)));
        tablaAccion.get(9).set(5, new ArrayList<>(Arrays.asList('r', 1)));

        tablaAccion.get(10).set(1, new ArrayList<>(Arrays.asList('r', 3)));
        tablaAccion.get(10).set(2, new ArrayList<>(Arrays.asList('r', 3)));
        tablaAccion.get(10).set(4, new ArrayList<>(Arrays.asList('r', 3)));
        tablaAccion.get(10).set(5, new ArrayList<>(Arrays.asList('r', 3)));

        tablaAccion.get(10).set(1, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(10).set(2, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(10).set(4, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(10).set(5, new ArrayList<>(Arrays.asList('r', 5)));

        tablaIrA.get(0).set(0,1);
        tablaIrA.get(0).set(1,2);
        tablaIrA.get(0).set(2,3);

        tablaIrA.get(4).set(0,8);
        tablaIrA.get(4).set(1,2);
        tablaIrA.get(4).set(2,3);

        tablaIrA.get(6).set(1,9);
        tablaIrA.get(6).set(2,3);

        tablaIrA.get(7).set(2,10);

        for(int j = 0; j <= 11; j++){
            filas.add(j);
        }

    }

    @Override
    public boolean parse() {
        //pila.push(TipoToken.EOF);
        //pila.push(NoTerminal.Q);

        pila.push(0);

        while(i < tokens.size()){
            int colu = columnas.indexOf(tokens.get(i));
            int fila = filas.indexOf(pila.peek());
            if(tablaAccion.get(fila).get(colu) == null){
                System.out.println("\033[91mSe encontraron errores\033[0m");
                return false;
            }
            i++;
        }

        return true;
    }

}
