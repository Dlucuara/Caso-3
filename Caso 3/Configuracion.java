import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Configuracion {
    public static int ni;   // número de sensores
    public static int ne;   // número base de eventos
    public static int nc;   // número de clasificadores
    public static int ns;   // número de servidores
    public static int tam1; // capacidad buzón clasificación
    public static int tam2; // capacidad buzón consolidación

    public static void cargarDesdeArchivo(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        ni   = Integer.parseInt(br.readLine().trim());
        ne   = Integer.parseInt(br.readLine().trim());
        nc   = Integer.parseInt(br.readLine().trim());
        ns   = Integer.parseInt(br.readLine().trim());
        tam1 = Integer.parseInt(br.readLine().trim());
        tam2 = Integer.parseInt(br.readLine().trim());
        br.close();
    }
}