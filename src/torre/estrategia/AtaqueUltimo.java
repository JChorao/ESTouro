package torre.estrategia;

import java.util.Comparator;
import java.util.List;
import java.awt.Point;
import bloon.Bloon;

public class AtaqueUltimo implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
        if (bloons == null || bloons.isEmpty()) {
            return null;
        }

        // Lógica do Switch: bloons.stream().min(...)
        // Encontra o Bloon com o menor valor de "posicaoNoCaminho"
        return bloons.stream()
                .min(Comparator.comparingInt(Bloon::getPosicaoNoCaminho))
                .orElse(null);
    }
}