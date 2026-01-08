package io;

import java.awt.Point;
import torre.*;

public class GameWriterVisitor implements TorreVisitor {

    private String infoParaGravar;

    public String getInfoParaGravar() {
        return infoParaGravar;
    }

    @Override
    public void visita(Torre t) {
        infoParaGravar = ""; 
    }

    @Override
    public void visita(TorreMacaco t) {
        infoParaGravar = "macaco";
    }

    @Override
    public void visita(TorreCanhao t) {
        infoParaGravar = "canhao";
    }

    @Override
    public void visita(TorreNinja t) {
        infoParaGravar = "ninja";
    }

    @Override
    public void visita(TorreOctogonal t) {
        infoParaGravar = "octo\t" + t.getComponente().getAngulo();
    }

    @Override
    public void visita(TorreBalista t) {
        infoParaGravar = "balista\t" + t.getComponente().getAngulo();
    }
    
    @Override
    public void visita(TorreSniper t) {
        infoParaGravar = "sniper\t" + t.getComponente().getAngulo();
    }

    @Override
    public void visita(TorreMorteiro t) {
        Point ataque = t.getAreaAlvo();
        infoParaGravar = "morteiro\t" + ataque.x + "\t" + ataque.y;
    }
}