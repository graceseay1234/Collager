package view;

import java.awt.*;

import javax.swing.*;

import model.Project;

/**
 * Represents the visual view of the grid for a game of Set. Cards are layed out
 * as a grid of unselected cards. Each card has its text representation printed
 * on the card back.
 */
public class BoardPanel extends JPanel {
  private Project modelState;

  private model.Image img;
  private final int cellDimension;
  private String layerName;

  /**
   * Constructs a new panel for the given project state.
   * @param state the current state of the project
   * @throws IllegalStateException if model is blank
   */
  public BoardPanel(Project state) throws IllegalArgumentException {
    super();
    if (state == null) {
      throw new IllegalArgumentException("parameters cannot be null");
    }
    this.modelState = state;
    this.setBackground(Color.WHITE);
    this.cellDimension = 50;
    this.setPreferredSize(new Dimension(this.modelState.getWidth() + 200,
            this.modelState.getHeight() + 200));


    this.img = this.modelState.getCompositeImage();

  }

  /*
   * empty board panel constructor.
   */
  /*public BoardPanel() {
    super();
    //add height and width later
    this.modelState = new ProjectImpl(100,100);
    this.setBackground(Color.WHITE);
    this.cellDimension = 50;
    this.setPreferredSize(new Dimension(this.modelState.getWidth() + 200,
            this.modelState.getHeight() + 200));

  }*/

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
        //System.out.println("" + r + " " + green + " " + b + "\n");
        //System.out.println("" + i + " " + j + "\n");
        g.setColor(new Color((int)r,
                (int)green,
                (int)b));
        g.drawLine(j,i,j,i);
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
