import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Proveedor {

    private LinkedList<Producto> inventario = new LinkedList<>();

    public Proveedor() {
        cargaInventario("../../inventario.txt");
    }

    public Proveedor(String direccion) {
        cargaInventario(direccion);
    }

    private void cargaInventario(String direccion) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(direccion));
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                String[] palabras = linea.split("\t");
                double precio = Double.parseDouble(palabras[2]);
                int cantidad = Integer.parseInt(palabras[1]);
                this.inventario.add(new Producto(i, palabras[0], precio, cantidad));
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al intentar cargar el archivo");
            System.out.println("Se cargara el archivo por defecto del programa");
            cargaInventario("../../inventario.txt");
        } catch (IOException e) {
            System.out.println("Error al intentar cargar el archivo");
            System.out.println("Se cargara el archivo por defecto del programa");
            cargaInventario("../../inventario.txt");
        }
    }

    private LinkedList<Producto> getInventario() {
        return this.inventario;
    }

    private void imprime() {
        System.out.println(this.inventario.toString());
    }

    public static void main(String[] args) {
        Proveedor p = new Proveedor("fallo.txt");
        p.imprime();
    }
}