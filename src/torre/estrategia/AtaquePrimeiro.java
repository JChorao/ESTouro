package torre.estrategia;

import bloon.Bloon;
import java.awt.Point;
import java.util.Comparator;
import java.util.List;

public class AtaquePrimeiro implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
        if (bloons == null || bloons.isEmpty()) {
            return null;
        }
        
        return bloons.stream()
                .max(Comparator.comparingInt(Bloon::getPosicaoNoCaminho))
                .orElse(null);
    }
}