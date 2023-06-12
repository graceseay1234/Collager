Eve Mooney
Addison Seay

Collager

Our program is a user-friendly tool that allows you to create projects of any size with multiple layers.
You can import images from your computer and add them to your desired layer.
The program provides a range of filters that you can apply to your images to enhance their appearance.
Your filter choices include the following:
    Altering the colors balance:
        red-component
        blue-component
        green-component

    Altering the brightness:
        brighten-luma
        darken-luma
        brighten-intensity
        darken-intensity
        brighten-value
        darken value

    Altering the image based on the image below it:
        difference
        multiply
        screen

With this program, you can create beautiful images with multiple layers and apply a
variety of filters to make them stand out. When done, you can save these images
to your computer or save the entire project to come back to later.

When this program is run with the following command:
    java -jar Collager.jar
You will be prompted with an interactive window, allowing you to do everything
mentioned above with the ease of buttons and a text box.
Using this interactive window, you will also be give the option to switch between
layers you wish to edit, the name of each layer showcased to the right of the screen, and the
name of the layer being edited highlighted in cyan.
Incredibly, this window also allows you to see your project, layers, and images
in real time as they are being edited!

If you wish to run this program using only text based commands, you may run the following:
    java -jar Collager.jar -text
You will be prompted with a list of possible commands and any information you may be required to provide.
    The potential commands are as follows:
  ┌────────────────────────────────────────────────────────────────────────────┐
          new-project                [height] [width]
          add-layer                  [layerName]
         load-project                [filePath]
       add-image-to-layer            [layerName] [imagePath] [offset] [offset]
          set-filter                 [layerName] [filterOption]
          save-image                 [imagePath]
             quit
  └─────────────────────────────────────────────────────────────────────────────┘
This version of the program will require you to enter the name of the layer
you wish to edit with many of the commands, rather than allowing you to simply
switch to the layer you wish to edit for the time being.
In addition, you will not be able to see the project, layers, or images as you edit them.

In order to properly run this program, you must have the following:
    Java 11 or higher JRE
    JUnit 4 for running the tests


For further information on how to use this program, look for the USEME text file
located within this zip file. The USEME file will provide you with the necessary information
to run this program and utilize all the features it has to offer.



.　 . • ☆ . ° .• °:. *₊ ° . ☆.　 . • ☆ . ° .• °:. *₊ ° . ☆.　 . • ☆ . ° .• °:. *₊ ° . ☆.　 . • ☆ . ° .• °:. *₊ ° . ☆.　





Collager


.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜
[MODEL]
.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜
[CLASSES]

** Class: ImageImpl
Purpose: This class is an implementation of the Image interface. It allows us to create
    instances of the Image object type and call it to classes such as LayerImpl and ProjectImpl.
    The default constructor takes in a filename, throwing an exception if the name is null or empty.
    This constructor allows us to call the ImageImpl class and retrieve
    an image based upon the user's specific file input.
    Thus, it uses a StringBuilder to retrieve information from the file,
    returning the height, width, max value, and all RGB values that represent the image.

    The other constructor takes in height and width, throwing an exception if either are 0.
    When called, the constructor initiates the height and width as well
    as the RGB values that represent each image in a 2D String array.
    Immediately upon creation, each layer's RBG values are set to all white and transparent.

    This third constructor allows a user to import an image file that may be a PNG or JPEG,
    no longer constraining them to PPMs.
    Given that the image is in PNG or JPEG format, the constructor will read the image file as a
    BufferedImage object using Java's ImageIO library.
    The constructor creates a BufferedImage object and sets the height and width instance variables to the height and
    width of the BufferedImage. It then reads the RGB values of each pixel in the BufferedImage
    and stores them in the rgbs array.
    If the image file is in the PPM format, the constructor reads the file using the Scanner object created earlier.
    The first token in the file should be "P3", which indicates that the file is a plain PPM file.
    If the token is not "P3", an IllegalArgumentException is thrown.
    The constructor then reads the height and width of the image from the file and stores them in the height
    and width instance variables.
    After reading the image file, the constructor sets the rgbs and hsls arrays as the instance variables
    of the ImageImpl object.



**ADDED PART2 (private) Method: adjustColor
Purpose: This method adjusts the color of a given RGB pixel to be primarily red, green,
or blue by setting the other two color components to 0. The method takes in the current values of red, green,
and blue for the pixel, along with a string indicating the desired color (either "red", "green", or "blue").
Depending on the provided color, the method uses a switch statement to determine which color components to set to
0 and which to leave as-is. It then returns the modified RGB values as a comma-separated string in the format "R,G,B".
If an invalid color string is provided (i.e., not "red", "green", or "blue"), the method throws an
IllegalArgumentException with an appropriate error message.

**ADDED PART2 Method: getRgbs
Purpose: This method is part of a class that contains a two-dimensional array of RGB color values.
The method getRgbs() returns this array to the caller. The returned array is a copy of the original array,
so changes made to the returned array will not affect the original array stored in the class.

**ADDED PART3 Method: getAlphas
Purpose: This method returns the alpha values of a given image, ensuring that transparency
of an image is taken into account.

**ADDED PART2 Method: setHsl
Purpose: This method converts the RGB values stored in the rgbs array of the current image object to HSL
(Hue, Saturation, Lightness) values and stores them in the hsls array.
The method starts by creating a new RepresentationConverter object. If the needsReset variable is set to true,
it calls the resetRGBStuff() method to reset any cached RGB calculations.
Then, it loops through each pixel in the image by iterating over the height and width of the image. For each pixel,
it extracts the RGB values from the rgbs array, converts them to decimal values between 0 and 1 by dividing each by 255,
and then calls the convertRGBtoHSL() method of the RepresentationConverter object to convert the RGB values to HSL
values. The resulting HSL value is then stored in the hsls array at the corresponding position.
Overall, this method allows for easy conversion between RGB and HSL representations of colors,
which can be useful for various image processing tasks.

**ADDED PART2 Method: getHsl
Purpose: This method returns a 2D string array representing the HSL values of each pixel in the image.
If the reset parameter is true, it first calls the setHsl method to reset the HSL values based on the current
RGB values of each pixel. The method then returns the HSL array.


**ADDED PART2 Method: hslToSetRGB
Purpose: This method converts the HSL (Hue, Saturation, Lightness) representation of an image stored in a 2D array
hsls to its corresponding RGB (Red, Green, Blue) representation and stores it in the rgbs 2D array.
The method iterates through every pixel of the image (represented by the height and width variables),
parses the HSL values from the hsls array, and uses a RepresentationConverter object to convert them to RGB values.
The resulting RGB values are stored in the rgbs array for each pixel.


**ADDED PART2 Method: rgbToString
Purpose: This method returns a string in the format of a P3 PPM file, which is a common image format.
The first line of the string is "P3", indicating the file type. The second and third lines indicate the width
and height of the image, respectively. The fourth line indicates the maximum value for each color component,
which is 255 for this implementation.
The remaining lines of the string represent the RGB values of each pixel in the image, with each value separated
by a newline character.
The RGB values are retrieved from the this.rgbs array, which stores the RGB values of each pixel.


**ADDED PART2 Method: rgbToString4Across
Purpose: This method is similar to rgbToString(), but adds an alpha component to each pixel.
The alpha component is always set to 255, indicating full opacity. The string is formatted with four values per line,
separated by spaces, with a newline character at the end of each line. This format is often used for printing image
data to the console.

**ADDED PART2 Method: adjustCompositeImage
Purpose: This method adjusts the composite image by combining the pixels of the current image with those of another
image based on the specified blending type.
If the blending type is "difference", it takes the difference between the RGB values of the current image pixel and
the other image pixel and returns the new RGB value for that pixel. This is done for all pixels in the image.
If the blending type is not "difference", it converts the RGB values of the current image and other image to HSL
values and adjusts the lightness of the current image pixel based on the specified blending type and the lightness of
the other image pixel. Then, it converts the adjusted HSL values back to RGB and returns the new RGB value for each
pixel in the image. This is done for all pixels in the image.
If needReset is true, it resets the RGB and HSL values of the current image before adjusting it with the other image.

**ADDED PART2 Method: (private) differentLights
Purpose: This method adjusts the lightness of a pixel in an image based on the lightness of the corresponding pixel in
another image (composite image) and a specified type of filter. The method takes in the hue, saturation, and initial
lightness values of the pixel, as well as the lightness value of the composite image.
If the filter type is "multiply," the lightness value is multiplied by the lightness of the composite image.
If the filter type is "screen," the lightness value is calculated based on a screen blend formula.
Finally, the method returns the updated HSL value as a string. If the updated lightness value is greater than 1,
it is capped at 1, and if it is less than 0, it is capped at 0.

**ADDED PART2 Method: (private) applyDifference
Purpose: This method applies the "difference" blend mode to two colors represented in RGB format.
It takes in two sets of RGB values, representing the color of a pixel in the image being modified ("r", "g", "b")
and the corresponding color in a second image ("dr", "dg", "db"). It then calculates the absolute difference between
each of the color channels (red, green, blue) of the two colors using the Math.abs() method.
The resulting color channels are then made within the 0-255 bounds by calling the makeWithinBounds()
method with a parameter of 0, which ensures that the returned RGB values are valid. Finally, the method returns a
string representation of the new RGB color in the format "r,g,b".

***ADDED PART3 Method: rgbToBufferedImage
Purpose: This method converts the RGB values stored in the instance variable rgbs of the PPM object
into a BufferedImage object.
First, a new BufferedImage is created with the same width and height as the PPM object. The color components of each
pixel in the rgbs 2D array are extracted and converted from string values to float values between 0 and 1.
These float values are then used to create a Color object.
Next, a float array is created with four elements representing the RGBA components of the color.
The first three elements are set to the RGB values of the pixel, and the fourth element (alpha) is set to 1.
The setRGB method of the BufferedImage object is then used to set the pixel at the current coordinates (w, h)
to the corresponding color using the ColorModel.getRGBdefault().getDataElement method.
Any ArrayIndexOutOfBoundsException exceptions that might occur are caught and ignored.
Finally, the resulting BufferedImage object is returned.

(private) Method: builderToRGBArray
Purpose: Transfers a PPM represented in a Scanner (of a StringBuilder for ex) to an RGB Array.
Parameters:sc a scanner of a StringBuilder that holds the PPM image(w height and width and stuff already taken out)

(private) Method: resetRGBStuff
Purpose: Reset RGB array to what it was from original file.

Method: adjustImage
Purpose: This method brightens every pixel in the image based on the type of brightening.
Parameters: type determines which value is being altered, bright determines whether the image
is being brightened or darkened, needReset represents whether or not the image has already
been altered in which case the alteration overrides it.

(private) Method: adjustBrightDark
Purpose:  Adjust the RGB value of a single pixel based on whether it is meant to be brightened or
darkened w the max, avg, or luma value.
Using the following: max : maximum of r, g, b |  avg = (r + g + b ) / 3
| luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
Parameters: r red RGB value, g green RGB value,  b blue RGB value,
bright whether it should be brightened(true) or darkened(false),
type  what val to add to the RGB, the max, avg, or luma, CSV String of the RGB value


(private) Method: makeWithinBounds
Purpose: Make sure n + add is within bounds on 0 and 255. If it exceeds the bounds then return 0 or 255
Parameters: n the number you are putting within bounds, add the number to add to the number,
the sum of n + add, or the bound it exceeds

-------------------------------------------------------------------------------------------------------------------

** Class: LayerImpl
Purpose: This class is an implementation of the Layer interface. It allows us to create
        instances of the Layer object type and call it to classes such as ProjectImpl.
        The default constructor takes in simply a height and width, throwing
        an Illegal Argument Exception if either value is equal to 0, and establishing the height and width
        of the layer as well as setting the filter of the layer to "normal". In addition, once the layer
        is created, it creates an a white transparent image as the background. This image is set as the layer's
        img (the image on the layer).

        The other constructor takes in height and width, this time along with a filter and an Image.
        If either of the height or width values equal 0, or either the filter or image is null,
        an Illegal Argument Exception is thrown. The input height and width determine the height and width
        of the layer. The input filter determines which filter is placed on the layer. The image determines the
        image placed on the layer.

Method: addImage
Purpose:This method adds an image to the layer at the given coordinates.

Method: getImage
Purpose: This method retrieves the image that currently resides within the
constructed layer.

Method: concatLayer
Purpose: Allows us to add an image to another image at given spot. This method
is used to add an image to a layer that already has another image.

Method: addFilter
Purpose: This method add the given type of filter to this layer.

Method: getLayerDataPPM
Purpose: This method retrieves the rgb values of the image present in the Layer. Proves useful for saving an image.

Method: getLayerDataText
Purpose: This method retrieves the rbg values of the image present in the layer in a specific format that allows
us to save a Project.

Method: getFilterName
Purpose: This method retrieves that name of the filter currently present on the layer.

Method: addCompositeFilter
Purpose: This method adds a composite filter to the image. If the filter option is not one of the available
composite filters ("difference", "multiply", "screen"), then it redirects to the regular addFilter() method.
If the current filter of the image is not "normal", then it sets the reset flag to true.
The method then calls the adjustCompositeImage() method of the image, passing in the filterOption,
the reset flag, and the image of the other layer. The adjustCompositeImage() method applies the filter
and updates the RGB and HSL values of the image.
Finally, the filter field of the layer is updated with the new filterOption.


Method: getBufferedImageOfLayer
Purpose: This method returns a BufferedImage representation of the current layer.
It does so by calling the rgbToBufferedImage() method of the Image object associated with the layer,
which converts the rgbs 2D array of the image to a BufferedImage object using the BufferedImage.TYPE_INT_RGB type.
The resulting BufferedImage represents the visual content of the layer as an RGB image that can be
displayed or further processed by other image processing algorithms.


-------------------------------------------------------------------------------------------------------------------


** Class: ProjectImpl
Purpose:This class is an implementation of the Project interface. It allows us to create
        instances of the Project object type.
        The constructor takes in simply a height and width, throwing
        an Illegal Argument Exception if either value is equal to 0, and establishing the height and width
        of the project as well as instantiating the project's list of layers which exists as a HashMap and a list of
        the order layers which exists as an ArrayList.
        A layer ("Layer 0") is automatically added to this list of layers and the layerOrder.

        The default constructor takes in no parameters, allowing the user to determine the height and width later on.


** ADDED PART 2 Method: setHeightAndWidth
Purpose:This method sets the height and width of a Project object, which is assumed to have a collection of
layers that are all the same height and width. If either the height or width is less than or equal to zero,
an IllegalArgumentException is thrown.
After setting the height and width, the method clears all existing layers and layer orders from the Project,
and adds a new blank layer with the same height and width as the Project.
The new layer is given the name "Layer0", and this name is set as the current layer name.
In essence, this method creates a new Project with a single blank layer, with the same height and width as the Project.

Method: addLayer
Purpose: This method adds a new layer with the given name to the top of the project. The new layer starts as a default
layer, white background with normal filter.
Parameters: layerName The name of the layer to be added

Method: addImageToLayer
Purpose: This method places given image on given layer such that top left corner of image is at (x,y).
Parameters: x the x position of the top left of the layer, y the y position of the top left of the layer

** ADDED PART 2 Method: addImageToLayerRGB
Purpose: This method adds an image represented by a 2D array of RGB values to the specified layer in a Project object.
First, the method checks if the specified layer name exists in the project's layer order.
If not, it throws an IllegalArgumentException with the message "there is no such layer".
Assuming the layer name is valid, the method creates a new ImageImpl object
using the provided 2D array of RGB values and the height and width of the project.
This ImageImpl object is then added to the specified layer using the layer's addImage method,
with an x and y position of 0, indicating that the image should be added at the top-left corner of the layer.
Finally, the method sets the current layer name to the name of the layer that the image was added to,
so that subsequent operations will be performed on that layer.

Method: setFilter
Purpose: This method sets the filter of the given layer to a specific option.
OPTIONS:
   * - normal : does nothing to the image
   * - red-component: only uses the red portion of the RGB. Similar for green-component and blue-component
   * - brighten-value: adds the brightness value pixel by pixel according to value from the corresponding pixel on the
   current layer .
   * Similar for brighten-intensity and brighten-luma. Only affects the current layer.
   * - darken-value: when applied, removes the brightness value pixel by pixel according to value from the corresponding
    pixel on the current layer .
   * Similar for darken-intensity and darken-luma. Only affects the current layer.
Parameters: layerName the name of the Layer to apply the filter to, filterOption the type of filter to apply to the
layer

** ADDED PART 2 Method: setCompositeFilter
Purpose: This method applies a composite filter to a layer by concatenating all the layers before it onto a temporary
layer, applying the filter to the temporary layer and the specified layer, and then cleaning up the temporary layer.

** ADDED PART 2 Method: getCompositeImage
Purpose: This method returns a composite image that represents the current state of a Project object,
by concatenating all layers into a single image.
The method creates a new temporary layer named "tempLayerName12345" using the addLayer method,
and then concatenates all layers in the Project onto this temporary layer using the concatLayer
method of the temporary layer.
After concatenating the layers onto the temporary layer, the method retrieves the Image representation of
the temporary layer using the getImage method. This Image represents the composite image of all layers in the Project.
Finally, the method deletes the temporary layer using the remove method of the Project's layers and layerOrder maps,
sets the current layer name to the original layer name, and returns the composite image.

** ADDED PART 2 Method: getHeight
Purpose: This method returns the height of the project.

** ADDED PART 2 Method: getWidth
Purpose: This method returns the width of the project.

** ADDED PART 2 Method: getLayerNames
Purpose: This method returns the names of all of the layers in a project as a List.

** ADDED PART 2 Method: getCurrentLayerName
Purpose: This method returns the name of the layer currently be edited.

Method: saveImage
Purpose: This method saves the result of applying all filters on the image.
Parameters: fileName the name of the file to save it as

Method: saveProject
Purpose: This method saves the project as one file.
Parameters: fileName the name of the file to save it as

** ADDED PART 2 Method: load
Purpose: This method loads a project from a StringBuilder data object and a file name.
The data object contains information about the project that was previously saved, in a specific format
(.txt format).
The method parses through the data, creating new layers and adding images to those layers based on the information
in the data. The method also sets the height and width of the project, as well as the filter name for each layer
(although the filter name is not used in the current implementation).
If the data object is null or does not contain valid data, the method throws an IllegalArgumentException.
If there is an error in the format of the data, such as missing information or incorrect formatting,
the method may print an error message or throw an exception.

Method: quit
Purpose: This method quits the project and lose all unsaved work.
Parameters: @return if the project has been quit

** ADDED PART 2 Method: switchCurrentLayer
Purpose: This method takes in the name of a layer and checks to see if the list of layers
in a project contains the given layer name.
If so, the name of the current layer is then deemed the name of said layer.

-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------


[INTERFACES]

** Interface: Image

Purpose: This interface represents an image in the system. It provides methods to adjust the brightness and change the
    color of the image. It also provides a method to set a point in the image.

Method: adjustImage
Purpose: This method brightens every pixel in the image based on the type of brightening.
Parameters: type A string that specifies the type of brightening ("max", "avg", or "luma"),
bright A boolean value that specifies whether the image should be brightened or darkened,
needReset A boolean value that specifies whether the image needs to be reset because it already has a filter on it.

Method: changeColor
Purpose: This method changes the color of every pixel in the image to one of its RGB components.
(sets all components not the given color in each pixel to 0)

**ADDED PART 2 Method: getHeight
Purpose: This method returns the height of the image

**ADDED PART 2 Method: getWidth
Purpose: This method returns the width of the image.

**ADDED PART 2 Method: getRbgs
Purpose: This method returns the rgb values of the image in String[][] format.

**ADDED PART 2 Method: rgbToString
Purpose: This method turns the RGB values of the image into a long string of ints.

Method: addImage
Purpose: This method adds an image to an image.
(usually used for base white images of layers, and adding images onto those)

** ADDED PART 2 Method: rgbToString4Across
Purpose: This method changes the image into rgb 4-value string.
(the fourth value is the alpha value, which is currently opaque for PPMs)

** ADDED PART 2 Method: setHsl
Purpose: This method creates the array of HSL values from the stored array of RGB values

** ADDED PART 2 Method: getHsl
Purpose: This method returns the HSL values of an image.

** ADDED PART 2 Method: adjustCompositeImage
Purpose: This method adjusts an image to a filter based on the composite image of the project.

** ADDED PART 3 Method: rgbToBufferedImage
Purpose: This method creates a BufferedImage from the RGB values of a given image.


-------------------------------------------------------------------------------------------------------------------

** Interface: Layer
Purpose: This interface represents a layer that can contain images. It provides methods to add an image to the
    layer at specific coordinates and to add a filter to the layer.

Method: addImage
Purpose: This method adds an image to the layer at the given coordinates.
Parameters:
img The image to add,
x The x coordinate to place the top left corner of the image
y The y coordinate to place the top left corner of the image

Method: addFilter
Purpose: This method adds the given type of filter to the layer.
Parameters:
filterOption The type of filter to add

** ADDED PART 2 Method: concatLayer
Purpose: This method combined two layers into one for the purposes of saving
the project image.

** ADDED PART 2 Method: getLayerDataPPM
Purpose: This method gets the text data of a given layer in PPM style format.

** ADDED PART 2 Method: getImage
Purpose: This method returns the image in a given layer.

** ADDED PART 2 Method: getLayerDataText
Purpose: This method gets the data of the layer in txt file format.

** ADDED PART 2 Method: getFilterName
Purpose: This method gets the name of the filter option currently being used by the layer.

** ADDED PART 2 Method: addCompositeFilter
Purpose: This method adds a filter to the layer that needs to composite image.

** ADDED PART 3 Method: getBufferedImageOfLayer
Purpose: This method gets a BufferedImage representing the image on the layer.


-------------------------------------------------------------------------------------------------------------------

** Interface: Project
Purpose: This interface represents a project that can contain multiple layers and
    provides methods for managing the layers, adding images to them, applying filters,
    and saving the resulting image.

Method: addLayer
Purpose: This method adds a new layer with the given name to the top of the project. The new layer starts as a default
layer, white background with normal filter.
Parameters: layerName The name of the layer to be added

Method: addImageToLayer
Purpose: This method places given image on given layer such that top left corner of image is at (x,y).
Parameters: x the x position of the top left of the layer, y the y position of the top left of the layer

Method: setFilter
Purpose: This method sets the filter of the given layer to a specific option.
OPTIONS:
   * - normal : does nothing to the image
   * - red-component: only uses the red portion of the RGB. Similar for green-component and blue-component
   * - brighten-value: adds the brightness value pixel by pixel according to value from the corresponding pixel on the
   current layer .
   * Similar for brighten-intensity and brighten-luma. Only affects the current layer.
   * - darken-value: when applied, removes the brightness value pixel by pixel according to value from the corresponding
    pixel on the current layer .
   * Similar for darken-intensity and darken-luma. Only affects the current layer.
Parameters: layerName the name of the Layer to apply the filter to, filterOption the type of filter to apply to the
layer


Method: saveImage
Purpose: This method saves the result of applying all filters on the image.
Parameters: fileName the name of the file to save it as

Method: saveProject
Purpose: This method saves the project as one file.
Parameters: fileName the name of the file to save it as

**ADDED PART 2 Method: addImageToLayerRGB
Purpose: This method adds an image to a layer based on just its RBG values.

**ADDED PART 2 Method: setCompositeFilter
Purpose: This method sets the filter to one that requires the data of the underlying image.

**ADDED PART 2 Method: getHeight
Purpose: This method returns the height of the project.

**ADDED PART 2 Method: getHWidth
Purpose: This method returns the width of the project.

**ADDED PART 2 Method: getLayerNames
Purpose: This method returns names of the layers in a project.

**ADDED PART 2 Method: getCurrentLayerName
Purpose: This method returns names of the layer current being edited.

**ADDED PART 2 Method: getCompositeImage
Purpose: This method gets the composite image of all the layers in a project.

**ADDED PART 2 Method: setHeightAndWidth
Purpose: This method sets the height and width of a project. It also clears the layer
and layerOrder fields. It basically resets the project to make a new project with
the given height and width.

**ADDED PART 2 Method: switchCurrentLayer
Purpose: This method switches the current layer to be the layer with the given
layer name.

**ADDED PART 2 Method: load
Purpose: This method loads a new project from a string builder.

Method: quit
Purpose: This method quits the project and lose all unsaved work.
Parameters: @return if the project has been quit


.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜

[CONTROLLER]
.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜
[CLASS]

** Class: ControllerImpl

Method: handleUserInput
Purpose: This method allows the controller to determine which method
to run in response to the users input. The users input is received as a String and specifies
which command they wish to run as well as an values they are required to set
Parameters: input retrieved from the user in the form of a string.

** Class: FeaturesImpl
Purpose: The Features interface provides a set of methods that can be implemented to enable various features
    in a GUIView application.

Method: callControllerOnInput
Purpose: This method takes the user input as a parameter, and based on the input, it performs necessary actions.
It also resets the input text box.

Method: exitProgram
Purpose: This method is used to exit the program.

Method: setView
Purpose: This method is used to set up the view and add features.

Method: changeFormat
Purpose: This method is used to change the format of the input string to simulate command prompts, so that it can be
parsed by the ControllerImpl's usual methods.

Method: switchLayer
Purpose: This method is used to switch the current layer.


-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------


[INTERFACE]

** Interface: Controller
Purpose: Controller for a Collager

Method: handleUserInput
Purpose: This method allows the controller to determine which method
to run in response to the users input. The users input is received as a String and specifies
which command they wish to run as well as an values they are required to set
Parameters: input retrieved from the user in the form of a string.


-------------------------------------------------------------------------------------------------------------------

** Interface: Features
Purpose: The FeaturesImpl class is an implementation of the Features interface, providing methods to
enable various features in a GUIView application.
    Constructors:
    FeaturesImpl(Project p, GUIView view, Controller c) - This constructor takes a Project object, a GUIView object,
    and a Controller object as parameters, and initializes them in the class.
    FeaturesImpl(IModel m) - This constructor takes an IModel object as a parameter and initializes it in the class.
    The FeaturesImpl class can be used to implement the Features interface for a GUIView application. The constructor
    initializes the necessary objects, and the methods can be customized to perform specific tasks based on the
    application requirements.
    Usage:
    To use the FeaturesImpl class, you can create an object of the class and pass it to the GUIView constructor.
    This will enable the features provided by the class in the GUIView application.


Method: callControllerOnInput
Purpose: This method takes the user input as a parameter, and based on the input, it performs necessary actions.
It also resets the input text box.

Method: exitProgram
Purpose: This method is used to exit the program.

Method: setView
Purpose: This method is used to set up the view and add features.

Method: changeFormat
Purpose: This method is used to change the format of the input string to simulate command prompts, so that it can be
parsed by the ControllerImpl's usual methods.

Method: switchLayer
Purpose: This method is used to switch the current layer.

.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・

[VIEW]
.・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜✭・.・✫・゜・。..・。.・゜
[CLASSES]

** Class: BoardPanel
Purpose: BoardPanel is a class in the view package that represents the visual view of the grid for a game of Set.
    This class extends JPanel and implements a method called paintComponent which is responsible for painting
    the contents of the panel.

    The class has two constructors:

    BoardPanel(Project state): Constructs a new panel for the given project state. It takes a Project object as a
    parameter and sets the state of the model to the provided object. If the provided object is null, it throws
    an IllegalArgumentException. The constructor sets the background color to white, initializes cellDimension to 50,
    and sets the preferred size of the panel to the width and height of the provided Project plus 200.
    BoardPanel(): Constructs an empty board panel with a default size of 100x100. It sets the background color to white,
    initializes cellDimension to 50, and sets the preferred size of the panel to the width and height of the Project
    object plus 200.

Method: (protected) paintComponent
Purpose: Overrides the paintComponent method of JPanel.
It paints the composite image of the project and the layers. It loops through the composite image and sets the color
of the pixel using the RGB values. It then loops through the layers and draws the name of each layer with a different
color for the current layer.

-------------------------------------------------------------------------------------------------------------------

** Class: SwingGUIView
Purpose: This is a Swing GUI implementation for an image processing application that allows the user to create a
new project, add layers, switch layers, and add images to layers, and apply filters to the images.
         The GUI is implemented as a JFrame with a BorderLayout. The main panel is divided into two sub-panels:
         boardPanel, which is the custom panel on which the image is drawn and buttonsPanel, which contains all the
         buttons and dialog boxes.
         The boardPanel is created by instantiating a new BoardPanel object with the Project model state.
         The buttonsPanel is created with a BoxLayout and contains various buttons to perform actions on the image.
         These buttons include newProjectButton, addLayerButton, exitButton, switchLayerButton, and
         addImageToLayerButton. Additionally, there is a JTextField for user input and a JButton to apply a filter.
         There are also several dialog boxes for loading and saving files and setting filter options.
         These are implemented with JPanels and JLabels.



Method: refresh
Purpose: This method refreshes the GUI once the user has input something that causes
the GUI to change. It updates the GUI to reflect the wanted input. In order to
do so, it sets the size (either to initial or to how the user wants it) and
repaints the GUI to represent the image a user wants or keeps it white.

Method: renderMessage
Purpose: This method renders a message to throw to an error panel that will
pop up if this user gives an invalid input. The message is taken
from IllegalArgumentExceptions or other illegal input exceptions thrown by other methods
or constructors.

Method: resetFocus
Purpose: "In order to make this frame respond to keyboard events, it must be within strong focus.
Since there could be multiple components on the screen that listen to keyboard events,
we must set one as the "currently focussed" one so that all keyboard events are
passed to that component. This component is said to have "strong focus".
We do this by first making the component focusable and then requesting focus to it.
Requesting focus makes the component have focus AND removes focus from whoever had it
before."

Method: addFeatures
Purpose: This method defines a series of event listeners for a graphical user interface (GUI). These listeners are
attached to different buttons on the GUI and perform various actions when the buttons are clicked.
The method takes an object of type Features as an argument, which is an interface that defines the different features
that can be performed by the GUI.
When the exitButton is clicked, the method calls the exitProgram() method of the Features object. When the
newProjectButton is clicked, the method calls the changeFormat() method of the Features object with the arguments
input.getText() and "new-project ". Similarly, when the addLayerButton is clicked, the method calls the changeFormat()
method of the Features object with the arguments input.getText() and "add-layer ".
When the switchLayerButton is clicked, the method calls the switchLayer() method of the Features object with the
argument input.getText(). When the addImageToLayerButton is clicked, the method calls the addImageToLayer() method of
the current object, passing the Features object and the argument input.getText().
When the loadProjectButton is clicked, the method calls the loadProject() method of the current object, passing the
Features object. Similarly, when the saveProjectButton is clicked, the method calls the saveProject() method of the
current object, passing the Features object. When the saveImageButton is clicked, the method calls the saveImage()
method of the current object, passing the Features object.
Finally, when the setFilterButton is clicked, the method calls the setFilter() method of the current object, passing
the Features object.

Method: (private) addImageToLayer
Purpose: This method works in association with buttons in order
to allow the user to place an image on any given layer. It utilizes JFileChooser
to allow the user to choose an image from their files rather
than prompting the user to enter a file path.

Method: (private) loadProject
Purpose: This method works in association with buttons in order
to allow the user load a project into a current GUI. It utilizes JFileChooser
to allow the user to choose an file from their files rather
than prompting the user to enter a file path.

Method: (private) saveImage
Purpose: This method works in association with buttons in order
to allow the user save an concatenated image from the current GUI. It utilizes JFileChooser
to allow the user to choose where they would like the image to be saved
rather than prompting them to write out their desired file path.

Method: (private) saveProject
Purpose: This method works in association with buttons in order
to allow the user save a project from the current GUI. It utilizes JFileChooser
to allow the user to choose where they would like the project to be saved
rather than prompting them to write out their desired file path.

Method: (private) setFilter
Purpose: This method works in association with buttons in order
to allow to choose the filter they wish to be placed
on a given layer. It utilizes an option dialog to give a drop down
of the filter options.

-------------------------------------------------------------------------------------------------------------------

** Class: ViewImpl
Purpose: This class is called ViewImpl and it is an implementation of the View interface. The View is responsible
    for displaying the current state of the project to the user and receiving input from the user.
    The ViewImpl class has three constructors, one of which takes a Project object and sets the Appendable
    destination to the console. The other two constructors take a Project object and an Appendable object, respectively,
    and use them to set the instance variables.
    Overall, the ViewImpl class is responsible for rendering messages and displaying commands to the user.
    It is an important part of the project's user interface.

Method: renderMessage
Purpose: This method takes a message and appends it to the Appendable destination. This method may throw an IOException
if there is an error while appending the message.


Method: printCommands
Purpose: This method is responsible for printing a list of available commands to the console. It displays the
commands in a table-like format, with each command on its own line. It also displays the available filters and their
names.

** Class: RepresentationConvertor
Purpose: This class contains utility methods to convert an RGB representation
           to HSL and back and print those results.
           Feel free to change these methods as required.

Method: convertRGBtoHSL
Purpose: This method takes three double values, representing the red, green, and blue values of an RGB color,
and returns a string with the corresponding HSL values. The HSL values are represented as a comma-separated string
with hue, saturation, and lightness values in that order.

Method: convertHSLtoRGB
Purpose: This method takes three double values representing the hue, saturation, and lightness values of an HSL color,
and returns a string with the corresponding RGB values. The RGB values are also represented as a comma-separated string
with red, green, and blue values in that order.

Method: convertFn
Purpose: This method is used to perform the conversion from HSL to RGB.


-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------

[INTERFACE]

** Interface: View
Purpose: The View interface defines the methods that a view component in a software application should implement in
    order to display information to the user. Overall, the GUIView interface provides the necessary methods for a GUI
    implementation to interact with the model and update the view in real-time. The implementation of this interface
    may vary depending on the specific requirements of the image processing program.

Method: renderMessage
Purpose: This method is responsible for rendering a given message to the data output in the implementation.
The message can be any string of characters, and the implementation should display it to the user in some way.

Method: printCommands
Purpose: This method is responsible for printing the commands the user can use in the program. It is up to the
implementation to decide how the commands are displayed to the user.

** Interface: GUIView
Purpose:The GUIView interface is a representation of a graphical user interface (GUI) view for an image processing
    program. This interface provides methods to interact with the GUI and to update the view when the model changes.

Method: refresh
Purpose: This method is called when the view needs to be updated. It refreshes the screen to reflect the changes
in the model.

Method: renderMessage
Purpose: This method displays a message in a suitable area of the GUI.

Method: getInputString
Purpose: This method retrieves the string from the text field and returns it.

Method: clearInputString
Purpose: This method clears the text field.

Method: resetFocus
Purpose: This method resets the focus on the appropriate part of the view that has the keyboard listener attached to it,
so that keyboard events will still flow through.

Method: addFeatures
Purpose:This method calls relevant methods on the Features interface when a button is pressed in the GUI.



╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮

  ╭──────────────────────────────────────╮
     WHAT CHANGES WERE MADE FOR PART 2?
  ╰──────────────────────────────────────╯

    ╭──────────────────╮
        NEWLY ADDED
    ╰──────────────────╯

- Features interface and class, to represent the controller for the GUI view
    - Features
    - FeaturesImpl
- RepresentationConvertor class, to handle converting images to and from HSL and RGB
    -RepresentationConvertor
- GUIView interface, Swing GUIView class, and BoardPanel class to construct the GUI
    -BoardPanel
    -GUIView
    -SwingGUIView

    ╭──────────────────╮
        ALTERATIONS
    ╰──────────────────╯


     𓆩⟡𓆪 Added methods in ProjectImpl and LayerImpl to support composite filters,
     without requiring additional space and time for filters that don't need composite images.
        ↑ Find above in ProjectImpl and Layer Impl ↑

     EXAMPLES:
     ★ Added a "setCompositeFilter" method that applies a composite filter to a layer by concatenating
     all previous layers onto a temporary layer, applying the filter, and then cleaning up the temporary layer.

     ★ Added a "getCompositeImage" method that returns a composite image of all layers in a Project object,
     by concatenating all layers into a single image using a temporary layer, and returns the composite image.

     𓆩⟡𓆪 Added HSL representation in ImageImpl and methods to support HSL filters,
     such as "setHsl", "getHsl", and "adjustCompositeImage".

     𓆩⟡𓆪 Added a currentLayerName field in Project, to keep track of the current layer the user was on to apply
     filters to it directly with the GUI, rather than the user having to input the layer name every time.


     𓆩⟡𓆪 AAdded a "setHeight" and "setWidth" method to refresh a project and make it a blank slate
     with the given dimensions, without creating a new project.
     This allows the GUI to have access to the project being edited.

     𓆩⟡𓆪 We added a private function to controller to direct regular filters and composite filters to
     their correct functions in project

╰──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯



╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮

    ╭──────────────────────────────────────╮
       WHAT CHANGES WERE MADE FOR PART 3?
    ╰──────────────────────────────────────╯

↗ Made new image constructor(with h and w) and changed image constructor from file(and add-image to layer in project)
    → This new constructor allows a user to import an image file that may be a PNG or JPEG,
    no longer constraining them to PPMs.
    Given that the image is in PNG or JPEG format, the constructor will read the image file as a
    BufferedImage object using Java's ImageIO library.
    he constructor creates a BufferedImage object and sets the height and width instance variables to the height and
    width of the BufferedImage. It then reads the RGB values of each pixel in the BufferedImage
    and stores them in the rgbs array.

    → If the image file is in the PPM format, the constructor reads the file using the Scanner object created earlier.
    The first token in the file should be "P3", which indicates that the file is a plain PPM file.
    If the token is not "P3", an IllegalArgumentException is thrown.
    The constructor then reads the height and width of the image from the file and stores them in the height
    and width instance variables.

    → After reading the image file, the constructor sets the rgbs and hsls arrays as the instance variables
    of the ImageImpl object.


↗ Made new method in layer
    → We added the method
            public BufferedImage getBufferedImageOfLayer()
    to both Layer and LayerImpl.
    →This method returns a BufferedImage of the layer represented by the Image object that the method is called on.

    → To create the BufferedImage, the method first calls the rgbToBufferedImage() method on the Image object,
    which converts the RGB values stored in the Image object's rgbs field to a BufferedImage.
    The BufferedImage is then returned.

↗ Made new method for saving png and jpg in image
    → We added the method
          public BufferedImage rgbToBufferedImage()
    to both Image and ImageImpl.
    → This method converts the RGB values stored in the rgbs field of an Image object to a BufferedImage.
    To do this, the method creates a new BufferedImage with dimensions equal to the width and height of the
    Image object.
    → The type of the BufferedImage is set to TYPE_INT_RGB, which means that each pixel is represented by an integer
    value that encodes the red, green, and blue components of the pixel.
    The method then iterates over each pixel in the rgbs field, and for each pixel,
    it extracts the red, green, and blue components from the corresponding CSV string in rgbs.
    → It then converts these components from their integer values in the range [0, 255] to floating point values
    in the range [0, 1], and uses them to create a new Color object.
    The method then creates an array of floating point values that represent the red, green, blue,
    and alpha components of the pixel, and sets the pixel in the BufferedImage to the corresponding
    RGB value using the setRGB method. Finally, the BufferedImage is returned.


↗ Added alpha values (represented as a 2D array of Doubles) as an additional field in the ImageImpl class.
    → An image may now be represented using the data for the alpha values of the image. This allows us to
    know just how transparent an image is and take it into account.


 ↗ Made new method for handling the alpha values of an image
    → This method (getAlphas) returns the alpha values of an image, ensuring that the transparency
    of an image is data that is taken into account. Beforehand, we had not taken these values into account
    when adding an image to a layer.




    FOR REFERENCE:

    OUR PROJECT IS FULLY DECOUPLED. ONLY INTERFACES USED WITHIN MODEL AND CONTROLLER + ALL CLASSES AND INTERFACES
    IN VIEW.

    [Interfaces Needed]
    model
        Image
        Layer
        Project
    controller
        Controller
        Features

   [View]
        BoardPanel
        GUIView
        SwingGUIView
        View
        ViewImpl



      FOR REFERENCE PART 2:

        OUR PROJECT WAS FULLY DECOUPLED ONCE MORE.
        ONLY INTERFACES USED WITHIN MODEL AND VIEW + ALL CLASSES AND INTERFACES
        IN CONTROLLER.

        [Interfaces Needed]
        model
            Image
            Layer
            Project

        [controller]
            Controller
            ControllerImpl
            Features
            FeaturesImpl

        view
            GUIView
            View


╰─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╯





╭──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╮


╭─────────────────────────────────╮
        HOW DO I RUN THIS?
╰─────────────────────────────────╯

(see USEME)

Example commands:

new-project 500 400
add-layer green-cat
set-filter green-cat green-component
add-image-to-layer green-cat model/CAT.ppm 0 0
add-layer blue-cat
add-image-to-layer blue-cat model/CAT.ppm 50 50
set-filter blue-cat blue-component
add-layer blank
set-filter blank brighten-luma
save-image res/colorfulcat.ppm
save-project projects/colorfulcat.txt


In order to run the commands shown above, a terminal must be opened and one must navigate into
the src directory, in which the Main class is located. Once there, the command
"java Main.java"
can be run.
Once run, any of the following commands may then be run.
-------------------------------------------------------------------------------
--    new-project                height width                                --
--    add-layer                  layerName                                   --
--   load-project                filePath                                    --
-- add-image-to-layer            layerName imagePath offset offset           --
--    set-filter                 layerName filterOption                      --
--    save-image                 imagePath                                   --
--       quit                                                                --
-------------------------------------------------------------------------------

In order to run the examples above, following these instructions:
enter the command
"new-project 500 400"
By doing so, you create a new project, setting the height and width of the project. In this case
500 represents the height and 400 represents the width. In order to make a project with different
dimensions, simply alter those numbers.

Next, enter the command
"add-layer green-cat"
This creates a new layer within the project you have just created. The new layer
is given the name green-cat. If you wish to name the layer something else,
replace "green-cat" either a singular word or multiple words seperated only by "-".

Next, enter the command
set-filter green-cat green-component
This sets the filter of the specified layer ("green-cat") to the filter entitled
"green-component". If you wish to a different filter, simply change "green-component"
to any of the following:


-------------------------
--      normal         --
--    red-component    --
--   blue-component    --
--   green-component   --
--   brighten-luma     --
--   bright-intensity  --
--   brighten-value    --
--   darken-luma       --
--   darken-intensity  --
--   darken-value      --
--    multiply         --
--    difference       --
--     screen          --
--       quit          --
-------------------------


Next, ensure that you have a picture entitled CAT in a ppm format within the model folder.
Next, enter the command
add-image-to-layer green-cat model/CAT.ppm 0 0
This adds the specified image "model/CAT.ppm" to the layer "green-cat"
with an offset specified by "0 0".
If you wish to place the image on a different layer, replace "green-cat" with another existing layer.
If you wish to place a different image on the layer, replace "model/CAT.ppm"
the directory of another image. For example, [folder]/[name-of-image].ppm
If you wish to alter the placement of image on the layer, change values "0 0". These values set
the top left corner of the image at these positions.

Next, enter the command
add-layer blue-cat
This adds another layer onto the project, this time entitled "blue-cat". This layer
is placed on top of the existing layers.
If you wish to name the layer something else,
replace "blue-cat" either a singular word or multiple words seperated only by "-".

Next, enter the command
add-image-to-layer green-cat model/CAT.ppm 50 50
This adds the specified image "model/CAT.ppm" to the layer "blue-cat"
with an offset specified by "50 50".
If you wish to place the image on a different layer, replace "blue-cat" with another existing layer.
If you wish to place a different image on the layer, replace "model/CAT.ppm"
the directory of another image. For example, [folder]/[name-of-image].ppm
If you wish to alter the placement of image on the layer, change values "50 505". These values set
the top left corner of the image at these positions.

Next, enter the command
set-filter blue-cat blue-component
This sets the filter of the specified layer ("blue-cat") to the filter entitled
"blue-component". If you wish to a different filter, simply change "blue-component"
to any of the following:

-------------------------
--      normal         --
--    red-component    --
--   blue-component    --
--   green-component   --
--   brighten-luma     --
--   bright-intensity  --
--   brighten-value    --
--   darken-luma       --
--   darken-intensity  --
--   darken-value      --
--    multiply         --
--    difference       --
--     screen          --
--       quit          --
-------------------------


Next enter the command
add-layer blank
This adds another layer onto the project, this time entitled "blank". This layer
is placed on top of the existing layers.
If you wish to name the layer something else,
replace "blank" either a singular word or multiple words seperated only by "-".

Next enter the command
set-filter blank brighten-luma
This sets the filter of the specified layer ("blank") to the filter entitled
"brighten-luma". If you wish to a different filter, simply change "brighten-luma"
to any of the following:

-------------------------
--      normal         --
--    red-component    --
--   blue-component    --
--   green-component   --
--   brighten-luma     --
--   bright-intensity  --
--   brighten-value    --
--   darken-luma       --
--   darken-intensity  --
--   darken-value      --
--    multiply         --
--    difference       --
--     screen          --
--       quit          --
-------------------------


Next enter the command
save-image res/colorfulcat.ppm
This allows you to save the image to the given folder ("res") with
any name you would like ("colorfulcat")
If you wish to change the folder that the image is saved to,
simply replace "res".
If you wish to change the name of the image, replaced "colorfulcat"
with your desired name, leaving ".ppm" at the end of it.

Next enter the command
save-project projects/colorfulcatyay.txt
This allows you to save the current project and all of its existing layers and images
as a file. Simply chose the location to which you want to save it ("projects") and give it a name (colorfulcatyay).
If you wish to change the location of the project file,
simply replace "projects".
If you wish to change the name of the image, replaced "colorfulcatyay"
with your desired name, leaving ".txt" at the end of it.



PART 2:

Following the addition of the GUI functionality, here is the new way in which a user can use
our program:

A user my simply open the provided jar file.
Once the GUI is displayed, a user is prompoted with an empty project and a multiple
of buttons and a text box that allow them to make changes.
These buttons include:
New Project
Save Project
Add Image to Layer
Save Image
Switch Layer
Load Project
Add Layer
Set Filter

These buttons work as follows:

If a user wishes to

[create a new project]:

They must type their preferred dimensions in the text box and click
the "New Project" button. This generates a brand new project in the GUI window,
overriding the previous project.

[save their project]
They must click the "Save Project" button that will open a file center
and allow them to choose the file in which they would like
to save their project file.

[add an image to a layer]
They must input their given coordinates in the text box
and click the "Add image to layer" button. This prompts them with a file center
that allows them to navigate to their preferred image. The image is then placed
on the layer that the user is currently on.

[save an image]
They must click the "Save Image" button that will open a file center
and allow them to choose the file in which they would like
to save their image.

[switch to a different layer]
They must enter the name of the layer they wish to navigate to in the text box.
Then they must click the "Switch Layer" button.
This will ensure that the current layer for editing is the input layer.
The layer chosen will be highlighted on the right side of the screen.

[load a different project]
They must click the "Load Project" button that will open a file center
and allow them to choose the file they would like to open in
the GUI. The opened project will override the project previously on the GUI.

[add a layer to their project]
They must input the preferred name of their new layer in the text box
and then click the "Add Layer" button.

[set the filter of a given layer]
The user must ensure that they are on the layer that
they wish to edit. Next, the user can click the "Set Filter"
drop down menu and they will be presented with many different filter options.
They must choose their preferred filter from the options.



CODE CITATION:

In ControllerImpl class:
loadProject() method:

We used these resources in order to figure out how to
use the load() method in order to load our project.

Not much code was actually taken, but the general idea in
regards to how to load a project was extrapolated.


Note for the code used in the View directory:
In SwingGUIView, BoardPanel, etc;
Much of our view code was inspired by code provided in our assignment instructions.
For example, the usages of Swing and RBG to HSL conversions. We utilized this code
to understand how to create a GUI window and alter it by adding buttons, a text box, and illustrating
an image.

- Le Anh Quan
- 2015
- Advanced Java Class Tutorial: A Guide to Class Reloading
- https://www.toptal.com/java/java-wizardry-101-a-guide-to-java-class-reloading


- Preeti Jain
- September 15, 2019
- Java System class load() method with example
- public static void load(String fn);
- https://www.includehelp.com/java/system-class-load-method-with-example.aspx



IMAGE CITATION:

(for CAT.ppm)

Nyan Cat
battosaix2l84u
https://knowyourmeme.com/memes/nyan-cat

(for the photo of the Rock hugging a Christmas tree)

Title: Dwayne Johnson on the cover of Entertainment Weekly
Creator: Art Streiber
Website: Entertainment Weekly
URL: https://ew.com/movies/2017/12/08/dwayne-johnson-ew-cover-story/
Accessed: April 12, 2023.

(for the photo of the guy holding a computer and looking very happy)

Title: Portrait of a smiling casual man
Creator: LightField Studios
Website: Adobe Stock
URL: https://stock.adobe.com/images/Portrait-of-a-smiling-casual-man/176994859
Accessed: April 12, 2023.