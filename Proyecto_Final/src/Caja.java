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
    private LinkedList<String> tickets;
    private int totalCompras; // Contador de compras realizadas en el día
    private int maximo; // Contador correspondiente al máximo número de clientes en la caja.
    private int paraCancelacion;
    private int cliente = 0;
    private Random rd;
    private Supermercado compartido;

    public Caja() {
        rd                = new Random();
        paraCancelacion   =  rd.nextInt(100);
        this.totalCompras = 0;
        this.maximo       = 0;
    }

    public Caja(LinkedList<Cliente> fila) {
        this.fila = fila;
        rd = new Random();
        paraCancelacion =  rd.nextInt(100);
        this.totalCompras = 0;
        this.maximo = 0;
    }

    /**
     * Método encargado de regresar el número de clientes formados.
     *
     * @return Numero de clientes formados.
     */
    public int getLongitud() {
        return this.fila.size();
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
    
    private void cobra() {
        Cliente c = fila.get(cliente);
        double tiempoDeEspera = c.getCarrito().size() * 0.002; // Tiempo que tardará en realizar la compra
        try {
            TimeUnit.SECONDS.sleep((int)tiempoDeEspera);
        } catch (InterruptedException e) {
            System.out.println("Error al intentar pausar la ejecucion de cobro");
        }
        int id = c.hashCode(); // Id único del ticket.
        int cancela = rd.nextInt(100);
        // Realización del ticket
        double subTotal = 0;
        String ticket = "TICKET DE COMPRA " + id;
        ticket += "----------------------------------------------";
        ticket += "#Producto\tCantidad\tNombre\tPrecio\tTotal";
        for (Producto p : c.getCarrito()) {
            double total = p.getCantidad() * p.getPrecio();
            subTotal += total;
            ticket += generaTicket(p.getID(), p.getCantidad(), p.getNombre(), p.getPrecio(), total);
            if (cancela == this.paraCancelacion) {
                ticket += compartido.cancela(p.getID(), p.getCantidad(), p.getNombre(), p.getPrecio(), total);
                subTotal -= total;
            }
        }
        double iva = subTotal * 0.08;
        ticket += String.format("Subtotal:%f\nIVA:%f\nTotal:%f", subTotal, iva, (iva + subTotal));
        ticket += "----------------------------------------------";
        ticket += "¡GRACIAS POR SU COMPRA, VUELVE PRONTO!";
        ticket += "----------------------------------------------";
        tickets.add(ticket);
    
        this.totalCompras++;
        this.cliente++;
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
           String compra = String.format("%d\t%d\t%s\t%f\t%f", id, cantidad, nombre, precio, total);
        return compra;
    }

    public void cierreDeCaja() {
        Fecha fecha = Simulador.getFecha();
        String nombre = String.format("Ventas de ", fecha.toString());
        File file = new File(nombre);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            PrintStream writer = new PrintStream(fos);
            for (String s : this.tickets) {
                writer.println(s);
            }
            writer.println("total de compras:" + this.totalCompras);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("error al intentar crear el archivo: " + nombre);
        }
    }

    @Override
    public void run() {
        while (!Simulador.getBandera()) {
            cobra();
        }
        cierreDeCaja();
    }

    // Getters y setters
    public LinkedList<Cliente> getFila() {
        if (maximo < this.fila.size()) {
            maximo = fila.size();
        }
        return this.fila;
    }

    public int getMaximo() {
        return maximo;
    }
}
