package model;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.awt.image.ColorModel;

import javax.imageio.ImageIO;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * implementation of ImageInterface.
 * - an image representation based on
 * a String[][] of RGB values
 * - an image can be created as a blank white image,
 * or read in from a PPM, JPEG, or PNG file.
 * - the original image created is stored in the builder
 * and can be refreshed
 * (it will go back to its initial state from filters(adjusting the image),
 * but not from adding other images to this image)
 */
public class ImageImpl implements Image {
  private int height;
  private int width;

  private String[][] rgbs;

  private String[][] hsls;

  private Double[][] alphas;


  private StringBuilder builder;


  /**
   * Default constructor, creates a blank white image.
   * @param width  width of the image (in pixels)
   * @param height height of the image (in pixels)
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageImpl(int width, int height) throws IllegalArgumentException {
    if ((width <= 0) || (height <= 0)) {
      throw new IllegalArgumentException("width and/or height of an image cannot be 0 or less");
    }
    this.width = width;
    this.height = height;
    this.rgbs = new String[height][width];
    this.hsls = new String[height][width];
    this.alphas = new Double[height][width];


    // set RGBs to all white
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        this.rgbs[i][j] = "" + 255 + "," + 255 + "," + 255;
        this.alphas[i][j] = 255.0;
        //System.out.println("" + i + " " + j + "\n");
      }
    }
    this.builder = new StringBuilder(this.rgbToString());
  }

  /**
   * construct an image from RGB values.
   * @param rgb    rgb values
   * @param width  width
   * @param height height
   * @throws IllegalArgumentException if any parameters are null or 0 or negative
   */
  public ImageImpl(String[][] rgb, int width, int height) throws IllegalArgumentException {
    if ((width <= 0) || (height <= 0) || (rgb == null)) {
      throw new IllegalArgumentException("width and/or height of an image cannot be 0 or less");
    }
    if (rgb.length != width) {
      throw new IllegalArgumentException("rgb array size must match width");
    }
    this.width = width;
    this.height = height;
    this.rgbs = rgb;
    this.hsls = new String[height][width];
    this.builder = new StringBuilder(this.rgbToString());
    this.alphas = new Double[height][width];
  }


  /**
   * Image constructor from a PPM, JPEG, or PNG File.
   * @param filename the name of the file the image being constructed is in
   * @throws IllegalArgumentException if any of the arguments are null
   */
  public ImageImpl(String filename) throws IllegalArgumentException {
    if ((filename == null) || (filename.equals(""))) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    if ( (!filename.contains(".ppm"))
            && (!filename.contains(".jpeg"))
            && (!filename.contains(".png"))) {
      // if not one of the supported file types
      throw new IllegalArgumentException("File must be a ppm, jpeg, or png");
    }

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File cannot be found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    try {
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }
      this.builder = builder;
      sc = new Scanner(builder.toString());

      String token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
      } else {
        this.width = sc.nextInt();
        this.height = sc.nextInt();
        this.rgbs = new String[this.height][this.width];
        this.hsls = new String[height][width];
        this.alphas = new Double[height][width];
        // this shoudl be max val, don't need it but need to pass it by
        sc.nextInt();
      }

      // transfer the builder to an array of RGB values represent as CSV strings
      this.builderToRGBArray(sc);


    } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
      // means is not ppm file
      System.out.println("NOT PPM");
      BufferedImage buffered;
      try {
        //buffered = ImageIO.read(ImageIO.createImageInputStream(new File(filename))) ;
        buffered = ImageIO.read(new File(filename));
        this.height = buffered.getHeight();
        this.width = buffered.getWidth();
        this.rgbs = new String[this.height][this.width];
        this.hsls = new String[height][width];
        this.alphas = new Double[height][width];
        Color c;

        for (int h = 0; h < this.height; h++) {
          for (int w = 0; w < this.width; w++) {
            c = new Color(buffered.getRGB(w, h), true);
            double r = c.getRed();
            double g = c.getGreen();
            double b = c.getBlue();
            double a = c.getAlpha();
            rgbs[h][w] = "" + r + "," + g + "," + b;
            this.alphas[h][w] = a;
          }
        }
      } catch (IOException ex) {
        // do nothing
      }

    }


  }


  /**
   * Image constructor from a PPM, JPEG, or PNG file.
   * with a pre-set height and width
   * so the image dimensions match the project and
   * layer dimensions
   * @param filename the name of the file path
   * @param height the height of the projecy
   * @param width the width of the project
   * @throws IllegalArgumentException if any parameters are null or less than 0
   */
  public ImageImpl(String filename, int height, int width) throws IllegalArgumentException {
    if ((filename == null) || (filename.equals(""))) {
      throw new IllegalArgumentException("Filename cannot be null");
    }
    if ( (!filename.contains(".ppm"))
            && (!filename.contains(".jpeg"))
            && (!filename.contains(".png"))) {
      // if not one of the supported file types
      throw new IllegalArgumentException("File must be a ppm, jpeg, or png");
    }
    if ((width <= 0) || (height <= 0)) {
      throw new IllegalArgumentException("width and/or height of an imagecannot be 0 or less");
    }
    this.width = width;
    this.height = height;
    this.rgbs = new String[this.height][this.width];
    this.hsls = new String[height][width];
    this.alphas = new Double[height][width];

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File cannot be found");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    try {
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }
      this.builder = builder;
      sc = new Scanner(builder.toString());

      String token = sc.next();
      if (!token.equals("P3")) {
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
      } else {
        sc.nextInt(); /// image height
        sc.nextInt(); // image width
        // this shoudl be max val, don't need it but need to pass it by
        sc.nextInt();
      }

      // transfer the builder to an array of RGB values represent as CSV strings
      this.builderToRGBArray(sc);


    } catch (StringIndexOutOfBoundsException |
             IllegalArgumentException e) {
      System.out.println(e.getMessage());
      // means is not ppm file
      System.out.println("NOT PPM");
      BufferedImage buffered;
      try {
        //buffered = ImageIO.read(ImageIO.createImageInputStream(new File(filename))) ;
        buffered = ImageIO.read(new File(filename));
        Color c;
        double r;
        double g;
        double b;
        double a;
        for (int h = 0; h < this.height; h++) {
          for (int w = 0; w < this.width; w++) {
            try {
              c = new Color(buffered.getRGB(w, h), true);
              //System.out.println("COLOR" + c.toString());
              r = c.getRed();
              g = c.getGreen();
              b = c.getBlue();
              a = c.getAlpha();
            } catch (ArrayIndexOutOfBoundsException exx) {
              // if image is smaller than project size fill rest in with white
              r = 255;
              g = 255;
              b = 255;
              a = 255;
            }
            rgbs[h][w] = "" + r + "," + g + "," + b;
            alphas[h][w] = a;
          }
        }
      } catch (IOException ex) {
        // do nothing
      }
    }

  }


  /**
   * Transfer a PPM represented in a Scanner.
   * (of a StringBuilder for ex) to an RGB Array.
   * StringBuilder that holds the PPM image
   * (w width and height and stuff already taken out)
   * @param sc a scanner of a StringBuilder that holds a PPM-formatted Image
   */
  private void builderToRGBArray(Scanner sc) {
    double r;
    double g;
    double b;

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        try {
          r = sc.nextDouble();
          g = sc.nextDouble();
          b = sc.nextDouble();
        } catch (NoSuchElementException e) {
          r = 255;
          g = 255;
          b = 255;
        }
        this.rgbs[i][j] = "" + r + "," + g + "," + b;
        // THIS WILL THROW AN EXCEPTION IF TEH IMAGE HEIGHT IS LESS THAN TEH PROJECT DIMENSIONS,
        // TRY CATCH SOMETHING FOR THIS AND FILL REST IN WITH WHITE PIXELS
      }
    }
  }

  /**
   * Reset RGB array to what it was from the original Image.
   * @throws IllegalStateException if the builder is not in PPM format
   */
  private void resetRGBStuff() throws IllegalStateException {
    Scanner sc = new Scanner(this.builder.toString());

    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("builder should be in PPM format");
    } else {
      // pass by width height and next val
      sc.nextInt();
      sc.nextInt();
      sc.nextInt();
    }
    this.rgbs = new String[this.height][this.width];
    // transfer the builder to an array of RGB values represent as CSV strings
    this.builderToRGBArray(sc);
  }


  @Override
  public void adjustImage(String type, boolean bright, boolean needReset) {
    if (needReset) {
      this.resetRGBStuff();
    }

    if (!type.equals("normal")) {
      String[] rgb;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          rgb = this.rgbs[i][j].split(",");
          this.rgbs[i][j] = this.adjustBrightDark(
                  Double.parseDouble(rgb[0]),
                  Double.parseDouble(rgb[1]),
                  Double.parseDouble(rgb[2]),
                  bright,
                  type);
        }
      }
    }


  }

  /**
   * Adjust the RGB value of a single pixel.
   * based on whether it is meant to be brightened or darkened w the max, avg, or luma value.
   * max : maximum of r, g, b
   * avg = (r + g + b ) / 3
   * luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
   * @param r      red RGB value
   * @param g      green RGB value
   * @param b      blue RGB value
   * @param bright whether it shoudl be brightened(true) or darkened(false)
   * @param type   what val to add to the RGB, the max, avg, or luma
   * @return CSV String of the RGB value
   * @throws IllegalArgumentException if type is not "max", "avg" or "luma"
   */
  private String adjustBrightDark(double r, double g, double b, boolean bright, String type)
          throws IllegalArgumentException {
    double add = 0;
    switch (type) {
      case "max":
        add = Math.max(r, Math.max(g, b));
        break;
      case "avg":
        add = (r + g + b) / 3;
        break;
      case "luma":
        add = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        break;
      default:
        throw new IllegalArgumentException("needs to be max, avg, or luma");

    }
    if (!bright) {
      add = add * -1;
    }
    r = this.makeWithinBounds(r, add);
    g = this.makeWithinBounds(g, add);
    b = this.makeWithinBounds(b, add);
    return "" + r + "," + g + "," + b;
  }

  /**
   * Make sure n + add is within bounds of 0 and 255.
   * (as those are the RGB value bounds)
   * if it exceeds(or is less than in the case of 0)
   * either bound then return the bound (0 or 255)
   * @param n   the number you are putting within bounds.
   * @param add the number to add to the number
   * @return the sum of n + add, or the bound it exceeds
   */
  private double makeWithinBounds(double n, double add) {
    if ((n + add) > 255) {
      return 255;
    }
    if ((n + add) < 0) {
      return 0;
    }
    return n + add;
  }


  @Override
  public void changeColor(String color, boolean needReset) {
    if (needReset) {
      this.resetRGBStuff();
    }
    String[] rgb;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb = this.rgbs[i][j].split(",");
        this.rgbs[i][j] = this.adjustColor(
                Double.parseDouble(rgb[0]),
                Double.parseDouble(rgb[1]),
                Double.parseDouble(rgb[2]),
                color);
      }
    }
  }


  /**
   * Adjust the color of a pixel to be r, g, or b.
   * if r, makes g and b vals 0, if b makes r and g vals 0, etc
   * @param r     red value
   * @param g     green val
   * @param b     blue val
   * @param color what color to adjust it to
   * @return CSV String of the RGB value
   * @throws IllegalArgumentException if the color is not "red", "green", or "blue"
   */
  private String adjustColor(double r, double g, double b, String color)
          throws IllegalArgumentException {
    switch (color) {
      case "red":
        return r + "," + 0 + "," + 0;
      case "green":
        return 0 + "," + g + "," + 0;
      case "blue":
        return 0 + "," + 0 + "," + b;
      default:
        throw new IllegalArgumentException("the color it is changed to must be red, green or blue");
    }
  }


  // add observers
  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public String[][] getRgbs() {
    return this.rgbs;
  }


  @Override
  public String rgbToString() {
    String[] rgb;
    String s = "P3\n" + this.width + "\n" + this.height + "\n" + "255\n";
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb = this.rgbs[i][j].split(",");
        s = s + rgb[0] + "\n" + rgb[1] + "\n" + rgb[2] + "\n";
      }
    }
    return s;
  }

  @Override
  public String rgbToString4Across() {
    String[] rgb;
    String s = "";
    double a;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb = this.rgbs[i][j].split(",");
        try {
          a = this.alphas[i][j];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
          a = 255;
        }
        s = s + "\n" + rgb[0] + " " + rgb[1] + " " + rgb[2] + " " + a;
      }
    }
    return s;
  }


  @Override
  public BufferedImage rgbToBufferedImage() {
    BufferedImage buffered = new BufferedImage(this.width, this.height, TYPE_INT_RGB);
    float r;
    float g;
    float b;
    String[] rgb;
    String s = "";
    float[] components = new float[4];
    for (int h = 0; h < this.height; h++) {
      for (int w = 0; w < this.width; w++) {
        rgb = this.rgbs[h][w].split(",");
        r = Float.parseFloat(rgb[0]) / 255;
        g = Float.parseFloat(rgb[1]) / 255;
        b = Float.parseFloat(rgb[2]) / 255;
        components[0] = r;
        components[1] = g;
        components[2] = b;
        components[3] = 1;

        try {
          buffered.setRGB(w, h, ColorModel.getRGBdefault().
                  getDataElement(components, 0));
        } catch (ArrayIndexOutOfBoundsException exx) {
          // do nothing
        }
      }
    }
    return buffered;
  }


  @Override
  public void setHsl(boolean needsReset) {
    RepresentationConverter rc = new RepresentationConverter();
    if (needsReset) {
      this.resetRGBStuff();
    }
    String[] rgb;
    double r;
    double g;
    double b;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        rgb = this.rgbs[i][j].split(",");
        r = Double.parseDouble(rgb[0]) / 255;
        g = Double.parseDouble(rgb[1]) / 255;
        b = Double.parseDouble(rgb[2]) / 255;
        this.hsls[i][j] = rc.convertRGBtoHSL(r, g, b);
      }
    }
  }

  @Override
  public String[][] getHSL(boolean reset) {
    if (reset) {
      this.setHsl(true);
    }
    return this.hsls;
  }

  /**
   * set the RGB array of this image to new values.
   * based on the HSL array
   */
  private void hslToSetRGB() {
    RepresentationConverter rc = new RepresentationConverter();
    String[] hsl;
    double h;
    double s;
    double l;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        hsl = this.hsls[i][j].split(",");
        h = Double.parseDouble(hsl[0]);
        s = Double.parseDouble(hsl[1]);
        l = Double.parseDouble(hsl[2]);
        this.rgbs[i][j] = rc.convertHSLtoRGB(h, s, l);
      }
    }
  }


  // difference
  // inversion blending, 2 pixels rgbs and subtracts them,
  // 1 on current layer and the 2 is the rgb of the composite below
  // change this layers rgb to be the difference (before setting alpha)


  // darkening blending filter (mulriply)
  // uses lightness vals of image below, get from HSL vals of below stuff
  // dakrneing and brighting requie HSL, also must be btwn 0-1
  // use HSL of composite image, current layer's HSL
  // mulriply currentl layers lightness by composite layers
  // lightness and set that as current vals new lightness
  // dont chnage hue or saturation
  // use lightness vals of compsoite imgae (dl) and curent layer (l)
  // l = dl * l
  // h = h, s = s


  // brighetning blending filter(screen)
  // use lightness vals of compsoite imgae (dl) and curent layer (l)
  // l = (1 - ((1 - l) * (1 - dl)))
  // h = h, s = s

  @Override
  public void adjustCompositeImage(String type, boolean needReset, Image other) {
    if (needReset) {
      this.resetRGBStuff();
    }
    double r;
    double g;
    double b;
    double dr;
    double dg;
    double db;
    double h;
    double s;
    double l;
    double dl;
    String[] rgb;
    String[] drgb;
    String[] hsl;
    String[] dhsl;

    if (type.equals("difference")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          //System.out.println("AT " + i + j + "\n");
          rgb = this.rgbs[i][j].split(",");
          drgb = other.getRgbs()[i][j].split(",");
          this.rgbs[i][j] = this.applyDifference(
                  Double.parseDouble(rgb[0]),
                  Double.parseDouble(rgb[1]),
                  Double.parseDouble(rgb[2]),
                  Double.parseDouble(drgb[0]),
                  Double.parseDouble(drgb[1]),
                  Double.parseDouble(drgb[2]));
        }
      }
    } else {
      this.setHsl(false);
      other.setHsl(false);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          hsl = this.hsls[i][j].split(",");
          dhsl = other.getHSL(false)[i][j].split(",");
          this.hsls[i][j] = this.differentLights(type,
                  Double.parseDouble(hsl[0]),
                  Double.parseDouble(hsl[1]),
                  Double.parseDouble(hsl[2]),
                  Double.parseDouble(dhsl[2])); // check if this is DL
        }
      }
      this.hslToSetRGB();

    }


  }


  /**
   * change the lightening of the image based on the composite image's light value(dl).
   * if it is multiply filter, l = dl * l
   * if it is screen filter, l = (1 - ((1 - l) * (1 - dl)))
   * @param type type of filter
   * @param h    hue
   * @param s    saturation
   * @param l    lightness
   * @param dl   lightness of composite image
   * @return new HSL value based on the filter type
   */
  private String differentLights(String type, double h, double s, double l, double dl) {
    if (type.equals("multiply")) {
      l = dl * l;
    }
    if (type.equals("screen")) {
      l = (1 - ((1 - l) * (1 - dl)));
    }
    if (l > 1) {
      l = 1;
    }
    if (l < 0) {
      l = 0;
    }
    return "" + h + "," + s + "," + l;
  }


  /**
   * apply the difference filter.
   * r = |r-dr|, etc
   * @param r  red
   * @param g  green
   * @param b  blue
   * @param dr composite red
   * @param dg composite green
   * @param db composite blue
   * @return teh new rgb values
   */
  private String applyDifference(double r, double g, double b,
                                 double dr, double dg, double db) {
    //System.out.println("" + r + g + b + dr + dg + db );
    r = Math.abs(r - dr);
    g = Math.abs(g - dg);
    b = Math.abs(b - db);

    r = this.makeWithinBounds(r, 0);
    g = this.makeWithinBounds(g, 0);
    b = this.makeWithinBounds(b, 0);

    return "" + r + "," + g + "," + b;
  }












  @Override
  public void addImage(Image img, int x, int y) {
    // if x or y is greater than width ro height of this, decrease them
    if (x > this.height) {
      x = this.height;
    }
    if (y > this.width) {
      y = this.width;
    }
    int xneg = 0;
    int yneg = 0;
    if (x < 0) {
      xneg = x;
      x = 0;
    }
    if (y < 0) {
      yneg = y;
      y = 0;
    }

    int rightx = x + img.getHeight();
    int righty = y + img.getWidth();
    if (rightx > this.height) {
      rightx = this.height;
    }
    if (righty > this.width) {
      righty = this.width;
    }
    double r;
    double g;
    double b;
    double a;
    double dr;
    double dg;
    double db;
    double da;
    //System.out.println("x = " + x + " y = " + y);
    //System.out.println("rightx = " + rightx + " righty = " + righty);
    // add the left corner
    // change RGB values between the x and y coordinates
    for (int i = x; i < rightx; i++) {
      for (int j = y; j < righty; j++) {
        try {
          // attempt to get the alpha value for this position
          a = img.getAlphas()[i - x][j - y];
          //System.out.println("ALPHA = " + a);
          if (a == 255) {
            throw new ArrayIndexOutOfBoundsException("skip");
          }
          // reformat as a 3-component representation to store
          // r' = r(a/255) + dr(da/255)(1-(a/255))(1/a'')
          // da = 255
          // a'' = (a/255) +(da/255)(1-(a/255))
          // a'' = (a/255) + (1)(1-(a/255))
          // a'' = (a/255) + (1-(a/255))
          // a'' = x + 1 - x
          // a'' = 1
          // r' = r(a/255) + dr(255/255)(1-(a/255))(1)
          // r' = r(a/255) + dr(1)(1-(a/255))
          // r' = r(a/255) + dr(1-(a/255))
          String[] drgb = this.getRgbs()[i][j].split(",");
          if (xneg != 0) {
            i = i + (xneg * -1);
          }
          if (yneg != 0) {
            j = j + (yneg * -1);
          }
          String[] rgb = img.getRgbs()[i - x][j - y].split(",");
          r = Double.parseDouble(rgb[0]);
          g = Double.parseDouble(rgb[1]);
          b = Double.parseDouble(rgb[2]);
          dr = Double.parseDouble(drgb[0]);
          dg = Double.parseDouble(drgb[1]);
          db = Double.parseDouble(drgb[2]);
          da = 255; // bottom image doesnt matter transparency
          r = (r * (a / 255)) + (dr * (1 - (a / 255)));
          g = (g * (a / 255)) + (dg * (1 - (a / 255)));
          b = (r * (a / 255)) + (db * (1 - (a / 255)));

          this.rgbs[i][j] = "" + r + "," + g + "," + b;
          //System.out.println(this.rgbs[i][j]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
          // if the alpha value for this pixel wasn't instantiated (ppm for ex)
          //System.out.println("NO ALPHA");
          if (xneg != 0) {
            i = i + (xneg * -1);
          }
          if (yneg != 0) {
            j = j + (yneg * -1);
          }
          this.rgbs[i][j] = img.getRgbs()[i - x][j - y];
        }
      }
    }

    // reset builder for when resetting the filters image doesnt disapeer
    this.builder = new StringBuilder(this.rgbToString());
  }


  @Override
  public Double[][] getAlphas() {
    return this.alphas;
  }

}
