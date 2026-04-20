import java.util.Random;

// ServidorConsolidacion.java
public class ServidoresDeConsolidacionYDespliegue extends Thread {
    private BuzonDeConsolidacion buzonConsolidacion;

    public ServidoresDeConsolidacionYDespliegue(BuzonDeConsolidacion buzon) {
        this.buzonConsolidacion = buzon;
    }

    @Override
    public void run() {
        try {
            boolean fin = false;
            while (!fin) {
                Evento evento = buzonConsolidacion.tomarEventoConsolidado();
                
                if (evento.Esfin()) {
                    System.out.println(Thread.currentThread().getName() + " - Evento de fin recibido. Finalizando.");
                    fin =true;
                }

                // Procesar el evento durante un tiempo aleatorio entre 100 y 1000 ms
                Random rand = new Random();
                int tiempoProceso = 100 + rand.nextInt(901);  // Entre 100 ms y 1000 ms
                System.out.println(Thread.currentThread().getName() + " - Procesando evento: " + evento.getId());
                Thread.sleep(tiempoProceso);  // Simula el procesamiento del evento
                System.out.println(Thread.currentThread().getName() + " - Evento procesado: " + evento.getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}