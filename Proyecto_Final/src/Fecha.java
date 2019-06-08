import java.time.LocalDateTime;

/*
 * Clase que simula fecha.
 */

public class Fecha{

    // Reloj sincronizado con el sistema.
    private LocalDateTime horarioSistema;
    //Elementos de la fecha.
    private int minuto;
    private int hora;
    private int dia;
    private int mes;
    private int anio;

    public Fecha(int minuto, int hora, int dia, int mes, int anio){
        this.minuto = minuto;
        this.hora   = hora;
        this.dia    = dia;
        this.mes    = mes;
        this.anio   = anio;
    }

    public Fecha(int dia, int mes, int anio){
        this.minuto = 0;
        this.hora   = 0;
        this.dia    = dia;
        this.mes    = mes;
        this.anio   = anio;
    }

    public Fecha(){
        this.horarioSistema = LocalDateTime.now();
        this.minuto = horarioSistema.getMinute();
        this.hora   = horarioSistema.getHour();
        this.dia    = horarioSistema.getDayOfMonth();
        this.mes    = horarioSistema.getMonthValue();
        this.anio   = horarioSistema.getYear();
    }

    public void estableceHorarioSistema(){
        this.minuto = horarioSistema.getMinute();
        this.hora   = horarioSistema.getHour();
        this.dia    = horarioSistema.getDayOfMonth();
        this.mes    = horarioSistema.getMonthValue();
        this.anio   = horarioSistema.getYear();
    }

    /*
     * Haciendo los cálculos necesarios, aumenta en uno el campo dado
     * ajustando los siguientes campos de ser necesario.
     * @param fecha a modificar.
     * @return fecha modificada.
     */
    public Fecha siguienteMinuto(Fecha fecha){
        int min = fecha.getMinuto();
        if(min == 59){
            fecha.setMinuto(0);
            fecha.setHora(fecha.getHora() + 1);
        } else{
            fecha.setMinuto(min + 1);
        }
        return fecha;
    }

    public Fecha siguienteHora(Fecha fecha){
        int h = fecha.getHora(); // Hora de la fecha dada.
        if(h == 23){
            fecha.setHora(0);
            fecha.setDia(fecha.getDia() + 1);
        }else{
            fecha.setHora(h + 1);
        }
        return fecha;
    }

    public Fecha siguienteDia(Fecha fecha){
        switch(fecha.getMes()){
            case 1: case 3: case 5: case 7:
            case 8: case 10:
                if(fecha.getDia() == 31){
                    fecha = siguienteMes(fecha);
                    fecha.setDia(1);
                }else{
                    fecha.setDia(fecha.getDia() + 1);
                }
                return fecha;
            case 4: case 6: case 9: case 11:
                if(fecha.getDia() == 30){
                    fecha = siguienteMes(fecha);
                    fecha.setDia(1);
                }else{
                    fecha.setDia(fecha.getDia() + 1);
                }
                return fecha;
            case 12:
                if(fecha.getDia() == 31){
                    fecha = siguienteMes(fecha);
                    fecha.setDia(1);
                    fecha.setAnio(fecha.getAnio() + 1);
                }else{
                    fecha.setDia(fecha.getDia() + 1);
                }
                return fecha;
            default:
                throw new IllegalArgumentException("Fecha no válida.");
        }
    }

    public Fecha siguienteMes(Fecha fecha){
        if(fecha.getMes() == 12){
            fecha.setMes(1);
        }else{
            fecha.setMes(fecha.getMes() + 1);
        }
        return fecha;
    }

    /*
     * Compara si la fecha actual es antes de la fecha dada.
     * @param fecha a comparar.
    */
    public boolean antes(Fecha fecha){
        return comparaFechas(fecha) < 0;
    }

    /*
     * Revisa si la fecha actual es después de la fecha dada.
     * @param fecha a comparar
    */
    public boolean despues(Fecha fecha){
        return comparaFechas(fecha) > 0;
    }

    public boolean iguales(Fecha fecha){
        return comparaFechas(fecha) == 0;
    }

    private int comparaFechas(Fecha fecha){
        int comparador = 0;
        if(anio < fecha.getAnio()) comparador -= 16;
        if(anio > fecha.getAnio()) comparador += 16;
        if(mes < fecha.getMes()) comparador -= 8;
        if(mes > fecha.getMes()) comparador += 8;
        if(dia < fecha.getDia()) comparador -= 4;
        if(dia > fecha.getDia()) comparador += 4;
        if(hora < fecha.getHora()) comparador -= 2;
        if(hora > fecha.getHora()) comparador += 2;
        if(minuto < fecha.getMinuto()) comparador -= 1;
        if(minuto > fecha.getMinuto()) comparador += 1;
        return comparador;
    }

    /*
     * Representación en cadena de los elementos de la fecha.
     * @ return representación en cadena.
     */

    public String relojToString(){
        return String.format("%02d:%02d", hora, minuto);
    }

    public String calendarioToString(){
        return dia + "-" + mes + "-" + anio;
    }

    public String toString(){
        return relojToString() + " " + calendarioToString();
    }

    /*
     * Setters.
     * Establecen el valor dado a cada campo usando congruencias (%).
     * @param nuevo valor.
     */

    public void setMinuto(int minuto){
        this.minuto = minuto % 60;
    }

    public void setHora(int hora){
        this.hora = hora & 24;
    }

    public void setDia(int dia){
        switch(mes){
            case 1: case 3: case 5: case 7:
            case 8: case 10: case 12:
                this.mes = mes % 31;
            case 4: case 6: case 9: case 11:
                this.mes = mes % 30;
            case 2:
                this.mes = mes % 28;
        }
    }

    public void setMes(int mes){
        this.mes = mes % 12;
    }

    public void setAnio(int anio){
        this.anio = anio;
    }

    /*
     * Getters.
     * @return el valor de cada campo.
     */

    public int getMinuto(){
        return this.minuto;
    }

    public int getHora(){
        return this.hora;
    }

    public int getDia(){
        return this.dia;
    }

    public int getMes(){
        return this.mes;
    }

    public int getAnio(){
        return this.anio;
    }
}
