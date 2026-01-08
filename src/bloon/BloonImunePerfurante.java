package bloon;

public class BloonImunePerfurante extends BloonDecorator {

    public BloonImunePerfurante(Bloon bloonDecorado) {
        super(bloonDecorado);
    }

    @Override
    public int pop(int estrago) {
        // Ignora o dano perfurante   
        return 0;
    }
}