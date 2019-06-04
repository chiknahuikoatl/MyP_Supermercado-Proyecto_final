import java.lang.Thread;
import java.lang.InterruptedException;

public class Supermercado{

    private class Gerente {
        public Gerente() {

        }

        public String cancela(int id, int cantidad, String nombre, double precio, double total) {
            return String.format("Cancelacion\n%d\t%d\t%s\t%f\t%f", id, cantidad, nombre, precio, total);
        }
    }

    private static Producto[] almacen;
    private static Caja[] cajas;
    private Gerente gerente;

    public Supermercado(){}

    /*
     * Método que devuelve la cantidad de elementos de un producto que hay
     * disponibles en el almacén.
     * @param producto, el id del producto a buscar.
     * @return cantidad disponible del producto dado.
     */
    public int enExistencia(int producto){
        try{
            return almacen[producto].getCantidad();
        }catch(IndexOutOfBoundsException e){
            sop("Producto inexistente.");
            return -1;
        }
    }

    /*
     * Quita del almacén el número dado de elementos dados del producto dado.
     * Si la cantidad dada es mayor al número de elementos en existencia,
     * entonces el almacén se deja en cero.
     * @param producto id del producto a retirar del almacén.
     * @param cantidadProducto el número de elementos a retirar del almacén.
     * @return prod, el producto con la cantidad actualizada.
     * @throws YaSeAcaboJovenException si ya no hay elementos de un producto.
     */
    public synchronized Producto retiraAlmacen(int producto, int cantidadProducto)
            throws YaSeAcaboJovenException{
        Producto prod = almacen[producto];
        if(prod.getCantidad() == 0){
            throw new YaSeAcaboJovenException();
        }else if(prod.getCantidad() < cantidadProducto){
            prod.setCantidad(0);
        }else{
            prod.setCantidad(prod.getCantidad()-cantidadProducto);
        }
        return new Producto(prod);
    }

    /**
     * Método encargado de la cancelacion de un producto
     * @param id
     * @param cantidad
     * @param nombre
     * @param precio
     * @param total
     * @return
     */
    public synchronized String cancela(int id, int cantidad, String nombre, double precio, double total) {
        return gerente.cancela(id, cantidad, nombre, precio, total);
    }

    // Getters y setters
    public Producto[] getAlmacen(){
        return almacen;
    }

    public Caja[] getCajas(){
        return cajas;
    }

    public static void sop(String s){
        System.out.println(s);
    }
}