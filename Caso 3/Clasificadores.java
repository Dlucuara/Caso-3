import java.util.ArrayList;

public class Clasificadores extends Thread{
    private BuzonDeClasificadores buzonDeClasificadores;
    private ArrayList<BuzonDeConsolidacion> buzonDeConsolidacion;
    private int ns = Configuracion.ns;

    public Clasificadores(BuzonDeClasificadores buzonDeClasificadores) {
        this.buzonDeClasificadores = buzonDeClasificadores;
        for (int i=0; i<ns; i++) {
            this.buzonDeConsolidacion.add(new BuzonDeConsolidacion(i, Configuracion.tam2));
        }
    }

    @Override
    public void run() {
        while (buzonDeClasificadores.estaLleno()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            Evento evento = buzonDeClasificadores.tomarEventoClasificado();
            clasificarEvento(evento);
    }


    public void clasificarEvento(Evento evento) {
        int tipoClasificacion = evento.getTipoYServidor();
        if (tipoClasificacion == -1) {
            for (int i=0; i<ns; i++) {
            while (buzonDeConsolidacion.get(i).estaLleno()) {
                try {
                    wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
                Evento eventoFin = new Evento(i, -1);
                buzonDeConsolidacion.get(i).depositarEventoConsolidado(eventoFin);
            }                
            }
        }
        else {
            while (buzonDeConsolidacion.get(tipoClasificacion).estaLleno()) {
                try {
                    wait();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            int servidorAsignado = evento.getTipoYServidor();
            buzonDeConsolidacion.get(servidorAsignado).depositarEventoConsolidado(evento);
            }                   
        }
        
    }
}
    


