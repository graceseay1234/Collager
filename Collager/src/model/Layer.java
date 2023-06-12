package model;


import java.awt.image.BufferedImage;

/**
 * A layer that contains 1 Image.
 * Images can be added to the Image on the layer(combining the 2 into 1 image)
 * the layer data can be returned as a String or BufferedImage
 * And Filters can be applied to the layer
 * (which are then applied to the image on the layer)
 */
public interface Layer {

  /**
   * add an image to this layer at the given coordinates.
   * @param img the image to add
   * @param x the x coordinate to place the top left corner of the image at.
   * @param y the y coordinate to place the top left corner of the image at.
   */
  public void addImage(Image img, int x, int y);

  /**
   * Add the given type of filter to this layer.
   * @param filterOption the type of filter to add
   */
  public void addFilter(String filterOption) throws IllegalArgumentException;

  /**
   * combine two layers into one for the purposes of saving the project image.
   * @param other the other layer to combine into this layer.
   */
  public void concatLayer(Layer other);

  /**
   * get the text data of this layer in PPM style format.
   * @return the text data
   */
  public String getLayerDataPPM();

  // ADD THIS TO OBSERVER INTERFACE OR SOMETHING

  /**
   * get the image in this layer.
   * @return the image of this layer
   */
  public Image getImage();

  /**
   * get the data of this layer in txt file format.
   * @return the data of this layer
   */
  public String getLayerDataText();

  /**
   * get the name of teh filter option this layer is using.
   * @return the filteroption this layer is using
   */
  public String getFilterName();


  /**
   * Add a filter to the layer that needs the composite image.
   * Options:
   * - difference, inversion blending filter
   * - multiply, darkening blending filter
   * - screen, brightening blending filter
   * @param filterOption type of filter to add
   * @param other the layer with the composite image
   */
  public void addCompositeFilter(String filterOption, Layer other);


  /**
   * get a bufferedImage representing the image on this layer.
   * @return a BufferedImage
   */
  public BufferedImage getBufferedImageOfLayer();




}
