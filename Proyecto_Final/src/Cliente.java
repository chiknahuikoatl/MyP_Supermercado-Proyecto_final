import java.util.LinkedList;
import java.util.random;

public class Cliente{
    private static Supermercado miSuper;
    private LinkedList<Producto> carrito;
    private Random random;

    public Cliente(Supermercado super){
        this.miSuper = super();
        random = new Random();
    }

    

    /*
     * Llena el carrito de un número aleatorio de productos.
     * La probabilidad de llevar menos de 20 productos es de 50%, la misma que
     * llevar entre 21 y 200.
     * @return el carrito con los productos comprados.
    */
    public LinkedList<Producto> llenaCarrito(){
        int productosAComprar;
        if(random.nextBoolean()){
            productosAComprar = random.nextInt(21);
        }else{
            productosAComprar = random.nextInt(181) + 20;
        }
        while(productosAComprar != 0){
            int cantProd = random.nextInt(productosAComprar) + 1;
            int prod = random.nextInt(miSuper.getAlmacen().length);
            ctosAComprar -= meteAlCarrito(prod, cantProd);
        }
        return this.carrito;
    }

    /*
     * Este método se encarga de meter a la lista del carrito la cantidad de
     * elementos dados del producto indicado.
     * Si la cantidad dada es mayor a la cantidad de elementos de un producto
     * en el almacén, se añadirán al carrito todas las unidades disponibles.
     * @param prod, el id del producto a sustraer del almacén.
     * @param cant, la cantidad de elementos del producto dado a sustraer.
     * @return el número de elementos introducidos.
     */
    public boolean meteAlCarrito(int prod, int cant){
        int cantProd = miSuper.enExistencia(prod);
        try{
            Producto p = new Producto(miSuper.retiraAlmacen(prod, cantProd),
                cantProd);
            carrito.add(p);
            return p.getCantidad();
        }catch(YaSeAcaboJovenException e){
            sop("" + e);
            return 0;
        }
    }

    // Getters y setters
    public Producto[] getCarrito(){
        return this.carrito;
    }
}
