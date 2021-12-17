package excepciones;

public class AlmacenExepcion extends RuntimeException{

    public AlmacenExepcion(String mensaje){
     super(mensaje);
    }

    public AlmacenExepcion(String mensaje , Throwable excepcion){
        super(mensaje, excepcion);
    }
    
}
