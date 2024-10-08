import drivers.EncoderDecoderInterface;
import helpers.AlertWindowHelper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaEncoderDecoder extends Application {

    public static float VERSION = (float) 0.1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JED <Java Encoder/Decoder> v" + String.valueOf(VERSION));

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(500);

        try {
            String iconPath = System.getProperty("user.dir") + "/resources/images/icons/favicon.png";
            Image applicationIcon = new Image((new File(iconPath)).toURI().toString());
            primaryStage.getIcons().add(applicationIcon);
        } catch (Exception e) {
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.setPrefSize(600, 500);
        grid.setGridLinesVisible(false);

        Label fileSelectLabel = new Label("File");
        GridPane.setConstraints(fileSelectLabel, 0, 0);

        TextField filePath = new TextField();
        filePath.setPromptText("File Real Path");
        filePath.setEditable(false);
        GridPane.setConstraints(filePath, 1, 0);

        Button fileSelectButton = new Button("File Select");
        GridPane.setConstraints(fileSelectButton, 2, 0);

        Label textLabel = new Label("Text");
        GridPane.setConstraints(textLabel, 0, 1);

        TextArea textAreaBeProcess = new TextArea();
        textAreaBeProcess.setPromptText("File select");
        GridPane.setConstraints(textAreaBeProcess, 1, 1, 3, 1);

        FileChooser fileChooser = new FileChooser();
        fileSelectButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filePath.setText(selectedFile.getAbsolutePath());
                textAreaBeProcess.setText("");
            }
        });

        Label optionsLabel = new Label("Options");
        GridPane.setConstraints(optionsLabel, 0, 2);

        ComboBox<String> driverSelected = new ComboBox<>();
        driverSelected.getItems().addAll(
                "Base64"
        );
        driverSelected.setValue("Base64");
        GridPane.setConstraints(driverSelected, 1, 2);

        ComboBox<String> processSelected = new ComboBox<>();
        processSelected.getItems().addAll(
                "encode", "decode"
        );
        processSelected.setValue("encode");
        GridPane.setConstraints(processSelected, 2, 2);

        ComboBox<String> encodingSelected = new ComboBox<>();
        encodingSelected.getItems().addAll(
                "US-ASCII", "ISO-8859-1", "UTF-8", "UTF-16BE", "UTF-16LE", "UTF-16"
        );
        encodingSelected.setValue("UTF-8");
        GridPane.setConstraints(encodingSelected, 3, 2);


        Button processButton = new Button("Encode/Decode");
        GridPane.setConstraints(processButton, 1, 3);

        TextArea resultTextArea = new TextArea();
        resultTextArea.setPromptText("Result");
        resultTextArea.setEditable(false);
        resultTextArea.setWrapText(true);
        GridPane.setConstraints(resultTextArea, 1, 4, 3, 1);


        processButton.setOnAction(e -> {
            try {
                String data = textAreaBeProcess.getText();
                boolean isFile = !filePath.getText().isEmpty();

                JEDFactory jedFactory = new JEDFactory();
                EncoderDecoderInterface driver = jedFactory.createJED(driverSelected.getValue(), encodingSelected.getValue());

                if (isFile) {
                    textAreaBeProcess.setText("");
                    data = new String(Files.readAllBytes(Paths.get(filePath.getText())), driver.getEncoding());
                }
                if (data.isEmpty()) {
                    throw new Exception("The void cannot be encode or decode!");
                }

                byte[] result;
                switch (processSelected.getValue().toLowerCase()) {
                    case "encode":
                        result = driver.encode(data);
                        break;
                    case "decode":
                        result = driver.decode(data);
                        break;
                    default:
                        throw new Exception("Unsupported process!");
                }

                resultTextArea.setText(new String(result, driver.getEncoding()));
            } catch (Exception exception) {
                AlertWindowHelper.error("Error", "An error occurred during the process : " + exception.getMessage());
            }
        });

        grid.getChildren().addAll(fileSelectLabel, filePath, fileSelectButton, textAreaBeProcess, optionsLabel, driverSelected, encodingSelected, processSelected, processButton, resultTextArea);

        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}