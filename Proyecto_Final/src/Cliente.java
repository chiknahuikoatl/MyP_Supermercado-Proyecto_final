import java.util.LinkedList;

public class Cliente{
    private static Supermercado miSuper;
    private LinkedList<Producto> carrito;

    public Cliente(Supermercado super){
        this.miSuper = super();
    }

    public boolean meteAlCarrito(int prod, int cant) throws
            YaSeAcaboJovenException{
        int cantProd = miSuper.enExistencia(prod);
        if(cantProd <= 0){
            throw new YaSeAcaboJovenException();
        }else if(cantProd < cant){
            carrito.append(new Producto(miSuper.retiraAlmacen(prod, cantProd),
                cantProd));
        }else{
            carrito.append(new Producto(miSuper.getAlmacen()[prod], cant));
        }
    }

    // Getters y setters
    public Producto[] getCarrito(){
        return this.carrito;
    }
}
