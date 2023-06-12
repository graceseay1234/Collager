package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import model.Project;

/**
 * Represents the visual view of the Collager Project.
 * On it, there is:
 * - a panel where the current image is displayed
 * - a list of what layers are in the project to the right
 * with the current layer in cyan, and the rest in black
 * - a text bar to write in, and buttons to do different actions
 * (see SwingGUI view for more information)
 * (buttons are: new-project, add-layer, exit, switch Layer
 * add image to layer, load project, save image, save project
 * set-filter)
 * This class has
 * - a Project to keep up with the current state of the model
 * - and an Image which represents what the Project currently looks like
 */
public class BoardPanel extends JPanel {
  private final Project modelState;

  private model.Image img;

  /**
   * Constructs a new panel for the given project state.
   * @param state the current state of the project
   * @throws IllegalStateException if model is null
   */
  public BoardPanel(Project state) throws IllegalArgumentException {
    super();
    if (state == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }
    this.modelState = state;
    this.setBackground(Color.WHITE);
    this.setPreferredSize(new Dimension(this.modelState.getWidth() + 200,
            this.modelState.getHeight() + 200));


    this.img = this.modelState.getCompositeImage();

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // draw the image here!
    String[][] rgbs = this.modelState.getCompositeImage().getRgbs();
    String[] rgb;
    double r;
    double green;
    double b;
    for (int i = 0; i < this.img.getHeight(); i++) {
      for (int j = 0; j < this.img.getWidth(); j++) {
        rgb = rgbs[i][j].split(",");
        r = Double.parseDouble(rgb[0]);
        green = Double.parseDouble(rgb[1]);
        b = Double.parseDouble(rgb[2]);
        g.setColor(new Color((int)r,
                (int)green,
                (int)b));
        g.fillRect(j,i,1,1);
      }
    }

    // draw layers
    // change color of current layer
    int x;
    int y;
    String name;
    for (int i = 0; i < this.modelState.getLayerNames().size(); i++) {
      name = this.modelState.getLayerNames().get(i);
      if (name.equals(this.modelState.getCurrentLayerName())) {
        g.setColor(Color.cyan);
      }
      else {
        g.setColor(Color.black);
      }
      x = this.modelState.getWidth() + 100;
      y = (i * 20) + 20;
      if ( y > this.getHeight()) {
        x = x + 50;
        y = y - this.getHeight() + 20;
      }


      g.drawString(name, x, y);
    }

  }
}
