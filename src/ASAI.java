import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ASAI implements Parser{
    PrintWriter pw = new PrintWriter(System.out, true);
    
    // Producciones
    private Produccion Q_ = new Produccion(NoTerminal.Q_, new ArrayList<Object>(Arrays.asList(NoTerminal.Q)));
    private Produccion Q = new Produccion(NoTerminal.Q, new ArrayList<Object>(Arrays.asList(TipoToken.SELECT, NoTerminal.D, TipoToken.FROM, NoTerminal.T)));
    private Produccion D_1 = new Produccion(NoTerminal.D, new ArrayList<Object>(Arrays.asList(TipoToken.DISTINCT, NoTerminal.P)));
    private Produccion D_2 = new Produccion(NoTerminal.D, new ArrayList<Object>(Arrays.asList(NoTerminal.P)));
    private Produccion P_1 = new Produccion(NoTerminal.P, new ArrayList<Object>(Arrays.asList(TipoToken.ASTERISCO)));
    private Produccion P_2 = new Produccion(NoTerminal.P, new ArrayList<Object>(Arrays.asList(NoTerminal.A)));
    
    private Produccion A_1 = new Produccion(NoTerminal.A, new ArrayList<Object>(Arrays.asList(NoTerminal.A, TipoToken.COMA, NoTerminal.A1)));
    private Produccion A_2 = new Produccion(NoTerminal.A, new ArrayList<Object>(Arrays.asList(NoTerminal.A1)));
    private Produccion A1 = new Produccion(NoTerminal.A1, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR, NoTerminal.A2)));
    private Produccion A2_1 = new Produccion(NoTerminal.A2, new ArrayList<Object>(Arrays.asList(TipoToken.PUNTO, TipoToken.IDENTIFICADOR)));
    private Produccion A2_2 = new Produccion(NoTerminal.A2, new ArrayList<Object>(Arrays.asList()));
    
    private Produccion T_1 = new Produccion(NoTerminal.T1, new ArrayList<Object>(Arrays.asList(NoTerminal.T, TipoToken.COMA, NoTerminal.T1)));
    private Produccion T_2 = new Produccion(NoTerminal.T1, new ArrayList<Object>(Arrays.asList(NoTerminal.T1)));
    private Produccion T1 = new Produccion(NoTerminal.T1, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR, NoTerminal.T2)));
    private Produccion T2_1 = new Produccion(NoTerminal.T2, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR)));
    private Produccion T2_2 = new Produccion(NoTerminal.T2, new ArrayList<Object>(Arrays.asList()));
    private final ArrayList<Produccion> ListaProducciones = new ArrayList<>(Arrays.asList(Q_, Q, D_1, D_2, P_1, P_2, A_1, A_2, A1, A2_1, A2_2, T_1, T_2, T1, T2_1, T2_2));
    /*
    // Tabla, filas son no terminales, columnas son tokens
    private final ArrayList<ArrayList<Object>> tabla = new ArrayList<>();
    ArrayList<NoTerminal> filas = new ArrayList<>(Arrays.asList(NoTerminal.Q,NoTerminal.D,NoTerminal.P,NoTerminal.A,NoTerminal.A1,NoTerminal.A2,NoTerminal.A3,NoTerminal.T,NoTerminal.T1,NoTerminal.T2,NoTerminal.T3));
    ArrayList<TipoToken> columnas = new ArrayList<>(Arrays.asList(TipoToken.SELECT,TipoToken.FROM,TipoToken.DISTINCT,TipoToken.ASTERISCO,TipoToken.PUNTO,TipoToken.COMA,TipoToken.IDENTIFICADOR, TipoToken.EOF));
    */

    private int i = 0;
    private final List<Token> tokens;
    private final Stack<Integer> pila = new Stack<>();
    private final Stack<Object> simbolos = new Stack<>();

    // Producciones
    /*
    private Produccion E_ = new Produccion(NoTerminal.E_, new ArrayList<Object>(Arrays.asList(NoTerminal.E)));
    private Produccion E_1 = new Produccion(NoTerminal.E, new ArrayList<Object>(Arrays.asList(NoTerminal.E, TipoToken.SUMA, NoTerminal.T)));
    private Produccion E_2 = new Produccion(NoTerminal.E, new ArrayList<Object>(Arrays.asList(NoTerminal.T)));
    private Produccion T_1 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.T, TipoToken.ASTERISCO, NoTerminal.F)));
    private Produccion T_2 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.F)));
    private Produccion F_1 = new Produccion(NoTerminal.F, new ArrayList<Object>(Arrays.asList(TipoToken.PAREN_IZQ, NoTerminal.E, TipoToken.PAREN_DER)));
    private Produccion F_2 = new Produccion(NoTerminal.F, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR)));
    private final ArrayList<Produccion> ListaProducciones = new ArrayList<>(Arrays.asList(E_, E_1, E_2, T_1, T_2, F_1, F_2));
    */
    // Tabla acción
    private final ArrayList<ArrayList<ArrayList<Object>>> tablaAccion = new ArrayList<>();
    private final ArrayList<Integer> filas = new ArrayList<>();
    private final ArrayList<TipoToken> columnas = new ArrayList<>(Arrays.asList(TipoToken.IDENTIFICADOR, TipoToken.SUMA, TipoToken.ASTERISCO, TipoToken.PAREN_IZQ, TipoToken.PAREN_DER, TipoToken.EOF));
    // Tabla Ir_A
    private final ArrayList<ArrayList<Integer>> tablaIrA = new ArrayList<>();
    private final ArrayList<NoTerminal> colIrA = new ArrayList<>(Arrays.asList(NoTerminal.E, NoTerminal.T, NoTerminal.F));

    public ASAI(List<Token> tokens){
        this.tokens = tokens;

        for(int j = 0; j<ListaProducciones.size(); j++){
            System.out.println(j+". "+ListaProducciones.get(j));
        }
        // Creación de la tabla
        for(int k=0; k<=11; k++){
            tablaAccion.add(new ArrayList<ArrayList<Object>>(Arrays.asList(null,null,null,null,null,null)));
            tablaIrA.add(new ArrayList<Integer>(Arrays.asList(null,null,null)));
            filas.add(k);
        }

        tablaAccion.get(0).set(0, new ArrayList<>(Arrays.asList('s', 5)));
        tablaAccion.get(0).set(3, new ArrayList<>(Arrays.asList('s', 4)));

        tablaAccion.get(1).set(1, new ArrayList<>(Arrays.asList('s', 6)));
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

        tablaAccion.get(11).set(1, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(11).set(2, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(11).set(4, new ArrayList<>(Arrays.asList('r', 5)));
        tablaAccion.get(11).set(5, new ArrayList<>(Arrays.asList('r', 5)));

        tablaIrA.get(0).set(0,1);
        tablaIrA.get(0).set(1,2);
        tablaIrA.get(0).set(2,3);

        tablaIrA.get(4).set(0,8);
        tablaIrA.get(4).set(1,2);
        tablaIrA.get(4).set(2,3);

        tablaIrA.get(6).set(1,9);
        tablaIrA.get(6).set(2,3);

        tablaIrA.get(7).set(2,10);

    }

    @Override
    public boolean parse() {
        pila.push(0);
        if(true){
            return true;
        }
        for(int j=0; j<tablaAccion.size();j++){
            System.out.println(tablaAccion.get(j));
        }

        while(i < tokens.size()){
            int colu = columnas.indexOf(tokens.get(i).tipo);
            int fila = filas.indexOf(pila.peek());
            /*
            System.out.print("\n\033[94m  TABLA ACCIÓN\033[0m");
            System.out.print("\nCelda: ["+pila.peek()+ ", "+TipoToken.imprimir(tokens.get(i).tipo)+"] = "+tablaAccion.get(fila).get(colu));
            System.out.print("\nPila: "+pila);
            System.out.print("\nSímbolos: "+impSimbolos()+"\n");
            */
            // Si la celda está vacía -> Error
            if(tablaAccion.get(fila).get(colu) == null){
                System.out.println("\033[91mSe encontraron errores\033[0m");
                return false;
            } else {
                ArrayList<Object> celda = tablaAccion.get(fila).get(colu);
                if(celda.get(0) == "acc"){
                    System.out.println("\033[94mAnálisis Sintáctico correcto\033[0m");
                    return true;
                }
                else if((char)celda.get(0) == 's'){
                    simbolos.push(tokens.get(i).tipo);
                    pila.push((Integer)celda.get(1));
                    i++;
                } else {
                    // Buscar la producción a reducir
                    Produccion PaR = ListaProducciones.get((Integer)celda.get(1));
                    System.out.print("\nProducción: "+PaR);

                    // Sacar la producción de símbolos y añadir la cabecera de la producción
                    for(int j = 0; j < PaR.ladoDerecho.size(); j++){
                        simbolos.pop();
                        pila.pop();
                    }
                    simbolos.push(PaR.ladoIzquierdo);

                    // Buscar el Ir A correspondiente
                    Integer estadoIrA = tablaIrA.get(pila.peek()).get(colIrA.indexOf(PaR.ladoIzquierdo));
                    //System.out.print("\nIr A: "+estadoIrA);
                    if(estadoIrA == null){
                        System.out.println("\033[91mSe encontraron errores\033[0m");
                        return false;
                    }
                    pila.push(estadoIrA);
                    //System.out.print("\nPila: "+pila+"\n");
                }
            }
        }

        return true;
    }
    /*
    private String impSimbolos(){
        String str = "";
        for (Object o : simbolos) {
            if(o instanceof NoTerminal){
                str += NoTerminal.imprimir((NoTerminal)o);
            }
            if(o instanceof TipoToken){
                str += TipoToken.imprimir((TipoToken)o);
            }
        }
        return str;
    }*/
}
