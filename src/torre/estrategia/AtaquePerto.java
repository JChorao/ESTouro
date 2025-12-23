package torre.estrategia;

import java.util.List;
import java.awt.Point;
import bloon.Bloon;

public class AtaquePerto implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
        if (bloons == null || bloons.isEmpty() || posicaoTorre == null) {
            return null;
        }

        Bloon alvoMaisPerto = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Bloon b : bloons) {
            // Obtém o centro do bloon
            Point posBloon = b.getBounds().getLocation(); 
            // O getBounds().getLocation() dá o canto superior esquerdo, 
            // idealmente seria o centro, mas para este efeito serve.
            
            double distancia = posicaoTorre.distance(posBloon);

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                alvoMaisPerto = b;
            }
        }
        return alvoMaisPerto;
    }
}
