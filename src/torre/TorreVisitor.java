package torre;

public interface  TorreVisitor {

    void visita(Torre t);

    void visita(TorreSniper t);
    void visita(TorreBalista t);
    void visita(TorreMorteiro t);
    void visita(TorreOctogonal t);
    void visita(TorreMacaco t);
    void visita(TorreCanhao t);
    void visita(TorreNinja t);
}
