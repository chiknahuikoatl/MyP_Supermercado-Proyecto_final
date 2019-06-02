public class YaSeAcaboJovenException extends Exception{
    final static String MENSAJE = "Ya se acabó el producto que busca, joven."+
        "Vuelva mañana.";

    /**
     *
     * @param o
     */
    public NoHayLugarException(Object o){
        super(MENSAJE+o.getClass().getName());
    }
}
