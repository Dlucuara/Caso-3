import java.util.concurrent.ThreadLocalRandom;

public class Broker extends Thread {
    private BuzonEntradaEventos buzonEntradaEventos;
    private final int totalEventos;

    private BuzonDeAlertas buzonDeAlertas;
    private BuzonDeClasificadores buzonDeClasificadores;

    private int contadorAlertas = 0;
    private int contadorNormales = 0;
    
    public Broker(BuzonEntradaEventos buzonEntradaEventos , BuzonDeAlertas buzonDeAlertas, BuzonDeClasificadores buzonDeClasificadores) {
        this.totalEventos = Configuracion.ne * (Configuracion.ni * (Configuracion.ni + 1) / 2);
        this.buzonEntradaEventos = buzonEntradaEventos;
        this.buzonDeAlertas = buzonDeAlertas;
        this.buzonDeClasificadores = buzonDeClasificadores;
    }
    private Evento leerEvento() {
        return buzonEntradaEventos.tomarEvento();

    }

    

    public  void EnviarEvento(Evento evento, boolean anomalo) {
       if (anomalo) {
        while (buzonDeAlertas.estaLleno()) {
            try {
                 Thread.sleep(100); 
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
        }
            buzonDeAlertas.depositarEvento(evento);

        } else {
            while (buzonDeClasificadores.estaLleno()) {
                try {
                     Thread.sleep(100); 
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                }
            }
            buzonDeClasificadores.depositarEventoClasificado(evento);
        }
    }

    public boolean esAnomalo() {
        int numeroSeudoAleatorio =ThreadLocalRandom.current().nextInt(0, 201);
        return numeroSeudoAleatorio % 8 == 0;
    }

    @Override
    public void run() {
       System.out.println("[BROKER] Iniciando. Total eventos esperados: " + totalEventos);
        int eventosProcessados = 0;

        while (eventosProcessados < totalEventos) {
            Evento evento = leerEvento();         

            boolean anomalo = esAnomalo();         
            EnviarEvento(evento, anomalo);        
            System.out.println("[BROKER] Evento " + evento.toString() + " -> " + (anomalo ? "ALERTA" : "CLASIFICACION"));
            if (anomalo) contadorAlertas++;
            else contadorNormales++;
            eventosProcessados++;
        }

        System.out.println("[BROKER] Procesados: " + eventosProcessados +
            " | Alertas: " + contadorAlertas +
            " | Clasificados: " + contadorNormales);

        buzonDeAlertas.depositarEvento(new Evento(-1, -1)); 

        System.out.println("[BROKER] TERMINÓ. Evento de fin enviado al administrador.");
    }
}



