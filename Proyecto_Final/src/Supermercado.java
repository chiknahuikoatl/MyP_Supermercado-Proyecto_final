import java.lang.Thread;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Supermercado{

    public int numeroCliente = 1;
    private static Producto[] almacen;
    private static Caja[] cajas;
    private Gerente gerente;
    private static Fecha fecha;
    private int numCajasRapidas;
    private Random rd = new Random();
    private Proveedor proveedor = new Proveedor();
    private boolean abierto;
    private double probaMasDeVeinte;
    public LinkedList<String> tickets = new LinkedList<>();

    private static LinkedList<Cliente> unifila = new LinkedList<>();

    public Supermercado(int numCajasRapidas, double probaMasDeVeinte, Fecha fecha){
        this.numCajasRapidas = numCajasRapidas;
        this.probaMasDeVeinte = probaMasDeVeinte;
        this.fecha = fecha;
        cajas = new Caja[15];
        llenaAlmacen();
        abierto = true;
    }

    public Supermercado() {}

    private void llenaAlmacen() {
        LinkedList<Producto> inventario = proveedor.getInventario();
        almacen = new Producto[inventario.size()];
        int i = 0;
        for (Producto p : inventario) {
            almacen[i] = p;
            i++;
        }
    }

    /**
     * Método fábrica para crear cajas.
     */
    public void creaCajas(){
        for(int i = 0; i < 15 - numCajasRapidas; i++){
            cajas[i] = new Caja(new LinkedList<Cliente>(), this);
        }
        for(int i = 15 - numCajasRapidas; i < 15; i++){
            cajas[i] = new Caja(unifila, this);
        }
    }

    /**
     * Método que devuelve la cantidad de artículos de un producto que hay
     * disponibles en el almacén.
     * @param producto, el id del producto a buscar.
     * @return cantidad disponible del producto dado.
     */
    public int enExistencia(int producto){
        try{
            return almacen[producto].getCantidad();
        }catch(IndexOutOfBoundsException e){
            return -1;
        }
    }

    /**
     * Quita del almacén el número dado de artículos dados del producto dado.
     * Si la cantidad dada es mayor al número de artículos en existencia,
     * entonces el almacén se deja en cero.
     * @param producto id del producto a retirar del almacén.
     * @param cantidadProducto el número de artículos a retirar del almacén.
     * @return prod el producto con la cantidad actualizada.
     * @throws YaSeAcaboJovenException si ya no hay artículos de un producto.
     */
    public synchronized Producto retiraAlmacen(int producto, int cantidadProducto)
            throws YaSeAcaboJovenException{
        Producto prod = almacen[producto];
        if(prod.getCantidad() == 0){
            almacen[producto] = proveedor.llenaProducto(producto);
            //Simulador.sop("retiro del almacen");
            //throw new YaSeAcaboJovenException(prod);
        }else if(prod.getCantidad() < cantidadProducto){
            prod.setCantidad(0);
        }else{
            prod.setCantidad(prod.getCantidad()-cantidadProducto);
        }
        return new Producto(prod);
    }

    public static void meteAlamcen(int producto, int cantidadProducto){
        Producto p = almacen[producto];
        p.setCantidad(p.getCantidad()+cantidadProducto);
    }

    /**
     * Forma al cliente en la caja más vacía.
     * Si el cliente tiene 20 artículos o menos, s.p.g. lo forma en la última
     * caja.
     * Si no, lo forma en la caja no rápida con la fila más corta.
     * @param cliente a formar.
     */
    public void formaEnCaja(Cliente cliente){
        Simulador.sop(""+numeroCliente);
        cliente.id = numeroCliente;
        if(cliente.getNumeroArticulos() <= 20){
            unifila.add(cliente);
        }else{
            Caja caja = cajaMasVacia();
            caja.forma(cliente);
        }

        numeroCliente ++;
    }

    /**
     * Devuelve la caja más vacía.
     * Busca en todas las cajas la que tenga la fila más corta.
     * Si la longitoud de la fila de una caja es cero, la devuelve. De otro
     * modo, sigue buscando.
     * @return caja con la fila más corta.
     */
    public synchronized Caja cajaMasVacia(){
        int indice = 0;
        for(int i = 0; i < 14 - numCajasRapidas; i++){
            if(cajas[i].getLongitud() == 0) return cajas[i];
            if(cajas[indice].getLongitud() > cajas[i].getLongitud()){
                indice = i;
            }
        }
        return cajas[indice];
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

    /**
     * Devuelve una bandera que indica si el supermercado está abierto-
     * @return abierto si el super sigue abierto.
     */
    public boolean estaAbierto(){
        return this.abierto;
    }

    // Getters y setters
    public Producto[] getAlmacen(){
        return almacen;
    }

    public Caja[] getCajas(){
        return cajas;
    }

    private class Gerente {
        public Gerente() {

        }

        public String cancela(int id, int cantidad, String nombre,
                              double precio, double total) {
            Supermercado.meteAlamcen(id,cantidad);
            return String.format("Cancelacion\n%d\t%d\t%s\t%f\t%f", id,
                cantidad, nombre, precio, total);
        }
    }

    public void ejecuta(int tiempo) throws InterruptedException {
        creaCajas();
        //int numeroPersonas = rd.nextInt(300)+100;
        int numeroPersonas = 130;
        while(abierto){
            despierta();
            int timpo = (int)(numeroPersonas*0.005)+1;
            for (int i = 0; i < numeroPersonas; i++) {
                Cliente c = new Cliente(this, probaMasDeVeinte);
                formaEnCaja(c);
                //Thread.sleep(timpo);
            }
            Thread.sleep(tiempo);
            abierto = false;
            cierreDeCaja();
            for (int i = 0; i < cajas.length; i++) {
                cajas[i].join();
            }
        }
    }


    // Thread.sleep(5);
    // Simulador.opcionDos();
    // abierto = false;

    public void despierta() throws InterruptedException {
        for (int i = 0; i < cajas.length; i++) {
            cajas[i].start();
        }
    }

    public void cierreDeCaja() {
        Fecha fecha = Simulador.getFecha();
        String nombre = String.format("Ventas_de_%s.txt", fecha.toString());
        File file = new File(nombre);
        FileOutputStream fos;
        int totalCompras = totalVentas();
        try {
            fos = new FileOutputStream(file);
            PrintStream writer = new PrintStream(fos);
            for (String s : tickets) {
                writer.println(s);
            }
            //writer.println(this.ticketsDia);
            writer.println("total de compras:" + totalCompras);
            writer.close();
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.out.println("error al intentar crear el archivo: " + nombre);
        }
    }

    public int totalVentas() {
        int total = 0;
        for (int i = 0; i < cajas.length; i++) {
            total += cajas[i].totalCompras;
        }
        return total;
    }
}
