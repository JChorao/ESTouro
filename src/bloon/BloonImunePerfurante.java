package bloon;

public class BloonImunePerfurante extends BloonDecorator {

    public BloonImunePerfurante(Bloon bloonDecorado) {
        super(bloonDecorado);
    }

    @Override
    public int pop(int estrago) {
        // Se for atingido por algo perfurante (pop), devolve o estrago intacto
        // e NÃO chama o super.pop(), logo não perde vida.
        System.out.println("Imune a Perfurante! O dardo bateu na armadura.");
        
        // Retornar 'estrago' significa que o projétil não gastou força (ou ricocheteou).
        // Se quiseres que o dardo "suma" ao bater (como num Bloon de Chumbo), retorna 0.
        return 0; 
    }
}