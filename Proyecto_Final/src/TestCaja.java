import java.util.LinkedList;

public class TestCaja {
    private static Supermercado sm = new Supermercado(2, 22, new Fecha(1,1,1,1,1));
    public static void main(String[] args) {
        Caja c;
        Cliente cliente1 = new Cliente(sm, 0.99);
        Cliente cliente2 = new Cliente(sm, 0.99);
        Cliente cliente3 = new Cliente(sm, 0.99);
        Cliente cliente4 = new Cliente(sm, 0.99);
        Cliente cliente5 = new Cliente(sm, 0.99);
        LinkedList<Cliente> fila = new LinkedList<>();
        cliente1.compra();
        fila.add(cliente1);
        cliente2.compra();
        fila.add(cliente2);
        cliente3.compra();
        fila.add(cliente3);
        cliente4.compra();
        fila.add(cliente4);
        cliente5.compra();
        fila.add(cliente5);
        c = new Caja(fila, sm);
        //c.cobra();
        //c.cierreDeCaja();
    }
}