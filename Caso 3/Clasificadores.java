import java.util.ArrayList;

public class Clasificadores extends Thread{
    private int id;
    private BuzonDeClasificadores buzonDeClasificadores;
    private ArrayList<BuzonDeConsolidacion> buzonDeConsolidacion;
    private int contador = 0;

    private static int clasificadoresActivos;
    private static final Object lockContador = new Object();


    public static void inicializarContador(int nc) {
        clasificadoresActivos = nc;
    }

    public Clasificadores(BuzonDeClasificadores buzonDeClasificadores, int id, ArrayList<BuzonDeConsolidacion> buzonesConsolidacion) {
        this.buzonDeClasificadores = buzonDeClasificadores;
        this.id = id;
        this.buzonDeConsolidacion = buzonesConsolidacion;
        
        
    }

    @Override
    public void run() {
        System.out.println("[CLASIFICADOR " + id + "] Iniciando.");
        while (true) {
            Evento evento = buzonDeClasificadores.tomarEventoClasificado();
            if (evento.Esfin()) {
                System.out.println("[CLASIFICADOR " + id + "] Recibió evento de fin.");
                verificarUltimo();
                break;
            }

            clasificarEvento(evento);
            contador++;
        }

        System.out.println("[CLASIFICADOR " + id + "] TERMINÓ. Eventos clasificados: " + contador);
    }

    private void verificarUltimo() {
    synchronized (lockContador) {
        clasificadoresActivos--;
        System.out.println("[CLASIFICADOR " + id + "] Clasificadores restantes: " + clasificadoresActivos);

        if (clasificadoresActivos == 0) {
            System.out.println("[CLASIFICADOR " + id + "] Soy el último. Enviando fin a "
                + Configuracion.ns + " servidores.");

            for (int i = 0; i < Configuracion.ns; i++) {
                buzonDeConsolidacion.get(i).depositarEventoConsolidado(new Evento(-1, -1));
                System.out.println("[CLASIFICADOR " + id + "] Fin enviado a Servidor " + (i + 1));
            }
        }
    }
    }
    


    public void clasificarEvento(Evento evento) {
        int tipoClasificacion = evento.getTipoYServidor() - 1; 
        buzonDeConsolidacion.get(tipoClasificacion).depositarEventoConsolidado(evento);
        System.out.println("[CLASIFICADOR " + id + "] " + evento + " -> Servidor " + (tipoClasificacion + 1));
       
        
    }
}
    


