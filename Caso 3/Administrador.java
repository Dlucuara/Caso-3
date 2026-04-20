
import java.util.concurrent.ThreadLocalRandom;

public class Administrador extends Thread {
    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;
    private BuzonAdmin buzonAdmin;

    private int contadorDescartados = 0;
    private int contadorRenviados = 0;
    public Administrador( BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores, BuzonAdmin buzonAdmin) {
        this.buzonDeAlertas = buzonDeAlertas;
        this.buzonDeClasificadores = buzonDeClasificadores;
        this.buzonAdmin = buzonAdmin;
    }

    private Evento leerEventos(){
        while (buzonDeAlertas.estaVacio()) {
            try {
                 Thread.yield();
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
                    Thread.yield();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
            }
            buzonDeClasificadores.depositarEventoClasificado(evento);
        }
        }else {
                while (buzonDeAlertas.estaLleno()) {
                    try {
                        Thread.yield();
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                        return;
                }
            }
                buzonDeAlertas.decartarEvento(evento);
            }
        }
       
    }

        private boolean esNormal() {
            int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 20);
            return numeroSeudoAleatorio % 4 == 0;
        }

    @Override
    public void run() {
        while (buzonAdmin.estaVacio()) {
            Evento evento = leerEventos();  
            boolean normal = esNormal();
            EnviarEvento(evento, normal); 
            System.out.println("[ADMIN] Evento " + evento + " → " + (normal ? "REENVIADO a clasificación" : "DESCARTADO"));
                if (normal) {
                    contadorRenviados++;
                } else {
                    contadorDescartados++;
                }
        }
      System.out.println("[ADMIN] TERMINÓ. Reenviados: " + contadorRenviados + 
    " | Descartados: " + contadorDescartados +
    " | Enviando " + Configuracion.nc + " eventos de fin a clasificadores.");
    }
    
}
