
import java.util.concurrent.ThreadLocalRandom;

public class Administrador extends Thread {
    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;

    private int contadorDescartados = 0;
    private int contadorRenviados = 0;
    public Administrador( BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores) {
        this.buzonDeAlertas = buzonDeAlertas;
        this.buzonDeClasificadores = buzonDeClasificadores;
    }

    private Evento leerEventos(){
        while (buzonDeAlertas.estaVacio()) {
            try {
                 Thread.sleep(100); 
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            
        }
        return buzonDeAlertas.tomarEvento();
    }

    public void EnviarEvento(Evento evento, boolean normal){ {
        if(normal) {
            while (buzonDeClasificadores.estaLleno()) {
                try {
                    Thread.sleep(100); 
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
            }
           
        }
         buzonDeClasificadores.depositarEventoClasificado(evento);
        }
        // se descarta el evento, no se hace nada
        }
       
    }

        private boolean esNormal() {
            int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 21);
            return numeroSeudoAleatorio % 4 == 0;
        }

        private void enviarFinAClasificadores() {
        System.out.println("[ADMIN] Enviando " + Configuracion.nc + " eventos de fin a clasificadores.");
        for (int i = 0; i < Configuracion.nc; i++) {
            buzonDeClasificadores.depositarEventoClasificado(new Evento(-1, -1));
        }
    }

    @Override
    public void run() {
        System.out.println("[ADMIN] Iniciando.");

        while (true) {
            Evento evento = leerEventos();

            if (evento.Esfin()) {
                System.out.println("[ADMIN] Recibió evento de fin del broker.");
                break;
            }

            boolean normal = esNormal();
            EnviarEvento(evento, normal);

            System.out.println("[ADMIN] Evento " + evento + " -> " 
                + (normal ? "REENVIADO a clasificacion" : "DESCARTADO"));

            if (normal) contadorRenviados++;
            else contadorDescartados++;
        }

        System.out.println("[ADMIN] TERMINO. Reenviados: " + contadorRenviados
            + " | Descartados: " + contadorDescartados);

        enviarFinAClasificadores();
    }
    
}
