package paint;

import shapes.StraightLine;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import shapes.*;

public class Controller implements Initializable {

    //variables from FXML
    @FXML
    private ResizableCanvas canvas;
    @FXML
    private TextField brushSize;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Slider slider;
    @FXML
    private MenuItem save, saveAs, quit, open, undo, resize, fDraw,
            lDraw, sDraw, rDraw, cDraw, eDraw, filled, transparent,
            about;
    @FXML
    private Menu fileFxml, help;

    private ColorPicker colorPickerFill; //to determine color of fill, not actually on program
    private String mode; //to determine what tool is currently being used
    private File file;
    private GraphicsContext graphicsContext;
    private boolean saved = false;

    //declaring all the modes
    private StraightLine straightLine;
    private MyRectangle myRectangle;
    private MySquare mySquare;
    private MyEllipse myEllipse;
    private MyCircle myCircle;
    private FreeDraw freeDraw;

    //stack for undo
    public Stack<MyShapes> undoStack = new Stack<>();
    
    
    //method for Save As button
    public void onSaveAs() {
        //opening file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        file = fileChooser.showSaveDialog(Paint.getPrimaryStage());

        //get current screen and make a file
        Image image = canvas.snapshot(null, null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            saved = true;
        } catch (Exception e) {
            System.out.println("Failed to save image.");
        }
    }

    //method for Save button
    public void onSave() {
        //if has not been saved before or opened from file, act like save as button
        if (!saved) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            file = fileChooser.showSaveDialog(Paint.getPrimaryStage());

            //get current screen and make a file
            Image image = canvas.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                saved = true;
                canvas.setCanvasSaved(true);
            } catch (Exception e) {
                System.out.println("Failed to save image.");
            }
            //if has been saved before or opened from file, do the following
        } else {
            //alert to confirm save
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Work?");
            alert.setContentText("Overwrite File?");
            Optional<ButtonType> rslt = alert.showAndWait();
            //if user selectes ok, save the file
            if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
                //get current screen and save it to the selected file
                try {
                    Image image = canvas.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    saved = true;
                    canvas.setCanvasSaved(true);
                } catch (Exception e) {
                    System.out.println("Failed to save image.");
                }
            }
            
        }
    }

    //method for Exit button
    public void onExit() {
        //if this a fresh project and the canvas has been edited
        if (!canvas.getCanvasSaved() && !saved) {
            //popup reminding the user work is not saved
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Work?");
            alert.setContentText("Exiting will lose all progress. Save work?");
            Optional<ButtonType> rslt = alert.showAndWait();
            //if user selects ok to save, act like saveAs button
            if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
                //opening file chooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Image");
                file = fileChooser.showSaveDialog(Paint.getPrimaryStage());

                //get current screen and make a file
                Image image = canvas.snapshot(null, null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } catch (Exception e) {
                    System.out.println("Failed to save image.");
                }
                canvas.setCanvasSaved(true);
            }
            //if user chooses cancel button, the program will close and not save
            else if ((rslt.isPresent()) && rslt.get() == ButtonType.CANCEL) {
                Platform.exit();
                System.exit(0);
            } 
            
            //if canvas has been edited but the work was already saved at lease
            //once or has been opened from a file, do the following
        } else if (!canvas.getCanvasSaved()){
            //pop up reminding them work has not been saved
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Save Work?");
            alert.setContentText("Exiting will lose all progress. Save work?");
            Optional<ButtonType> rslt = alert.showAndWait();
            //if they choose to save work, acts like save button when ok is pressed
            if ((rslt.isPresent()) && rslt.get() == ButtonType.OK) {
                //get current screen and save it to the selected file
                try {
                    Image image = canvas.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    saved = true;
                    canvas.setCanvasSaved(true);
                } catch (Exception e) {
                    System.out.println("Failed to save image.");
                }
            }
            //after saving, exit the program
            Platform.exit();
            System.exit(0);
            //if the canvas not been edited, or the work was already saved and the
            //canvas has not been edited since last save, exit the program
        } else {
            Platform.exit();
            System.exit(0);
        }
        
    }

    //method for Open button
    public void onOpen() {
        //opening file chooser
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog(Paint.getPrimaryStage());

        //put image on the canvas
        if (file != null) {
            Image image = new Image(file.toURI().toString());

            //setting the canvas to the size of the image
            canvas.setHeight(image.getHeight());
            canvas.setWidth(image.getWidth());
            graphicsContext.drawImage(image, 0, 0); //0 corresponds to x and y position, putting in center
            saved = true;
        }
    }

    //method for About button
    public void onAbout() {
        //creating a TextArea where the following will be stored to describe the progam
        TextArea textArea = new TextArea("Paint V2\n"
                + "The following program is a paint application. "
                + "You can open a picture by clicking: \n\n"
                + "File -> Open -> Select an image \n\n"
                + "Once a file is opened, you can draw on it. "
                + "Once you are done, save the file:\n\n"
                + "File -> Save As -> Save in desired directory");
        textArea.setEditable(false);
        textArea.setWrapText(true);

        //creating GridPane to hold TextArea
        GridPane gridPane = new GridPane();
        gridPane.add(textArea, 0, 0); //0 corresponds to x and y position, putting in center

        //creating the popup for the help menu
        Alert alert = new Alert(AlertType.INFORMATION); //setting type of alert it is
        alert.setTitle("About Paint");
        alert.getDialogPane().setContent(gridPane); //putting gridPane into alert
        alert.showAndWait(); //wait for user to do something with alert  
    }

    //to initialize canvas upon program start up and what to do when mouse is used in canvas
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initializing canvas so it can be written on
        graphicsContext = canvas.getGraphicsContext2D();

        //adjusting look of colorpicker and default color
        colorPicker.getStyleClass().add("button");
        colorPicker.setValue(javafx.scene.paint.Color.BLACK);

        //setting default of mode and colorPickerFill so they do not throw Null
        mode = "Free Draw";
        colorPickerFill = colorPicker;
        
        setKeyCombinations();
        
        //setting start for slider and listener to adjust zoom
        slider.setValue(1);
        slider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
            canvas.setScaleX(newvalue.doubleValue());
            canvas.setScaleY(newvalue.doubleValue());
        });

        //what to do when mouse is clicked on canvas
        canvas.setOnMousePressed(e -> {
            //setting size for free draw and line
            setSize(e.getX(), e.getY());

            //when the mode is line
            if (mode.equals("Line")) {
                graphicsContext.setStroke(colorPicker.getValue()); //set color of line

                straightLine = new StraightLine(); //instantiating object

                //giving the line object data about color, graphicsContext, and start point
                straightLine.setGraphicsContext(graphicsContext);
                straightLine.setColor(colorPicker);
                straightLine.setStartPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);
                //when the mode is free draw
            } else if (mode.equals("Free Draw")) {
                graphicsContext.setStroke(colorPicker.getValue());
                graphicsContext.beginPath();
                graphicsContext.lineTo(e.getX(), e.getY());

                freeDraw = new FreeDraw();

                freeDraw.setGraphicsContext(graphicsContext);
                freeDraw.setColor(colorPicker);
                freeDraw.setStartPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);

                //when mode is rectangle
            } else if (mode.equals("Rectangle")) {
                graphicsContext.setStroke(colorPicker.getValue()); //set color of rectangle
                graphicsContext.setFill(colorPicker.getValue()); //set fill of rectangle

                myRectangle = new MyRectangle(); //instantiating object

                //give the rectangle object data about color, graphicsContext, and start point
                myRectangle.setGraphicsContext(graphicsContext);
                myRectangle.setColor(colorPicker);
                myRectangle.setFill(colorPickerFill);
                myRectangle.setStartPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);

                //when mode is square
            } else if (mode.equals("Square")) {
                graphicsContext.setStroke(colorPicker.getValue()); //set color of square
                graphicsContext.setFill(colorPickerFill.getValue()); //set fill of square

                mySquare = new MySquare(); //instantiating object

                //give the square object data about color, graphicsContext, and start point
                mySquare.setGraphicsContext(graphicsContext);
                mySquare.setColor(colorPicker);
                mySquare.setFill(colorPickerFill);
                mySquare.setStartPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);

                //when mode is ellipse
            } else if (mode.equals("Ellipse")) {
                graphicsContext.setStroke(colorPicker.getValue()); //set color of ellipse
                graphicsContext.setFill(colorPickerFill.getValue()); //set fill of ellipse

                myEllipse = new MyEllipse(); //instantiating object

                //give the ellipse object data about color, graphicsContext, and start point
                myEllipse.setGraphicsContext(graphicsContext);
                myEllipse.setColor(colorPicker);
                myEllipse.setFill(colorPickerFill);
                myEllipse.setCenterPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);

                //when mode is circle
            } else if (mode.equals("Circle")) {
                graphicsContext.setStroke(colorPicker.getValue()); //set color of circle
                graphicsContext.setFill(colorPickerFill.getValue()); //set fill of circle

                myCircle = new MyCircle(); //instantiating object

                //give the circle object data about color, graphicsContext, and start point
                myCircle.setGraphicsContext(graphicsContext);
                myCircle.setColor(colorPicker);
                myCircle.setFill(colorPickerFill);
                myCircle.setCenterPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);
            }
        });

        //what to do when mouse is pressed and dragged
        canvas.setOnMouseDragged(e -> {

            //when mode is free draw
            if (mode.equals("Free Draw")) {
                graphicsContext.lineTo(e.getX(), e.getY());
                graphicsContext.stroke();

                freeDraw.addPoint(e.getX(), e.getY());
                canvas.setCanvasSaved(false);
            }
        });

        //what to do when mouse is pressed and then released
        canvas.setOnMouseReleased(e -> {
            //when mode is free draw
            if (mode.equals("Free Draw")) {
                graphicsContext.lineTo(e.getX(), e.getY());
                graphicsContext.stroke();

                freeDraw.setEndPoint(e.getX(), e.getY());
                undoStack.push(freeDraw);
                canvas.setCanvasSaved(false);

                //when mode is line
            } else if (mode.equals("Line")) {
                straightLine.setEndPoint(e.getX(), e.getY()); //set where line ends

                straightLine.draw(); //put line on canvas
                undoStack.push(straightLine);
                canvas.setCanvasSaved(false);

                //when mode is rectangle
            } else if (mode.equals("Rectangle")) {
                myRectangle.setEndPoint(e.getX(), e.getY()); //set where mouse ends

                //set width and height
                myRectangle.setWidth();
                myRectangle.setHeight();
                myRectangle.check();

                myRectangle.draw(); //put rectangle on canvas
                undoStack.push(myRectangle);
                canvas.setCanvasSaved(false);

                //when mode is square
            } else if (mode.equals("Square")) {
                mySquare.setEndPoint(e.getX(), e.getY()); //set where mouse ends

                //set width and height
                mySquare.setWidth();
                mySquare.setHeight();
                mySquare.check();

                mySquare.draw(); //put square on canvas
                undoStack.push(mySquare);
                canvas.setCanvasSaved(false);

                //when mode is ellipse
            } else if (mode.equals("Ellipse")) {
                myEllipse.setEndPoint(e.getX(), e.getY()); //set where mouse ends

                //set radius for ellipse
                myEllipse.setRadius();
                myEllipse.check();

                myEllipse.draw(); //put ellipse on canvas
                undoStack.push(myEllipse);
                canvas.setCanvasSaved(false);

                //when mode is circle
            } else if (mode.equals("Circle")) {
                myCircle.setEndPoint(e.getX(), e.getY()); //set where mouse ends

                //set radius for circle
                myCircle.setRadius();
                myCircle.check();

                myCircle.draw(); //put on canvas
                undoStack.push(myCircle);
                canvas.setCanvasSaved(false);
            }

        });

    }

    //configuring the file chooser
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures"); //title of file chooser
        fileChooser.setInitialDirectory( //which directory the dialogue will start in
                new File(System.getProperty("user.home"))
        );

        //the extentions the user will be open from the file chooser
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png", "*.jpeg"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
        );
    }

    //setting the size of the line
    private void setSize(double a, double b) {
        graphicsContext.setLineWidth(Double.parseDouble(brushSize.getText()));

    }

    //following 6 methods are for changing the mode when selected tools
    public void onRectangle() {
        mode = "Rectangle";
    }

    public void onFreeDraw() {
        mode = "Free Draw";
    }

    public void onSquare() {
        mode = "Square";
    }

    public void onEllipse() {
        mode = "Ellipse";
    }

    public void onCircle() {
        mode = "Circle";
    }

    public void onLine() {
        mode = "Line";
    }

    //when filled button is selected under fill
    public void onFill() {
        colorPickerFill = colorPicker; //set fill to whatever current color is
    }

    //when transparent button is selected under fill
    public void onTransparent() {
        colorPickerFill = new ColorPicker(Color.TRANSPARENT); //set fill to transparent
    }

    //what to do when clicking undo
    public void onUndo() {
        if (!undoStack.empty()) {
            //remove first element
            undoStack.pop();
            
            //to hold what is in undoStack so that we do not remove anything else from it
            //this is explained a bit more below
            Stack<MyShapes> tempStack = new Stack<>();
            
            //clear canvas so that we can put everything back on canvas further down the method
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            
            //used to loop through undoStack
            Iterator iterator = undoStack.iterator();
            
            //putting what is in undoStack into tempStack starting at 1st element
            //may need adjusted to start at the end of the stack to prevent odd overlapping bug??
            while (iterator.hasNext()) {
                tempStack.push((MyShapes) iterator.next());
            }

            while (!tempStack.isEmpty()) {
                //grabs the first element from the temporary stack and removes it for next loop
                //used so that we do not remove anything from undoStack and have to put it back in
                MyShapes tempShape = tempStack.pop();
                
                //use the current shape and draw it back on the canvas
                if (tempShape.getClass() == FreeDraw.class) {
                    FreeDraw tempFreeDraw = (FreeDraw) tempShape;
                    tempFreeDraw.draw();
                } else if (tempShape.getClass() == StraightLine.class) {
                    StraightLine tempStraightLine = (StraightLine) tempShape;
                    tempStraightLine.draw();
                } else if (tempShape.getClass() == MyRectangle.class) {
                    MyRectangle tempRectangle = (MyRectangle) tempShape;
                    tempRectangle.draw();
                } else if (tempShape.getClass() == MyEllipse.class) {
                    MyEllipse tempEllipse = (MyEllipse) tempShape;
                    tempEllipse.draw();
                } else if (tempShape.getClass() == MySquare.class) {
                    MySquare tempSquare = (MySquare) tempShape;
                    tempSquare.draw();
                } else if (tempShape.getClass() == MyCircle.class) {
                    MyCircle tempCircle = (MyCircle) tempShape;
                    tempCircle.draw();
                }
            }
        }
    }

    //pop up to resize the canvas
    public void onResize() {
        //layout of popup
        GridPane gridPane = new GridPane();
        Label heightLabel = new Label("Height Value: ");
        Label widthLabel = new Label("Width Value: ");
        
        //getting height/width of current canvas
        TextField heightInput = new TextField(Double.toString(canvas.getHeight()));
        TextField widthInput = new TextField(Double.toString(canvas.getWidth()));
        Button update = new Button("Update"); //to update height/width of canvas
        
        //putting gridpane together
        gridPane.add(heightLabel, 0, 0);
        gridPane.add(heightInput, 1, 0);
        gridPane.add(widthLabel, 0, 1);
        gridPane.add(widthInput, 1, 1);
        gridPane.add(update, 1, 2);
        
        //what to do when clicking update button
        update.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                canvas.resizeCanvas(Double.parseDouble(heightInput.getText()), Double.parseDouble(widthInput.getText()));

                System.out.println("Done");
            }
        });
        
        //pop up for resizing canvas
        Alert alert = new Alert(AlertType.INFORMATION); //setting type of alert it is
        alert.setTitle("Resize Canvas");
        alert.getDialogPane().setContent(gridPane); //putting gridPane into alert
        alert.showAndWait();

    }
    
    //setting key combinations for all the buttons
    public void setKeyCombinations() {
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        saveAs.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        fileFxml.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
        undo.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        resize.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        fDraw.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));
        lDraw.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN));
        sDraw.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));
        rDraw.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN));
        cDraw.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN));
        eDraw.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.ALT_DOWN));
        filled.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN));
        transparent.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN));
        about.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN));
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.ALT_DOWN));
    }
}
