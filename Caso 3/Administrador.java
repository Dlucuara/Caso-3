
import java.util.concurrent.ThreadLocalRandom;

public class Administrador extends Thread {
    private int id;
    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;
    private BuzonAdmin buzonAdmin;
    public Administrador(int id, BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores, BuzonAdmin buzonAdmin) {
        this.id = id;
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

    public void EnviarEvento(Evento evento) {
        if(esNormal()){
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

        private boolean esNormal() {
            int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 20);
            return numeroSeudoAleatorio % 4 == 0;
        }

    @Override
    public void run() {
        while (buzonAdmin.estaVacio()) {
            Evento evento = leerEventos();  
            EnviarEvento(evento);     
        }
         System.out.println("Administrador " + id + " terminó.");
    }
    
}
