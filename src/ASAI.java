import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ASAI implements Parser{
    PrintWriter pw = new PrintWriter(System.out, true);

    private int i = 0;
    private final List<Token> tokens;
    private final Stack<Integer> pila = new Stack<>();
    private final Stack<Object> simbolos = new Stack<>();

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
    
    private Produccion T_1 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.T, TipoToken.COMA, NoTerminal.T1)));
    private Produccion T_2 = new Produccion(NoTerminal.T, new ArrayList<Object>(Arrays.asList(NoTerminal.T1)));
    private Produccion T1 = new Produccion(NoTerminal.T1, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR, NoTerminal.T2)));
    private Produccion T2_1 = new Produccion(NoTerminal.T2, new ArrayList<Object>(Arrays.asList(TipoToken.IDENTIFICADOR)));
    private Produccion T2_2 = new Produccion(NoTerminal.T2, new ArrayList<Object>(Arrays.asList()));
    private final ArrayList<Produccion> ListaProducciones = new ArrayList<>(Arrays.asList(Q_, Q, D_1, D_2, P_1, P_2, A_1, A_2, A1, A2_1, A2_2, T_1, T_2, T1, T2_1, T2_2));

    // Tabla acción
    private final ArrayList<ArrayList<ArrayList<Object>>> tablaAccion = new ArrayList<>();
    private final ArrayList<Integer> filas = new ArrayList<>();
    private final ArrayList<TipoToken> columnas = new ArrayList<>(Arrays.asList(TipoToken.SELECT, TipoToken.FROM, TipoToken.DISTINCT, TipoToken.ASTERISCO, TipoToken.PUNTO, TipoToken.COMA, TipoToken.IDENTIFICADOR, TipoToken.EOF));
    // Tabla Ir_A
    private final ArrayList<ArrayList<Integer>> tablaIrA = new ArrayList<>();
    private final ArrayList<NoTerminal> colIrA = new ArrayList<>(Arrays.asList(NoTerminal.Q, NoTerminal.D, NoTerminal.P, NoTerminal.A, NoTerminal.A1, NoTerminal.A2, NoTerminal.T, NoTerminal.T1, NoTerminal.T2));

    public ASAI(List<Token> tokens){
        this.tokens = tokens;

        // Creación de la tabla
        for(int k=0; k<=23; k++){
            tablaAccion.add(new ArrayList<ArrayList<Object>>(Arrays.asList(null,null,null,null, null,null,null,null)));
            tablaIrA.add(new ArrayList<Integer>(Arrays.asList(null,null,null, null,null,null, null,null,null)));
            filas.add(k);
        }

        // Crear tabla de acciones
        addAccion(0, TipoToken.SELECT, 's', 2);
        addAccion(1, TipoToken.EOF, "acc");
        addAccion(2, TipoToken.DISTINCT, 's', 5);
        addAccion(2, TipoToken.ASTERISCO, 's', 8);
        addAccion(2, TipoToken.IDENTIFICADOR, 's', 11);
        addAccion(3, TipoToken.FROM, 's', 4);
        addAccion(4, TipoToken.IDENTIFICADOR, 's', 19);
        addAccion(5, TipoToken.ASTERISCO, 's', 8);
        addAccion(5, TipoToken.IDENTIFICADOR, 's', 11);
        addAccion(6, TipoToken.FROM, 'r', 2);
        addAccion(7, TipoToken.FROM, 'r', 3);
        addAccion(8, TipoToken.FROM, 'r', 4);
        addAccion(9, TipoToken.FROM, 'r', 5);
        addAccion(9, TipoToken.COMA, 's', 15);
        addAccion(10, TipoToken.FROM, 'r', 7);
        addAccion(10, TipoToken.COMA, 'r', 7);
        addAccion(11, TipoToken.COMA, 'r', 10);
        addAccion(11, TipoToken.FROM, 'r', 10);
        addAccion(11, TipoToken.PUNTO, 's', 14);
        addAccion(12, TipoToken.EOF, 'r', 1);
        addAccion(12, TipoToken.COMA, 's', 18);
        addAccion(13, TipoToken.FROM, 'r', 8);
        addAccion(13, TipoToken.COMA, 'r', 8);
        addAccion(14, TipoToken.IDENTIFICADOR, 's', 16);
        addAccion(15, TipoToken.IDENTIFICADOR, 's', 11);
        addAccion(16, TipoToken.FROM, 'r', 9);
        addAccion(16, TipoToken.COMA, 'r', 9);
        addAccion(17, TipoToken.FROM, 'r', 6);
        addAccion(17, TipoToken.COMA, 'r', 6);
        addAccion(18, TipoToken.IDENTIFICADOR, 's', 19);
        addAccion(19, TipoToken.COMA, 'r', 15);
        addAccion(19, TipoToken.EOF, 'r', 15);
        addAccion(19, TipoToken.IDENTIFICADOR, 's', 23);
        addAccion(20, TipoToken.COMA, 'r', 11);
        addAccion(20, TipoToken.EOF, 'r', 11);
        addAccion(21, TipoToken.COMA, 'r', 12);
        addAccion(21, TipoToken.EOF, 'r', 12);
        addAccion(22, TipoToken.COMA, 'r', 13);
        addAccion(22, TipoToken.EOF, 'r', 13);
        addAccion(23, TipoToken.COMA, 'r', 14);
        addAccion(23, TipoToken.EOF, 'r', 14);
        
        // Crear tabla Ir_A
        addIrA(0, NoTerminal.Q, 1);
        addIrA(2, NoTerminal.D, 3);
        addIrA(2, NoTerminal.P, 7);
        addIrA(2, NoTerminal.A, 9);
        addIrA(2, NoTerminal.A1, 10);
        addIrA(4, NoTerminal.T, 12);
        addIrA(4, NoTerminal.T1, 21);
        addIrA(5, NoTerminal.A, 9);
        addIrA(5, NoTerminal.A1, 10);
        addIrA(5, NoTerminal.P, 6);
        addIrA(11, NoTerminal.A2, 13);
        addIrA(15, NoTerminal.A1, 17);
        addIrA(18, NoTerminal.T1, 20);
        addIrA(19, NoTerminal.T2, 22);
    }

    @Override
    public boolean parse() {
        pila.push(0);

        /*for(int j=0; j<tablaAccion.size();j++){
            System.out.println(tablaAccion.get(j));
        }*/

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
                imprimirError(fila, i);
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
                    //System.out.print("\nProducción: "+PaR);

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

    private void addAccion(int fila, TipoToken tt, char accion, int estado){
        int col = columnas.indexOf(tt);
        tablaAccion.get(fila).set(col, new ArrayList<Object>(Arrays.asList(accion, estado)));
    }
    private void addAccion(int fila, TipoToken tt, String acc){
        int col = columnas.indexOf(tt);
        tablaAccion.get(fila).set(col, new ArrayList<>(Arrays.asList(acc)));
    }
    private void addIrA(int fila, NoTerminal nt, int estado){
        int col = colIrA.indexOf(nt);
        tablaIrA.get(fila).set(col, estado);
    }

    private void imprimirError(int estado, int hasta){
        String str = "  Se esperaba ";
        ArrayList<TipoToken> esperados = new ArrayList<>();
        int k;
        
        for(k=0; k<tablaAccion.get(estado).size(); k++){
            if(tablaAccion.get(estado).get(k) != null && columnas.get(k) != TipoToken.EOF){
                esperados.add(columnas.get(k));
            }
        }
        
        for(k=0; k<esperados.size(); k++){
            str += "\'" + TipoToken.imprimir(esperados.get(k)) + "\'";
            if((k>0 && k != esperados.size()-1)||(k==0 && esperados.size()>1)){
                str += " o ";
            }
        }

        System.out.print("\033[91m"+str+"\033[0m");
        if(hasta == 0){
            System.out.print("\n");
            return;
        }
        System.out.println("\033[91m después de\033[0m");
        str = "";
        String s;
        for(k=0; k<i; k++){
            if(tokens.get(k).tipo != TipoToken.IDENTIFICADOR){
                s = TipoToken.imprimir(tokens.get(k).tipo);
            } else {
                s = tokens.get(k).lexema;
            }
            str += s+" ";
        }
        System.out.println("  \033[91m\'"+str+"\'\033[0m");
    }

}
