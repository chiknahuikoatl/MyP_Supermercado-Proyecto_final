import java.lang.Thread;
import java.lang.InterruptedException;

public class Supermercado{
    private static Producto[] almacen;
    private static Caja[] cajas;

    public Supermercado(){}

    public int enExistencia(int producto){
        try{
            int cantidad = almacen[producto].getCantidad();
        }
    }

    // Getters y setters
    public Producto[] getAlmacen(){
        return almacen;
    }

    public Caja[] getCajas(){
        return cajas;
    }
}
