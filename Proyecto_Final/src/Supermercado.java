import java.lang.Thread;
import java.util.LinkedList;
import java.util.Random;
import java.lang.InterruptedException;

public class Supermercado{

    private static Producto[] almacen;
    private static Caja[] cajas;
    private Gerente gerente;
    private static Fecha fecha;
    private int numCajasRapidas;
    private Random rd = new Random();

    private static LinkedList<Cliente> unifila;

    public Supermercado(int numCajasRapidas, double probaMasDeVeinte, Fecha fecha){
        this.numCajasRapidas = numCajasRapidas;
        this.fecha = fecha;
        cajas = new Caja[15];
    }

    /**
     * Método fábrica para crear cajas.
     */
    public void creaCajas(){
        for(int i = 0; i < 15 - numCajasRapidas; i++){
            cajas[i] = new Caja(new LinkedList<Cliente>());
        }
        for(int i = 15 - numCajasRapidas; i < 15; i++){
            cajas[i] = new Caja(unifila);
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
            Simulador.sop("Producto inexistente.");
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
            throw new YaSeAcaboJovenException(prod);
        }else if(prod.getCantidad() < cantidadProducto){
            prod.setCantidad(0);
        }else{
            prod.setCantidad(prod.getCantidad()-cantidadProducto);
        }
        return new Producto(prod);
    }

    /**
     * Forma al cliente en la caja más vacía.
     * Si el cliente tiene 20 artículos o menos, s.p.g. lo forma en la última
     * caja.
     * Si no, lo forma en la caja no rápida con la fila más corta.
     * @param cliente a formar.
     */
    public void formaEnCaja(Cliente cliente){
        if(cliente.getNumeroArticulos() <= 20){
            unifila.add(cliente);
        }else{
            Caja caja = cajaMasVacia();
            caja.forma(cliente);
        }
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

        public String cancela(int id, int cantidad, String nombre, double precio, double total) {
            return String.format("Cancelacion\n%d\t%d\t%s\t%f\t%f", id, cantidad, nombre, precio, total);
        }   
    }

    public void ejecuta(int tiempo) {
        creaCajas();
        while (!Simulador.getBandera()) {
            int numeroPersonas = rd.nextInt(300)+1;
            for (int i = 0; i < numeroPersonas; i++) {
                double proba = rd.nextDouble();
                Cliente c = new Cliente(this, proba);
                formaEnCaja(c);
                if (i == 30) {
                    despierta();
                }
            }            
        }
    }

    public void despierta() {
        for (int i = 0; i < cajas.length; i++) {
            cajas[i].start();
        }
    }
}
