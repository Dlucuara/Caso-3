public class Evento {
    private int eventoId;
    private int TipoYServidor;


    public Evento(int eventoId, int tipoYServidor) {
        this.eventoId = eventoId;
        this.TipoYServidor = tipoYServidor;
    }

    // se define un evento de fin con tipoYServidor = -1
    public boolean Esfin() {
        return TipoYServidor == -1; 
    }


    
}
