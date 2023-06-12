package model;


import java.awt.image.BufferedImage;

/**
 * An Image(Representation of a PPM as of A4).
 */
public interface Image {

  /**
   * Brighten every pixel in this image.
   * based on the type of brighetning,
   * max: uses max value of the pixels RGB
   * avg: uses avg value of the pixels RGB
   * luma: uses luma(0.2126R+ 0.7152G +0.0722B) value of the pixels RGB
   * @param type the type of brightening(max, avg, or luma).
   * @param bright whether it brightens or darkens
   * @param needReset if the image needs to be reset beacsue it already has a filter on it
   */
  public void adjustImage(String type, boolean bright, boolean needReset);


  /**
   * Change the color of every pixel in the image to one of its RGB components.
   * (sets all components not the given color in each pixel to 0)
   * @param color the color that the image shoudl be filtered to
   */
  public void changeColor(String color, boolean needReset);


  /**
   * get the height of this image.
   * @return the height
   */
  public int getHeight();

  /**
   * get the width of this image.
   * @return the width
   */
  public int getWidth();

  /**
   * get the Rgb values of this image in String[][] format.
   * @return teh rgb values of this image
   */
  public String[][] getRgbs();

  /**
   * turn the RGB values of this image into a long string of ints.
   * @return teh RGB values of this image as a string
   */
  public String rgbToString();

  /**
   * add an image to this image.
   * (usually used for base white images of layers, and adding images onto those)
   * @param img the image to be added
   * @param x the x value of its top left corner
   * @param y the y value of its top left corner
   */
  public void addImage(Image img, int x, int y);

  /**
   * change this image into rgb 4-value string.
   * (the fourth value is the alpha vlaue, which is currently opaque for PPMs)
   * @return this image as a string of 4-value rgb values
   */
  public String rgbToString4Across();


  /**
   * create the array of HSL values from the stored array of RGB values.
   * @param needsReset if the rgb array needs to be reset before converting
   */
  public void setHsl(boolean needsReset);


  /**
   * get hsl values.
   * @param reset if teh HSL needs to be reset
   * @return hsl values
   */
  public String[][] getHSL(boolean reset);

  /**
   * adjust image to a filter based on the composite image of the project.
   * @param type type of filter to add
   * @param needReset if it had a filter on it already and needs to reset
   * @param other the composite image
   */
  public void adjustCompositeImage(String type, boolean needReset, Image other);




  public BufferedImage rgbToBufferedImage();

}
