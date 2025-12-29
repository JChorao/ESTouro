package game.manipulator;

import torre.*;

public class ManipuladorTorreVisitor implements TorreVisitor {
// Variável para guardar o resultado
    private ManipuladorTorre resultado;

    // Método para recuperar o resultado depois da visita
    public ManipuladorTorre getResultado() {
        return resultado;
    }

    @Override
    public void visita(Torre t) {
        resultado = new ManipuladorVazio(t);
    }

    @Override
    public void visita(TorreSniper t) {
        resultado = new ManipuladorSniper(t);
    }
    
    // ... (faz o mesmo para Balista, Morteiro, Octo: resultado = new ...)

    @Override public void visita(TorreBalista t) { resultado = new ManipuladorBalista(t); }
    @Override public void visita(TorreMorteiro t) { resultado = new ManipuladorMorteiro(t); }
    @Override public void visita(TorreOctogonal t) { resultado = new ManipuladorOcto(t); }

    // Torres simples
    @Override public void visita(TorreMacaco t) { resultado = new ManipuladorVazio(t); }
    @Override public void visita(TorreCanhao t) { resultado = new ManipuladorVazio(t); }
    @Override public void visita(TorreNinja t) { resultado = new ManipuladorVazio(t); }
}