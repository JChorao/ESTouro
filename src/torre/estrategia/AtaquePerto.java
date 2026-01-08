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
            Point posBloon = b.getBounds().getLocation(); 
            double distancia = posicaoTorre.distance(posBloon);

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                alvoMaisPerto = b;
            }
        }
        return alvoMaisPerto;
    }
}
