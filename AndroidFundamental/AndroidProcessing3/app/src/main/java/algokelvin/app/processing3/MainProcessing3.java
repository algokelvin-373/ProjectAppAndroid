package algokelvin.app.processing3;

import processing.core.PApplet;

public class MainProcessing3 extends PApplet {
    @Override
    public void setup() {
        super.setup();
        size(250, 250);
    }

    @Override
    public void draw() {
        super.draw();
        String txt1 = "AlgoKelvin";
        String txt2 = "I'm learn Coding Processing3";

        textSize(25);
        fill(0);
        text(txt1, 25, 180);

        textSize(15);
        fill(255, 0, 0);
        text(txt2, 25, 200);
    }
}
