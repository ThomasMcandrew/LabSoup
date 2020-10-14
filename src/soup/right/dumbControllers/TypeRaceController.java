package soup.right.dumbControllers;

import soup.center.CenterController;
import soup.center.panels.AbstractPanel;
import soup.center.panels.dumbPanels.TypeRace;
import soup.right.AbstractController;

import javax.swing.*;

public class TypeRaceController extends AbstractController {
    private JLabel wpm,correct,total;
    private TypeRace panel;
    public TypeRaceController(CenterController controller) {
        super(controller);
    }


    public void setWPM(int n){
        wpm.setText("WPM: " + n);

    }
    public void setCorrect(int n){
        correct.setText("Correct Words: " + n);

    }
    public void setTotal(int n){
        total.setText("Total Words: " + n);

    }

    @Override
    protected void init() {
        wpm = new JLabel("WPM: 0");
        correct = new JLabel("Correct Words: 0");
        total = new JLabel("Total Words: 0");
        add(wpm);
        add(correct);
        add(total);
    }

    @Override
    public void setPanel(AbstractPanel panel) {
        this.panel = (TypeRace) panel;
        this.panel.setControls(this);
    }
}
