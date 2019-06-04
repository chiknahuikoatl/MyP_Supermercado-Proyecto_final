import java.util.Scanner;

public class Simulador {

    Scanner sc = new Scanner(System.in);

    public void muestraMenu() {
        sop("Bienvenido, seleccione una opcion por favor");
        sop("1) Labor administrativa.");
        sop("2) Ejecutar simulacion por omisión.");
        sop("3) ejecutar simulacion con parámetros distintos.");
    }

    public static void main(String[] args) {
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
    }

    public void imprimeMenuUno(){
        sop("Opciones de Labor Administrativa:");
        sop("-----------------------------------");
        sop("1) Mostrar productos agotados.");
        sop("2) Ver mejores cajas.");
        sop("3) Ver lista de tickets.");
    }

    public void opcionDos(){
        sop("Opción dos por implementar.");
    }

    public static void sop(String s){
        System.out.println(s);
    }
}
