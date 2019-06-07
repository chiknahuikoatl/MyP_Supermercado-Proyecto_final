import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Caja extends Thread {
    private LinkedList<Cliente> fila; // Linked list que será usada cómo cola
    // Lista encargada de almacenar los ticket generados por cada compra para el
    // proceso de persitencia.
    private String ticketsDia = "";
    public int totalCompras; // Contador de compras realizadas en el día
    private int maximo; // Contador correspondiente al máximo número de clientes en la caja.
    private int paraCancelacion;
    private int cliente = 0;
    private int detener;
    private Random rd;
    private Supermercado miSuper;

    public Caja() {
        rd = new Random();
        paraCancelacion = rd.nextInt(100);
        this.totalCompras = 0;
        this.maximo = 0;
        detener = rd.nextInt(100);
    }

    public Caja(LinkedList<Cliente> fila) {
        this.fila = fila;
        rd = new Random();
        paraCancelacion = rd.nextInt(100);
        this.totalCompras = 0;
        this.maximo = 0;
    }

    public Caja(LinkedList<Cliente> fila, Supermercado miSuper){
        this.fila = fila;
        this.miSuper = miSuper;
        rd = new Random();
        paraCancelacion = rd.nextInt(100);
        this.totalCompras = 0;
        this.maximo = 0;
    }

    @Override
    public void run() {
        while (miSuper.estaAbierto() || fila.size() != 0) {
            cobra();
        }
        //miSuper.cierreDeCaja();
    }

    /**
     * Método principal encargado de realizar el cobro por cliente y genera el
     * ticket de compra;
     *
     * @param c Cliente al cual se le realizará la compra.
     */
    public void forma(Cliente c) {
        fila.add(c);
    }

    public void cobra() {
        while(fila.size() != 0){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Cliente c = fila.getFirst();
            fila.removeFirst();
            Simulador.sop("cobra con el cliente " + c.toString());
            int detener = rd.nextInt(100);
            if (detener == this.detener) {
                //Detencion de cobro de caja
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            int id = c.hashCode(); // Id único del ticket.
            int cancela = rd.nextInt(100);
            // Realización del ticket
            double subTotal = 0;
            String ticket = "TICKET DE COMPRA " + id + "\n";
            ticket += "----------------------------------------------\n";
            ticket += "#Producto\tCantidad\tNombre\tPrecio\tTotal\n";
            for (Producto p : c.getCarrito()) {
                double total = p.getCantidad() * p.getPrecio();
                subTotal += total;
                ticket += generaTicket(p.getID(), p.getCantidad(), p.getNombre(), p.getPrecio(), total);
                if (cancela == this.paraCancelacion) {
                    ticket += miSuper.cancela(p.getID(), p.getCantidad(), p.getNombre(), p.getPrecio(), total) + "\n";
                    subTotal -= total;
                }
            }
            double iva = subTotal * 0.08;
            ticket += String.format("Subtotal:%f\nIVA:%f\nTotal:%f", subTotal, iva, (iva + subTotal));
            ticket += "----------------------------------------------\n";
            ticket += "¡GRACIAS POR SU COMPRA, VUELVE PRONTO!\n";
            ticket += "----------------------------------------------\n";
            //ticketsDia = ticketsDia + ticket +"\n";
            miSuper.tickets.add(ticket);
            this.totalCompras++;
            this.cliente++;
        }
    }

    /**
     * Método auxiliar para la generación del ticket que concatena los datos de id
     * del producto, la cantidad comprada, el nombre del producto, el precio
     * unitario del producto y el total por la compra del número del producto.
     *
     * @param id
     * @param cantidad
     * @param nombre
     * @param precio
     * @param total
     * @return
     */
    public String generaTicket(int id, int cantidad, String nombre, double precio, double total) {
        String compra = String.format("%d\t%d\t%s\t%f\t%f\n", id, cantidad, nombre, precio, total);
        return compra;
    }

    // Getters y setters
    public LinkedList<Cliente> getFila() {
        if (maximo < this.fila.size()) {
            maximo = fila.size();
        }
        return this.fila;
    }

    /**
     * Método encargado de regresar el número de clientes formados.
     *
     * @return Numero de clientes formados.
     */
    public int getLongitud() {
        return this.fila.size();
    }

    public int getMaximo() {
        return maximo;
    }
}
