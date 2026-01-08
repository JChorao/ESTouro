package torre.estrategia;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.Point;
import bloon.Bloon;

public class AtaqueJuntos implements EstrategiaAtaque {

    @Override
    public Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre) {
        if (bloons == null || bloons.isEmpty()) {
            return null;
        }

        Map<Integer, List<Bloon>> posicoes = bloons.stream()
                .collect(Collectors.groupingBy(b -> b.getPosicaoNoCaminho() / 20));

        if (posicoes.isEmpty()) return null;

        int posicaoComMais = Collections.max(posicoes.keySet(),
                (k1, k2) -> posicoes.get(k1).size() - posicoes.get(k2).size());

        List<Bloon> grupoAlvo = posicoes.get(posicaoComMais);
        if (grupoAlvo != null && !grupoAlvo.isEmpty()) {
            return grupoAlvo.get(0);
        }
        
        return null;
    }
}