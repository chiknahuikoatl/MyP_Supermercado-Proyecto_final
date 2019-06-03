import java.util.LinkedList;

public class Cliente{
    private static Supermercado miSuper;
    private LinkedList<Producto> carrito;

    public Cliente(Supermercado super){
        this.miSuper = super();
    }

    /*
     * Este método se encarga de meter a la lista del carrito la cantidad de
     * elementos dados del producto indicado.
     * Si la cantidad dada es mayor a la cantidad de elementos de un producto
     * en el almacén, se añadirán al carrito todas las unidades disponibles.
     * @param prod, el id del producto a sustraer del almacén.
     * @param cant, la cantidad de elementos del producto dado a sustraer.
     * @return false si no hay ningún elemento del producto dado, true en otro
     * caso.
     */
    public boolean meteAlCarrito(int prod, int cant){
        int cantProd = miSuper.enExistencia(prod);
        try{
            carrito.append(new Producto(miSuper.retiraAlmacen(prod, cantProd),
                cantProd));
            return true;
        }catch(YaSeAcaboJovenException e){
            sop("" + e);
            return false;
        }
    }

    // Getters y setters
    public Producto[] getCarrito(){
        return this.carrito;
    }
}
