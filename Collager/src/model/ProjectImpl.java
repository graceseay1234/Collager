package model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * Implementation of Project class.
 * Contains Layers,
 * which each contain an Image
 * - layers are stored by saving the name of the layer
 * in an arrayList, to keep the layers in order,
 * and then in a Hashmap, where key = name, data = Layer object
 * - a project has a current layer, which is used in the GUIView to apply filters
 * and perform other operations on the highlighted layer in the view, so the user does not
 * have to type in the name and the application is more user-friendly
 */
public class ProjectImpl implements Project {
  private int height;
  private int width;
  // made protected instead of private for testing purposes
  protected HashMap<String, Layer> layers;
  private ArrayList<String> layerOrder;
  private String currentLayerName;

  /**
   * Create a new Collager Project.
   * this project has a default blank white layer called, "Layer0"
   * @param h height of the project (in pixels)
   * @param w width of the project (in pixels)
   * @throws IllegalArgumentException if the given h and/or w are 0 or less
   */
  public ProjectImpl(int h, int w) throws IllegalArgumentException {
    if ((h <= 0) || (w <= 0)) {
      throw new IllegalArgumentException(
              "Height and/or width of a Project cannot be 0 or negative");
    }
    this.height = h;
    this.width = w;
    this.layers = new HashMap<String, Layer>();
    this.layerOrder = new ArrayList<String>();
    // add a blank layer
    this.layers.put("Layer0", new LayerImpl(h, w));
    this.layerOrder.add("Layer0");
    this.currentLayerName = "Layer0";
  }

  /**
   * create a blank project, mainly for setting up main.
   */
  public ProjectImpl() {
    this.layers = new HashMap<String, Layer>();
    this.layerOrder = new ArrayList<String>();
    this.currentLayerName = "Layer0";
  }

  @Override
  public void setHeightAndWidth(int h, int w) throws IllegalArgumentException {
    if ((h <= 0) || (w <= 0)) {
      throw new IllegalArgumentException(
              "Height and/or width of a Project cannot be 0 or negative");
    }
    this.height = h;
    this.width = w;

    // remove all old layers so all layers are same height and width
    // this is creating a new project essentially so its fine
    this.layers.clear();
    this.layerOrder.clear();


    // add a blank layer
    this.layers.put("Layer0", new LayerImpl(h, w));
    this.layerOrder.add("Layer0");
    this.currentLayerName = "Layer0";
  }


  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    if (this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is already a layer by that name");
    }
    layers.put(layerName, new LayerImpl(this.height, this.width));
    layerOrder.add(layerName);
    this.currentLayerName = layerName;
  }

  @Override
  public void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalArgumentException {
    if (!this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is no such layer");
    }
    layers.get(layerName).addImage(new ImageImpl(imageName, this.height, this.width), x, y);
    this.currentLayerName = layerName;
  }

  @Override
  public void addImageToLayerRGB(String layerName, String[][] rgb)
          throws IllegalArgumentException {
    if (!this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is no such layer");
    }
    layers.get(layerName).addImage(new ImageImpl(rgb, this.width, this.height), 0, 0);
    this.currentLayerName = layerName;
  }

  @Override
  public void setFilter(String layerName, String filterOption)
          throws IllegalArgumentException {
    if (!this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is no such layer");
    }
    // note, don't need to check that filterOption is not empty
    // because reader would not read anything if was empty
    // (This is the same for all other string inputs for functions in this class,
    // inputs from the controller handle their own things, a string
    // out of bounds exception would be thrown if anything was not there
    // before it got to ProjectImpl)
    layers.get(layerName).addFilter(filterOption);
    this.currentLayerName = layerName;
  }

  @Override
  public void setCompositeFilter(String layerName, String filterOption)
          throws IllegalArgumentException {
    if (!this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is no such layer");
    }

    // concat all layers to a new temp layer
    this.addLayer("tempLayerName1234");
    int index = layerOrder.indexOf(layerName);
    for (int i = 0; i < index; i++) {
      this.layers.get("tempLayerName1234").concatLayer(this.layers.get(this.layerOrder.get(i)));
    }
    // send the temp layer as an extra argument
    layers.get(layerName).addCompositeFilter(filterOption, this.layers.get("tempLayerName1234"));


    // delete the temp layer
    this.layers.remove("tempLayerName1234");
    this.layerOrder.remove("tempLayerName1234");

    this.currentLayerName = layerName;

  }

  @Override
  public Image getCompositeImage() {
    // concat all layers to a new temp layer
    String layerName = this.currentLayerName;
    this.addLayer("tempLayerName12345");
    int index = layerOrder.size();
    for (int i = 0; i < index; i++) {
      this.layers.get("tempLayerName12345").concatLayer(this.layers.get(this.layerOrder.get(i)));
    }
    Image img = this.layers.get("tempLayerName12345").getImage();
    // delete the temp layer
    this.layers.remove("tempLayerName12345");
    this.layerOrder.remove("tempLayerName12345");

    this.currentLayerName = layerName;
    return img;

  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public List<String> getLayerNames() {
    return this.layerOrder;
  }

  @Override
  public String getCurrentLayerName() {
    return this.currentLayerName;
  }


  // save as PPM
  @Override
  public void saveImage(String fileName) {
    // concat all layers
    // then print their data and save
    for (int i = 0; i < this.layers.size(); i++) {
      this.layers.get("Layer0").concatLayer(this.layers.get(this.layerOrder.get(i)));
    }

    BufferedWriter writer = null;


    if (fileName.contains(".ppm")) {
      String data = this.layers.get("Layer0").getLayerDataPPM();

      try {
        writer = new BufferedWriter(new FileWriter(new File(fileName)));
        writer.write(data);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (writer != null) {
            writer.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if (fileName.contains(".jpeg")) {
      System.out.println("JPEG SAVE");
      BufferedImage buffered = this.layers.get("Layer0").getBufferedImageOfLayer();
      try {
        ImageIO.write(buffered, "jpeg", new File(fileName));

      } catch (IOException ex) {
        // do nothing
      }
    }
    if (fileName.contains(".png")) {
      System.out.println("PNG SAVE");
      BufferedImage buffered = this.layers.get("Layer0").getBufferedImageOfLayer();
      try {
        ImageIO.write(buffered, "png", new File(fileName));

      } catch (IOException ex) {
        // do nothing
      }

    }


  }


  // save as Layer Content Format
  @Override
  public void saveProject(String fileName) {
    String data = fileName + "\n" + this.width + " " + this.height + "\n225\n";
    for (int i = 0; i < this.layers.size(); i++) {
      data = data + this.layerOrder.get(i) + " ";
      data = data + this.layers.get(this.layerOrder.get(i)).getFilterName();
      data = data + this.layers.get(this.layerOrder.get(i)).getLayerDataText();
      data = data + "\n...\n";
    }

    try {
      FileWriter myWriter = new FileWriter(fileName);
      myWriter.write(data);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }


  @Override
  public void load(StringBuilder data, String fileName) throws IllegalArgumentException {

    double r;
    double g;
    double b;
    if ((data == null)) {
      throw new IllegalArgumentException("data cannot be null");
    }

    Scanner sc = new Scanner(data.toString());

    String token = sc.next();
    if (token.contains(".txt")) {
      System.out.println("TXT");
      this.width = sc.nextInt();
      this.height = sc.nextInt();
      this.setHeightAndWidth(this.height, this.width);
      // this should be max val, don't need it but need to pass it by
      sc.nextInt();

      layers.clear();
      layerOrder.clear();

      String[][] rgbs = new String[this.height][this.width];
      try {
        while (true) {
          String lname = sc.next();
          this.addLayer(lname);
          // filter name
          sc.next();
          //this.layers.get(lname).setFilterName(sc.next());
          // dont need to set the filter name to teh layers filter
          // because you cant revert the values to unfiltered
          // so the filtered state is the "normal" state for that layer now
          // and we can furtehr filter it so it shoudl be normal and then can be filtered later on

          // saved in alpha format so thats something
          for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
              r = sc.nextDouble();
              g = sc.nextDouble();
              b = sc.nextDouble();
              sc.nextDouble(); //a
              rgbs[i][j] = "" + r + "," + g + "," + b;

            }
          }


          this.addImageToLayerRGB(lname, rgbs);

          if (!sc.next().equals("...")) {
            System.out.println("why not dot");
          }

        }
      } catch (NoSuchElementException e) {
        // do nothing
      }
    }
  }


  @Override
  public void switchCurrentLayer(String layerName) throws IllegalArgumentException {
    if (!this.layerOrder.contains(layerName)) {
      throw new IllegalArgumentException("there is no layer by that name");
    }
    this.currentLayerName = layerName;
  }


}
