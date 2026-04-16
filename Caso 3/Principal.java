import java.io.IOException;

public class Principal {

public static void main(String[] args) throws IOException {
    Configuracion.cargarDesdeArchivo("config.txt");

        Sensores[] sensores = new Sensores[Configuracion.ni];

        for (int i = 0; i < Configuracion.ni; i++) {
            sensores[i] = new Sensores(i + 1, null); 
            sensores[i].start();
        }
    }

}