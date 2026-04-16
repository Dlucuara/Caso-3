public class Evento {
    private int eventoId;
    private int TipoYServidor;


    public Evento(int eventoId, int tipoYServidor) {
        this.eventoId = eventoId;
        this.TipoYServidor = tipoYServidor;
    }

    public boolean Esfin() {
        return TipoYServidor == -1;
    }


    
}
