import java.util.concurrent.ThreadLocalRandom;

public class Sensores extends Thread {
    private final int id;
    private final int numeroEventos;
    private BuzonEntradaEventos buzonEntradaEventos;

    public Sensores(int id, BuzonEntradaEventos buzonEntradaEventos) {
        this.id = id;
        this.numeroEventos = Configuracion.ne * id;
        this.buzonEntradaEventos = buzonEntradaEventos;
    }

   

    public void generarEventos() {
         for (int i = 0; i < numeroEventos; i++) {

            while (buzonEntradaEventos.estaLleno()) {
            try {
                    Thread.yield();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
        }
            int eventoId = id * 1000 + i; 
            int tipoYServidor = ThreadLocalRandom.current().nextInt(1, Configuracion.ns);
            Evento evento = new Evento(eventoId, tipoYServidor);
            buzonEntradaEventos.depositarEvento(evento);
        }
          System.out.println("Sensor " + id + " terminó.");
    }
    

    @Override
    public void run() {
        generarEventos();
        
        
    }



    
}