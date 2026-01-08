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
        double maiorDistancia = -1.0; 

        for (Bloon b : bloons) {
            Point posBloon = b.getBounds().getLocation(); 
            
            double distancia = posicaoTorre.distance(posBloon);
            
            if (distancia > maiorDistancia) {
                maiorDistancia = distancia;
                alvoMaisLonge = b;
            }
        }
        return alvoMaisLonge;
    }
}