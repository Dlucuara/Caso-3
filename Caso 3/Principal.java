import java.io.IOException;
import java.util.ArrayList;

public class Principal {

public static void main(String[] args) throws IOException, InterruptedException {
    Configuracion.cargarDesdeArchivo("config.txt");

        System.out.println("Sensores (ni):              " + Configuracion.ni);
        System.out.println("Base eventos (ne):          " + Configuracion.ne);
        System.out.println("Clasificadores (nc):        " + Configuracion.nc);
        System.out.println("Servidores (ns):            " + Configuracion.ns);
        System.out.println("Tam buzón clasificación:    " + Configuracion.tam1);
        System.out.println("Tam buzón consolidación:    " + Configuracion.tam2);
        int totalEsperado = Configuracion.ne * (Configuracion.ni * (Configuracion.ni + 1) / 2);
        System.out.println("Total eventos esperados:    " + totalEsperado);

        //Creacion de buzones
        BuzonEntradaEventos buzonEntrada = new BuzonEntradaEventos();
        BuzonDeAlertas buzonDeAlertas = new BuzonDeAlertas();
        BuzonDeClasificadores buzonDeClasificadores = new BuzonDeClasificadores(Configuracion.tam1);

        ArrayList<BuzonDeConsolidacion> buzonesConsolidacion = new ArrayList<>();
        for (int i = 0; i < Configuracion.ns; i++) {
            buzonesConsolidacion.add(new BuzonDeConsolidacion(i + 1, Configuracion.tam2));
        }

        //creacion de sensores
        Sensores[] sensores = new Sensores[Configuracion.ni];

        for (int i = 0; i < Configuracion.ni; i++) {
            sensores[i] = new Sensores(i + 1, buzonEntrada); 
            sensores[i].start();
        }

        //Creacion de broker
        Broker broker = new Broker(buzonEntrada, buzonDeAlertas, buzonDeClasificadores);
        broker.start();

        //Creacion de administrador
        Administrador administrador = new Administrador(buzonDeAlertas, buzonDeClasificadores);
        administrador.start();


        //Creacion de clasificadores
        Clasificadores.inicializarContador(Configuracion.nc);
        Clasificadores[] clasificadores = new Clasificadores[Configuracion.nc];     
        for (int i = 0; i < Configuracion.nc; i++) {
            clasificadores[i] = new Clasificadores(buzonDeClasificadores, i + 1, buzonesConsolidacion);
            clasificadores[i].start();
        }


        //Creacion de servidores
        ServidoresDeConsolidacionYDespliegue[] servidores = new ServidoresDeConsolidacionYDespliegue[Configuracion.ns];
        for (int i = 0; i < Configuracion.ns; i++) {
            servidores[i] = new ServidoresDeConsolidacionYDespliegue(i + 1, buzonesConsolidacion.get(i));
            servidores[i].start();
        }

        // esperar a que terminen todos los hilos
        for (Sensores s : sensores)                         s.join();
        broker.join();
        administrador.join();
        for (Clasificadores c : clasificadores)             c.join();
        for (ServidoresDeConsolidacionYDespliegue sv : servidores) sv.join();

        // verificar que los buzones estén vacíos
        System.out.println("Buzón entrada vacío:        " + buzonEntrada.estaVacio());
        System.out.println("Buzón alertas vacío:        " + buzonDeAlertas.estaVacio());
        System.out.println("Buzón clasificación vacío:  " + buzonDeClasificadores.estaVacio());
        for (int i = 0; i < Configuracion.ns; i++) {
            System.out.println("Buzón consolidación " + (i+1) + " vacío: " 
                + buzonesConsolidacion.get(i).estaVacio());
        }
    }
        


}