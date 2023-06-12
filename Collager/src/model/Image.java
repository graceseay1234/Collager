package model;


import java.awt.image.BufferedImage;

/**
 * An Image represented as a String[][] of RGB values.
 * This image can be adjusted based on different filters
 * (methods: adjustImage, changeColor, adjustComposite image)
 * this image can be converted to and from HSL values
 * and its information can be returned in different formats,
 * like a String, or BufferedImage.
 */
public interface Image {

  /**
   * Brighten or Darken every pixel in this image.
   * based on the type,
   * max: uses max value of the pixels RGB
   * avg: uses avg value of the pixels RGB
   * luma: uses luma(0.2126R+ 0.7152G +0.0722B) value of the pixels RGB
   * if it is brightening, add the calculated type value to the RGB value of each pixel
   * if it is darkening, subtract the calculated type value from the RGB value of each pixel
   * then make sure the resulting RGB is in bounds of 0-255
   * @param type the type of brightening(max, avg, or luma).
   * @param bright whether it brightens or darkens
   * @param needReset if the image needs to be reset beacsue it already has a filter on it
   */
  public void adjustImage(String type, boolean bright, boolean needReset);


  /**
   * Change the color of every pixel in the image to one of its RGB components.
   * (sets all components not the given color in each pixel to 0)
   * (ex: if "red", all pixels R = its original r-value, G = 0, and B = 0)
   * @param color the color that the image should be filtered to
   * @param needReset if the image needs to be reset to its original state before changing the color
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
   * @return the rgb values of this image
   */
  public String[][] getRgbs();

  /**
   * turn the RGB values of this image into a long string of ints.
   * this is in the same format as a PPM(with "P3, width, height, maxVal" at beginning before RGBs)
   * @return the RGB values of this image as a string
   */
  public String rgbToString();

  /**
   * add an image to this image.
   * by using this image and the given images rgb values
   * to overlay the given image over this image,
   * taking into account alpha values(transparency)
   * @param img the image to be added
   * @param x the x value of its top left corner
   * @param y the y value of its top left corner
   */
  public void addImage(Image img, int x, int y);

  /**
   * change this image into rgb 4-value string.
   * (the fourth value is the alpha value, which is opaque for PPMs)
   * @return this image as a string of 4-value rgb values
   */
  public String rgbToString4Across();


  /**
   * create the array of HSL values from the stored array of RGB values.
   * @param needsReset if the rgb array needs to be reset before converting
   */
  public void setHsl(boolean needsReset);


  /**
   * get the HSL values of this Image.
   * @param reset if the stored HSL values of this image need to be reset
   * @return HSL values
   */
  public String[][] getHSL(boolean reset);

  /**
   * adjust image to a filter based on a given Image.
   * essentially, filter this Image based on the given Image's RGB or HSL values
   * (the composite Image of all Images in layers lower than this Image's layer in the project)
   * supports:
   *  - difference   (inversion blending)
   *  - multiply     (darkening blending)
   *  - screen       (brightening blending)
   * @param type type of filter to add
   * @param needReset if this Image has a filter on it already, needs reset before applying another
   * @param other the composite image to filter with
   */
  public void adjustCompositeImage(String type, boolean needReset, Image other);


  /**
   * create a bufferedImage from these RGB values.
   * (create a bufferedImage to represent this Image)
   * @return a BufferedImage.
   */
  public BufferedImage rgbToBufferedImage();


  /**
   * get the alpha values of this image.
   * @return the alpha values of this image
   */
  public Double[][] getAlphas();


}
