package torre.estrategia;

import bloon.Bloon;
import java.awt.Point;
import java.util.List;

public class AtaqueForte implements EstrategiaAtaque {
    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
        if (bloons == null || bloons.isEmpty()) {
            return null;
        }

        Bloon alvoMaisForte = null;
        int maiorValor = 0; 

        for (Bloon b : bloons) {
            if (b.getValor() > maiorValor) {
                maiorValor = b.getValor();
                alvoMaisForte = b;
            }
        }
        return alvoMaisForte;
    }
}
