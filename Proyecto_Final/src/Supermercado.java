import java.lang.Thread;
import java.lang.InterruptedException;

public class Supermercado{
    private static Producto[] almacen;
    private static Caja[] cajas;

    public Supermercado(){}

    // Getters y setters
    public Producto[] getAlmacen(){
        return almacen;
    }

    public Caja[] getCajas(){
        return cajas;
    }
}
