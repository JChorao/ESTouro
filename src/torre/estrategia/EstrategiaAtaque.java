package torre.estrategia;

import bloon.Bloon;
import java.awt.Point; // Importa a classe Bloon do pacote 'bloon'
import java.util.List;

public interface EstrategiaAtaque {
    // Adicionamos 'posicaoTorre' para as estratégias que dependem da distância física
    Bloon escolherAlvo(List<Bloon> bloons, Point posicaoTorre);
}