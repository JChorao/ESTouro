package torre.estrategia;

import bloon.Bloon;
import java.awt.Point; 
import java.util.List;

public interface EstrategiaAtaque {
    Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre);
}