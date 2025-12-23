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
        
        // Lógica do Switch: bloons.stream().max((b1, b2) -> b1.getPosicaoNoCaminho() - b2.getPosicaoNoCaminho())
        // Encontra o Bloon com o maior valor de "posicaoNoCaminho"
        return bloons.stream()
                .max(Comparator.comparingInt(Bloon::getPosicaoNoCaminho))
                .orElse(null);
    }
}