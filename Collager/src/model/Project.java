package model;

import java.util.List;

/**
 * Collager Project Interface.
 * A project has layers, which have images on them
 * Filters can be applied to specific layers in a project
 * Projects can be saved as Images(currently in PPM, PNG, or JPEG format)
 * Project can also be saved as TXTs and then be loaded up again
 */

public interface Project {

  /**
   * Add a new layer with the given name to the top of the project.
   * the new layer starts as a default layer, white background with normal filter.
   * @param layerName The name of the layer to be added
   * @throws IllegalArgumentException if there is already a layer by that name in the project
   */
  public void addLayer(String layerName) throws IllegalArgumentException ;


  /**
   * add the given image to the given layer such that top left corner of the image is at (x,y).
   * @param layerName the name of the layer to place it on
   * @param imageName the given image to add to the layer
   * @param x the x position of the top left corner of the Image
   * @param y the y position of the top left corner of the Image
   * @throws IllegalArgumentException if the layer that is being added to does not exist
   */
  public void addImageToLayer(String layerName, String imageName, int x, int y)
          throws IllegalArgumentException;



  /**
   * Set the filter of the given layer to a specific option.
   * - normal : does nothing to the image
   * - red-component: only uses the red portion of the RGB.
   * Similar for green-component and blue-component
   * - brighten-value: adds the brightness value pixel by pixel
   * according to value from the corresponding pixel on the current layer .
   * Similar for brighten-intensity and brighten-luma. Only affects the current layer.
   * - darken-value: when applied, removes the brightness value pixel by pixel
   * according to value from the corresponding pixel on the current layer .
   * Similar for darken-intensity and darken-luma. Only affects the current layer.
   * @param layerName the name of the Layer to apply the filter to
   * @param filterOption the type of filter to apply to the layer
   * @throws IllegalArgumentException if the layer that the filter is being added to does not exist
   */
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException;


  /**
   * Save the result of applying all filters on the image.
   * @param fileName the name of the file to save it as
   */
  public void saveImage(String fileName);


  /**
   * save the project as a .txt file.
   * In the format:
   * PathToThisFile
   * width height
   * max-value
   * layer-name filter-name
   * LAYER-CONTENT-FORMAT
   * ...
   * layer-name filter-name
   * LAYER-CONTENT-FORMAT
   * ...
   * @param fileName the path of the file to save it to
   */
  public void saveProject(String fileName);

  /**
   * add an image to a layer based just on the Image's RGB values.
   * @param layerName the name of the layer to add the image to
   * @param rgb the rgb values of the image within the layer
   * @throws IllegalArgumentException if the layer that is being added to does not exist
   */
  public void addImageToLayerRGB(String layerName, String[][] rgb) throws IllegalArgumentException;


  /**
   * load a new project from a Stringbuilder of data from a txt file.
   * @param data the stringBuilder
   * @param filename the file path of the project to be loaded
   * @throws IllegalArgumentException if the data is null
   */
  public void load(StringBuilder data, String filename) throws IllegalArgumentException;


  /**
   * Set the filter to one that requires the underlying image.
   * (the underlying image being all the images on the layers under the layer called layerName)
   * @param layerName the name of the layer to set the filter of
   * @param filterOption the filter to set it to
   * @throws IllegalArgumentException if the layer that the filter is being added to does not exist
   */
  public void setCompositeFilter(String layerName, String filterOption)
          throws IllegalArgumentException;


  /**
   * get the height of this project.
   * @return the height
   */
  public int getHeight();

  /**
   * get the width of this project.
   * @return the width
   */
  public int getWidth();

  /**
   * get the names of layers in this project.
   * if empty this returns an empty list.
   * @return names of all the layers in this project in order.
   */
  public List<String> getLayerNames();

  /**
   * get the name of the current layer.
   * @return the name of the current layer
   */
  public String getCurrentLayerName();

  /**
   * get the composite image of all the layers.
   * @return the composite image
   */
  public Image getCompositeImage();


  /**
   * set the height and width of this project.
   * also clear the layer and layerOrder fields
   * basically reset this project to a new project
   * with the given height and width
   * @param h height
   * @param w width
   * @throws IllegalArgumentException if h or w are 0 or less
   */
  public void setHeightAndWidth(int h, int w) throws IllegalArgumentException;

  /**
   * switch the current layer to the given layer name.
   * @param layerName the layer to switch the current layer to
   * @throws IllegalArgumentException if the given layerName is not a layer in the project.
   */
  public void switchCurrentLayer(String layerName) throws IllegalArgumentException;

}
