package model;


import java.awt.image.BufferedImage;

/**
 * Implementation of layer.
 * - contains one Image
 * - filters can be applied to the image on this layer
 * Currently supported filters:
 * - normal :             does nothing to the image
 * - red-component:       only uses the red portion of the RGB.
 * - green-component:     only uses the green portion of the RGB.
 * - blue-component:      only uses the blue portion of the RGB.
 * - brighten-value:      adds the brightness value pixel by pixel
 * according to value from the corresponding pixel on the current layer
 * based on the max RGB value.
 * - brighten-intensity:  similar to brighten value but uses average value from each RGB
 * - brighten-luma:       similar to brighten value but uses luma formula to calculate the value
 * - darken-value:        when applied, removes the brightness value pixel by pixel
 * according to value from the corresponding pixel on the current layer .
 * - darken-intensity:    similar to darken value but uses average value from each RGB
 * - darken-luma:         similar to darken value but uses luma formula to calculate the value
 * - difference:          inversion blending filter
 * - multiply:            darkening blending filter
 * - screen:              brightening blending filter
 */
public class LayerImpl implements Layer {
  private final int height;
  private final int width;

  private String filter;
  private final Image img;

  /**
   * Create a Layer.
   * the given image must be the same height and width as the layer
   * the given filter is immediately applied to the image
   * @param h      height of layer (pixels)
   * @param w      width of layer (pixels)
   * @param filter filter applied to layer
   * @param img    image on layer
   * @throws IllegalArgumentException if any input parameters are 0 or less or null
   */
  public LayerImpl(int h, int w, String filter, Image img) throws IllegalArgumentException {
    if ((h <= 0) || (w <= 0) || (filter == null) || (img == null)) {
      throw new IllegalArgumentException("construction parameters for a Layer cannot be 0 or null");
    }
    if ((img.getHeight() != h) || (img.getWidth() != w)) {
      throw new IllegalArgumentException("provided image must have same dimensions as layer, " +
              "try to make a blank layer and add the image after.");
    }
    this.height = h;
    this.width = w;
    this.filter = filter;
    this.img = img;
    this.addFilter(filter);
  }

  /**
   * Default constructor for Layer.
   * makes a completely white image with a normal filter.
   * @param h height of layer (pixels)
   * @param w width of layer (pixels)
   * @throws IllegalArgumentException if any input parameters are 0 or less
   */
  public LayerImpl(int h, int w) throws IllegalArgumentException {
    if ((h <= 0) || (w <= 0)) {
      throw new IllegalArgumentException("Height and/or width of a Layer cannot be 0");
    }
    this.height = h;
    this.width = w;
    this.filter = "normal";


    // white image default
    this.img = new ImageImpl(w, h);
  }


  @Override
  public void addImage(Image img, int x, int y) {
    String oldFilter = this.filter;
    this.addFilter("normal");
    this.img.addImage(img, x, y);
    this.addFilter(oldFilter);
  }

  @Override
  public Image getImage() {
    return this.img;
  }

  @Override
  public void concatLayer(Layer other) {
    this.addImage(other.getImage(), 0, 0);
  }

  @Override
  public void addFilter(String filterOption) throws IllegalArgumentException {
    // if already had a filter on
    // need to remove previous filter (go back to default state of image
    // and overwrite with new filter
    // so reset would be true
    boolean reset = false;
    if (!this.filter.equals("normal")) {
      reset = true;
    }

    switch (filterOption) {
      case "normal":
        this.img.adjustImage("normal", false, reset);
        break;
      case "red-component":
        this.img.changeColor("red", reset);
        break;
      case "green-component":
        this.img.changeColor("green", reset);
        break;
      case "blue-component":
        this.img.changeColor("blue", reset);
        break;
      case "brighten-value":
        this.img.adjustImage("max", true, reset);
        break;
      case "brighten-intensity":
        this.img.adjustImage("avg", true, reset);
        break;
      case "brighten-luma":
        this.img.adjustImage("luma", true, reset);
        break;
      case "darken-value":
        this.img.adjustImage("max", false, reset);
        break;
      case "darken-intensity":
        this.img.adjustImage("avg", false, reset);
        break;
      case "darken-luma":
        this.img.adjustImage("luma", false, reset);
        break;
      default:
        throw new IllegalArgumentException("Unsupported filter option");
    }
    this.filter = filterOption;

  }


  @Override
  public String getLayerDataPPM() {
    return this.img.rgbToString();
  }

  @Override
  public String getLayerDataText() {
    return this.img.rgbToString4Across();
  }

  @Override
  public String getFilterName() {
    return this.filter;
  }


  @Override
  public void addCompositeFilter(String filterOption, Layer other) {
    if ((!filterOption.equals("difference"))
            && (!filterOption.equals("multiply"))
            && (!filterOption.equals("screen"))) {
      // redirect in case it is a regular filter
      // it throws an exception anyway if it is not a real filter there
      this.addFilter(filterOption);
    }
    boolean reset = false;
    if (!this.filter.equals("normal")) {
      reset = true;
    }
    this.img.adjustCompositeImage(filterOption, reset, other.getImage());

    this.filter = filterOption;
  }


  @Override
  public BufferedImage getBufferedImageOfLayer() {
    return this.img.rgbToBufferedImage();
  }


}
