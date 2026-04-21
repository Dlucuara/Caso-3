import java.io.IOException;
import java.util.ArrayList;

public class Principal {

public static void main(String[] args) throws IOException {
    Configuracion.cargarDesdeArchivo("./Caso-3./config.txt");

        BuzonEntradaEventos buzonEntrada = new BuzonEntradaEventos();
        BuzonDeAlertas buzonDeAlertas = new BuzonDeAlertas();
        BuzonDeClasificadores buzonDeClasificadores = new BuzonDeClasificadores();
        BuzonAdmin buzonAdmin = new BuzonAdmin();
        ArrayList<BuzonDeConsolidacion> buzonesDeConsolidacion = new ArrayList<>();
        Sensores[] sensores = new Sensores[Configuracion.ni];

        for (int i = 0; i < Configuracion.ni; i++) {
            sensores[i] = new Sensores(i + 1, buzonEntrada); 
            sensores[i].start();
        }

        Broker broker = new Broker(buzonEntrada, buzonDeAlertas, buzonDeClasificadores, buzonAdmin);
        broker.start();

        Administrador administrador = new Administrador(buzonDeAlertas, buzonDeClasificadores, buzonAdmin);
        administrador.start();

        for (int i=0; i<Configuracion.ns; i++) {
            buzonesDeConsolidacion.add(new BuzonDeConsolidacion(i, Configuracion.tam2));
        }

        for (int i = 0; i < Configuracion.nc; i++) {
            Clasificadores clasificador = new Clasificadores(buzonDeClasificadores, buzonesDeConsolidacion);
            clasificador.start();
        }

        for (int i = 0; i < Configuracion.ns; i++) {
            ServidoresDeConsolidacionYDespliegue servidor = new ServidoresDeConsolidacionYDespliegue(buzonesDeConsolidacion.get(i));
            servidor.start();
        }

        
    }

}