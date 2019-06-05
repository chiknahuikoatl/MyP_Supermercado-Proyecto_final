import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Proveedor {

    private LinkedList<Producto> inventario = new LinkedList<>();
    private String registro = "Productos agotados\n-------------------------------------------";

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

    public LinkedList<Producto> getInventario() {
        return this.inventario;
    }

    public Producto llenaProducto(int producto) {
        Producto p = this.inventario.get(producto);
        registro += String.format("\n%d\t%s\t%2f", p.getID(), p.getNombre(), p.getPrecio());
        return p;
    }

    public String getRegistro() {
        return registro + "\n-------------------------------------------";
    }
}