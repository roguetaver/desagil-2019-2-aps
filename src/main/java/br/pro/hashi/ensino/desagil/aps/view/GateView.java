
package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Switch[] switches;
    private final Image image;
    private Light light;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;



    public GateView(Gate gate) {
        super(250, 150);

        this.gate = gate;

        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        light = new Light();
        light.connect(0,gate);
        light.setR(255);


        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        JLabel inputLabel = new JLabel("Entrada");

        add(inputLabel);

        if (inputBoxes.length == 1){
            add(inputBoxes[0],26,54,25,25);
        }
        else{
            int y = 39;
            for (JCheckBox inputBox : inputBoxes) {
                add(inputBox,40,y,25,25);
                y += 30;
            }
        }

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addActionListener(this);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);
        addMouseListener(this);

        update();
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        Color color;

        if (Math.pow((event.getX() - 180),2) + Math.pow((event.getY() - 60),2) <= Math.pow(17,2)){

            color = JColorChooser.showDialog(this, null, new Color(light.getR(), light.getG(), light.getB()));
            if (color != null) {
                light.setB(color.getBlue());
                light.setG(color.getGreen());
                light.setR(color.getRed());
            }
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Color color = new Color(light.getR(),light.getG(),light.getB());
        g.setColor(color);

        g.drawImage(image, 40, 30, 150, 75, this);
        g.fillRoundRect(180,60,15,15,20,20);

        getToolkit().sync();
    }
}
