import java.util.LinkedList;
import java.util.Random;

import org.omg.IOP.ProfileIdHelper;

public class Cliente{
    private static Supermercado miSuper;
    private LinkedList<Producto> carrito;
    private int numeroArticulos;
    private double probaMasDeVeinte; //La proba de tener más de veinte artículos
    private Random random;
    public int id;

    public Cliente(Supermercado miSuper, double probaMasDeVeinte){
        this.miSuper = miSuper;
        this.probaMasDeVeinte = probaMasDeVeinte;
        random = new Random();
        carrito = new LinkedList<>();
        compra();
    }

    /**
     * Simula la interacción del cliente en un supermercado.
     * Llena el carrito con llenaCarrito() y busca y se forma en la fila más
     * vacía.
    */
    public void compra(){
        llenaCarrito();
    }

    /**
     * Llena el carrito de un número aleatorio de productos.
     * La probabilidad de llevar entre 21 y 200 artículos se toma de la variable
     * probaMasDeVeinte instanciada en el constructor.
     * @return el carrito con los productos comprados.
    */
    public LinkedList<Producto> llenaCarrito(){
        int productosAComprar;
        if(random.nextDouble() < probaMasDeVeinte){
            productosAComprar = random.nextInt(181) + 20;
        }else{
            productosAComprar = random.nextInt(21);
        }
        numeroArticulos = productosAComprar;
        while(productosAComprar != 0){
            if (productosAComprar < 0) {
                break;
            }
            int cantProd = random.nextInt(productosAComprar) + 1;
            Simulador.sop(String.valueOf(cantProd));
            int prod = random.nextInt(miSuper.getAlmacen().length);
            productosAComprar -= meteAlCarrito(prod, cantProd);
            int bandera = meteAlCarrito(prod, cantProd);
            if (bandera == 0) {
                productosAComprar --;
            }
        }
        return this.carrito;
    }

    /**
     * Este método se encarga de meter a la lista del carrito la cantidad de
     * artículos dados del producto indicado.
     * Si la cantidad dada es mayor a la cantidad de artículos de un producto
     * en el almacén, se añadirán al carrito todas las unidades disponibles.
     * @param prod, el id del producto a sustraer del almacén.
     * @param cant, la cantidad de artículos del producto dado a sustraer.
     * @return el número de artículos introducidos.
     */
    public int meteAlCarrito(int prod, int cant){
        int cantProd = miSuper.enExistencia(prod);
        try{
            Producto p = new Producto(miSuper.retiraAlmacen(prod, cantProd),
                cantProd);
            carrito.add(p);
            Simulador.sop("almacen:\n" + miSuper.getAlmacen().toString());
            return p.getCantidad();
        }catch(YaSeAcaboJovenException e){
            return 0;
        }
    }

    // Getters y setters
    public LinkedList<Producto> getCarrito(){
        return this.carrito;
    }

    public int getNumeroArticulos(){
        return this.numeroArticulos;
    }

    public String toString() {
        String s = "";
        for (Producto p : carrito) {
            s += p.toString();
        }
        return s;
    }
}
