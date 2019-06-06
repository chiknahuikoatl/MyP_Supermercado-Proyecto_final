import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Simulador {

    public static boolean programaTerminado = false;
    private static Fecha fecha = new Fecha();

    public static boolean getBandera() {
        return programaTerminado;
    }

    //public void

    public static Fecha getFecha(){
        return fecha;
    }

    public void muestraMenu() {
        sop("Bienvenido, seleccione una opcion por favor");
        sop("1) Labor administrativa.");
        sop("2) Ejecutar simulacion por omisión.");
        sop("3) ejecutar simulacion con parámetros distintos.");
    }

    public static void main(String[] args) throws InterruptedException {
        /* Scanner sc = new Scanner(System.in);
        Simulador s = new Simulador();
        s.muestraMenu();
        int opcion;
        opcion = sc.nextInt();
        while (opcion <= 0 || opcion > 3) {
            sop("Opcion incorrecta");
            s.muestraMenu();
            opcion = sc.nextInt();
        }

        switch(opcion) {
            case 1:
                s.opcionUno();
                break;
            case 2:
                s.opcionDos();
                break;
            default:
                break;
        }
    }

    public void opcionUno(){
        s.imprimeMenuUno();
        int op = 0;
        while(op < 1 || op > 3){
            try{
                op = sc.nextInt();
            }catch(InputMismatchException e){
                sop("Número no válido. Vuelve a introducirlo.");
                sop("");
                s.imprimeMenuUno();
            }
        }
        swtich(op){
            case 1:
                sop("Uno");
                break;
            case 2:
                sop("Dos");
                break;
            default:
                sop("Omisión
                break;
        }

         */
        //Detencion de cobro de caja
        Simulador s = new Simulador();
        opcionDos();

    }

    public void imprimeMenuUno(){
        sop("Opciones de Labor Administrativa:");
        sop("-----------------------------------");
        sop("1) Mostrar productos agotados.");
        sop("2) Ver mejores cajas.");
        sop("3) Ver lista de tickets.");
    }

    public static void opcionDos() throws InterruptedException {
        sop("Simulación por con datos por omisión.");
        sop("Cajas rápidas: "+6+"; Probabilidad de llevar más de 20 artículos: "+0.5);
        Supermercado superMercado = new Supermercado(6, 0.5, fecha);
        superMercado.ejecuta(10);
    }

    public static void sop(String s){
        System.out.println(s);
    }
}
