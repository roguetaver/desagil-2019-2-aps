package br.pro.hashi.ensino.desagil.aps.model;

public class TripleInputOrGate extends Gate {

    private final NandGate nandTop1;
    private final NandGate nandMid;
    private final NandGate nandBottom;
    private final NandGate nandfinal;


    public TripleInputOrGate() {
        super("TripleInputOR", 3);

        nandTop1 = new NandGate();

        nandMid = new NandGate();

        nandBottom = new NandGate();

        NandGate nandTop2 = new NandGate();
        nandTop2.connect(0, nandTop1);
        nandTop2.connect(1, nandMid);


        NandGate nandTop3 = new NandGate();
        nandTop3.connect(0, nandTop2);
        nandTop3.connect(1, nandTop2);


        nandfinal = new NandGate();
        nandfinal.connect(0, nandTop3);
        nandfinal.connect(1, nandBottom);

    }


    @Override
    public boolean read(int outputPin) {
        if (outputPin != 0) {
            throw new IndexOutOfBoundsException(outputPin);
        }
        return nandfinal.read();
    }


    @Override
    public void connect(int inputPin, SignalEmitter emitter) {
        switch (inputPin) {
            case 0:
                nandTop1.connect(0, emitter);
                nandTop1.connect(1, emitter);
                break;
            case 1:
                nandMid.connect(0, emitter);
                nandMid.connect(1, emitter);
                break;
            case 2:
                nandBottom.connect(0, emitter);
                nandBottom.connect(1, emitter);
                break;
            default:
                throw new IndexOutOfBoundsException(inputPin);
        }
    }
}
