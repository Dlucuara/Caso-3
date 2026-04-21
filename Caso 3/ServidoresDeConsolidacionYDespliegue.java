import java.util.Random;

public class ServidoresDeConsolidacionYDespliegue extends Thread {
    private int id;
    private BuzonDeConsolidacion buzonConsolidacion;
    private int contador = 0;

    public ServidoresDeConsolidacionYDespliegue( int id, BuzonDeConsolidacion buzon) {
        this.id = id;
        this.buzonConsolidacion = buzon;
    }

    @Override
    public void run() {
        System.out.println("[SERVIDOR " + id + "] Iniciando.");
        
            while (true) {
                Evento evento = buzonConsolidacion.tomarEventoConsolidado();
                
                if (evento.Esfin()) {
                    System.out.println("[SERVIDOR " + id + "] Recibió fin. Terminando.");
                    break;
                }

                procesarEvento(evento);
                contador++;
        }

        System.out.println("[SERVIDOR " + id + "] TERMINÓ. Procesados: " + contador);
    }

    private void procesarEvento(Evento evento) {
        int tiempo = 100 + new Random().nextInt(901); // entre 100ms y 1000ms
        System.out.println("[SERVIDOR " + id + "] Procesando " + evento + " durante " + tiempo + "ms...");
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[SERVIDOR " + id + "] " + evento + " procesado.");
        
        
    }
}