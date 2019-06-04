import java.util.Scanner;

public class Simulador {

    private static boolean programaTerminado = false;


    public static boolean getBandera() {
        return programaTerminado;
    }

    public void muestraMenu() {
        System.out.println("Bienvenido, seleccione una opcion por favor");
        System.out.println("1) Labor administrativa");
        System.out.println("2) ejecutar simulacion");
        System.out.println("3) ejecutar simulacion con un numero especifico de cajas");
    }

    public static void main(String[] args) {
        /* Scanner sc = new Scanner(System.in);
        Simulador s = new Simulador();
        s.muestraMenu();
        int opcion;
        opcion = sc.nextInt();
        while (opcion <= 0 || opcion > 3) {
            System.out.println("Opcion incorrecta");
            s.muestraMenu();
            opcion = sc.nextInt();
        }

        switch(opcion) {
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }

         */

        
    }
}