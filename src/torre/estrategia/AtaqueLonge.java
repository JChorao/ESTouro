package torre.estrategia;

import bloon.Bloon;
import java.awt.Point;
import java.util.List;

public class AtaqueLonge implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
    if (bloons == null || bloons.isEmpty() || posicaoTorre == null) {
            return null;
        }

        Bloon alvoMaisLonge = null;
        // Inicializamos com -1 (ou 0) para garantir que a primeira distância encontrada seja maior
        double maiorDistancia = -1.0; 

        for (Bloon b : bloons) {
            // Obtém o centro do bloon
            Point posBloon = b.getBounds().getLocation(); 
            // Mantém a lógica original de pegar a localização
            
            double distancia = posicaoTorre.distance(posBloon);

            // AQUI ESTÁ A MUDANÇA PRINCIPAL:
            // Verificamos se a distância atual é MAIOR (>) que a maior registada até agora
            if (distancia > maiorDistancia) {
                maiorDistancia = distancia;
                alvoMaisLonge = b;
            }
        }
        return alvoMaisLonge;
    }
}