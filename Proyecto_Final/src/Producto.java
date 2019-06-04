public class Producto{

    private final int id;
    private final String nombre;
    private final double precio;
    private int cantidad;

    /*
     * Constructor de la clase.
     * @param id el identificador del producto;
     * @param nombre del producto.
     * @param precio del producto.
     * @param cantidad del producto.
     */
    public Producto(int id, String nombre, double precio, int cantidad){
        this.id       = id;
        this.nombre   = nombre;
        this.precio   = precio;
        this.cantidad = cantidad;
    }

    /*
     * Constructor de clase que construye un producto con las mismas
     * características que el producto dado.
     * @param producto prototipo a crear.
     */
    public Producto(Producto producto){
        this.id       = producto.getID();
        this.nombre   = producto.getNombre();
        this.precio   = producto.getPrecio();
        this.cantidad = producto.getCantidad();
    }

    /*
     * Constructor de clase que construye un producto a partir de otro pero
     * con cantidad distinta.
     * @param producto prototipo a crear.
     * @param cantidad nueva a asignar.
     */
    public Producto(Producto producto, int cantidad){
        this.id       = producto.getID();
        this.nombre   = producto.getNombre();
        this.precio   = producto.getPrecio();
        this.cantidad = cantidad;
    }

    public String toString(){
        String s = String.format("%06d, %s, %.2f, %d",
                         id, nombre, precio, cantidad);
        return s;
    }

    // Getters y setters
    public int getID(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public double getPrecio(){
        return this.precio;
    }

    public int getCantidad(){
        return this.cantidad;
    }

    public void setCantidad(int cant){
        this.cantidad = cant;
    }

}
