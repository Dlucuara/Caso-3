import java.io.IOException;

public class Principal {

public static void main(String[] args) throws IOException {
    Configuracion.cargarDesdeArchivo("config.txt");

        BuzonEntradaEventos buzonEntrada = new BuzonEntradaEventos();
        BuzonDeAlertas buzonDeAlertas = new BuzonDeAlertas();
        BuzonDeClasificadores buzonDeClasificadores = new BuzonDeClasificadores();
        BuzonAdmin buzonAdmin = new BuzonAdmin();

        Sensores[] sensores = new Sensores[Configuracion.ni];

        for (int i = 0; i < Configuracion.ni; i++) {
            sensores[i] = new Sensores(i + 1, buzonEntrada); 
            sensores[i].start();
        }

        Broker broker = new Broker(buzonEntrada, buzonDeAlertas, buzonDeClasificadores, buzonAdmin);
        broker.start();

        Administrador administrador = new Administrador(buzonDeAlertas, buzonDeClasificadores, buzonAdmin);
        administrador.start();
    }

}