public class Evento {
    private int eventoId;
    private int tipoYServidor;


    public Evento(int eventoId, int tipoYServidor) {
        this.eventoId = eventoId;
        this.tipoYServidor = tipoYServidor;
    }

    // se define un evento de fin con tipoYServidor = -1
    public boolean Esfin() {
        return tipoYServidor == -1; 
    }

    public int getId() {
        return eventoId;
    }

    public int getTipoYServidor() {
        return tipoYServidor;
    }

    @Override
    public String toString() {
    if (Esfin()) {
        return "[EventoFin]";
    }
    return "[Evento id=" + eventoId + " tipo=" + tipoYServidor + "]";
}


    
}
