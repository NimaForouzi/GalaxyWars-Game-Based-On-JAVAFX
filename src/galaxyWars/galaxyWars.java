package galaxyWars;

import com.sun.media.jfxmedia.events.PlayerEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class galaxyWars extends Application {

    private Pane root = new Pane();

    private double t = 0;

    Image damnShip = new Image("/spaceShips_002 (Copy).png");

    Image enemyShip = new Image("/spaceAstronauts_017.png");

    Image bulletPlayerImg = new Image("/spaceMissiles_004.png");

    Image bulletEnemyImg = new Image("/spaceMissiles_021.png");

    public Sprite player = new Sprite(300, 750, "player", damnShip);

    private Parent createContent() {
        root.setPrefSize(600, 800);

        BackgroundImage myBI = new BackgroundImage(new Image("fN6yg6C.jpg", 600, 800, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        root.getChildren().add(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        nextLevel();

        return root;
    }

    private void nextLevel() {
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i * 100, 150, "enemy", enemyShip);
            s.setUserData("EN");
            root.getChildren().add(s);
        }
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i * 100, 200, "enemy", enemyShip);
            s.setUserData("EN");

            root.getChildren().add(s);
        }
    }

    private List<Sprite> sprites() {
        return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
        //return root.getChildren().
    }
    Pane endPane = new Pane();
    Image endImg = new Image("/098287771e8b366470d8111c18944d08.jpg");
    BackgroundImage endBC = new BackgroundImage(endImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    Scene myScene = new Scene(endPane);

    private int specifyEN = 0;
    private int specifyPL = 0;
    int x = 0;
    int y = 0;
    int score;
    String temp = " ";

    String StringName, StringScore;

    private void update() {
        Image bcbt = new Image("/universe.png");
        BackgroundImage btnIMG = new BackgroundImage(bcbt, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        Background bc = new Background(btnIMG);

        endPane.setBackground(new Background(endBC));
        Button endBTN = new Button("END");
        endBTN.setPrefSize(400, 400);
        endBTN.setTextFill(Color.ALICEBLUE);
        endBTN.setFont(Font.font("Titr", 80));
        endBTN.setBackground(bc);
        endPane.getChildren().add(endBTN);
        endBTN.setTranslateX(100);
        endBTN.setTranslateY(-65);
        t += 0.016;
        player.setUserData("PL");
        endPane.setPrefSize(600, 800);
        sprites().forEach((s) -> {
            switch (s.type) {

                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                        this.x += 1;
                        if (x == 1) {
                            Label wonLabel = new Label();
                            wonLabel.setTextAlignment(TextAlignment.CENTER);
                            wonLabel.setText("YOU LOOSE!");
                            wonLabel.setTranslateX(160);
                            Glow myGlow = new Glow(0.9);
                            wonLabel.setEffect(myGlow);
                            wonLabel.setTranslateY(325);
                            wonLabel.setTextFill(Color.RED);
                            wonLabel.setStyle("-fx-font-size: 60px; -fx-font-weight: bold");
                            endPane.getChildren().add(wonLabel);

                            // LabelName
                            Label nameLabel = new Label("NAME: ");
                            nameLabel.setTextFill(Color.ALICEBLUE);
                            nameLabel.setTranslateX(170);
                            nameLabel.setTranslateY(425);
                            nameLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold");
                            endPane.getChildren().add(nameLabel);

                            //TextBox
                            TextField nameTextField = new TextField();
                            nameTextField.setPromptText("enter your name");
                            nameTextField.setTranslateX(340);
                            nameTextField.setTranslateY(434);
                            nameTextField.setPrefSize(190, 40);
                            endPane.getChildren().add(nameTextField);

                            //scoreLabel
                            Label scoreLabel = new Label("SCORE: ");
                            scoreLabel.setTextFill(Color.ALICEBLUE);
                            scoreLabel.setTranslateX(170);
                            scoreLabel.setTranslateY(495);
                            scoreLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold");
                            endPane.getChildren().add(scoreLabel);

                            //answerScoreLabel
                            Label answerScore = new Label();
                            answerScore.setText(Integer.toString(score));
                            Glow answerGlow = new Glow(0.9);
                            answerScore.setEffect(myGlow);
                            answerScore.setTextFill(Color.YELLOW);
                            answerScore.setTranslateX(360);
                            answerScore.setTranslateY(485);
                            answerScore.setStyle("-fx-font-size: 65px; -fx-font-weight: bold");
                            endPane.getChildren().add(answerScore);

                            //close button
                            Button closeMe = new Button("SAVE AND CLOSE");
                            closeMe.setPrefSize(180, 100);
                            closeMe.setTranslateX(20);
                            closeMe.setTranslateY(650);
                            closeMe.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
                            closeMe.setOnMouseClicked(event -> {
                                try {
                                    this.StringName = nameTextField.getText();
                                    this.StringScore = answerScore.getText();
                                    File myFile = new File("Records.txt");
                                    
                                    Scanner reader = new Scanner(myFile);
                                    while (reader.hasNext()) {
                                        this.temp += reader.nextLine();
                                    }
                                    
                                    FileWriter Records = new FileWriter(myFile);
                                    Records.write("\n"+this.temp + "" + this.StringName + ":" + this.StringScore+ " ");
                                    Records.close();
                                } catch (IOException ex) {
                                    System.err.println("error");
                                }
                                Stage now = (Stage) (scoreLabel.getScene().getWindow());
                                now.close();
                            });
                            endPane.getChildren().add(closeMe);

                            Stage Current = (Stage) player.getScene().getWindow();
                            Current.setScene(myScene);
                        }
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                            ++y;
                            score += 50;
                            if (y == 10) {
                                Label wonLabel = new Label("YOU WON!");
                                wonLabel.setTextAlignment(TextAlignment.CENTER);
                                wonLabel.setText("YOU WON!");
                                wonLabel.setTranslateX(160);
                                Glow myGlow = new Glow(0.9);
                                wonLabel.setEffect(myGlow);
                                wonLabel.setTranslateY(305);
                                wonLabel.setTextFill(Color.FORESTGREEN);
                                wonLabel.setStyle("-fx-font-size: 60px;  -fx-font-weight: bold");
                                endPane.getChildren().add(wonLabel);
                                //

                                // LabelName
                                Label nameLabel = new Label("NAME: ");
                                nameLabel.setTextFill(Color.ALICEBLUE);
                                nameLabel.setTranslateX(170);
                                nameLabel.setTranslateY(425);
                                nameLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold");
                                endPane.getChildren().add(nameLabel);

                                //TextBox
                                TextField nameTextField = new TextField();
                                nameTextField.setPromptText("enter your name");
                                nameTextField.setTranslateX(340);
                                nameTextField.setTranslateY(434);
                                nameTextField.setPrefSize(190, 40);
                                endPane.getChildren().add(nameTextField);

                                //scoreLabel
                                Label scoreLabel = new Label("SCORE: ");
                                scoreLabel.setTextFill(Color.ALICEBLUE);
                                scoreLabel.setTranslateX(170);
                                scoreLabel.setTranslateY(495);
                                scoreLabel.setStyle("-fx-font-size: 45px; -fx-font-weight: bold");
                                endPane.getChildren().add(scoreLabel);

                                //answerScoreLabel
                                Label answerScore = new Label();
                                answerScore.setText(Integer.toString(score));
                                Glow answerGlow = new Glow(0.9);
                                answerScore.setEffect(myGlow);
                                answerScore.setTextFill(Color.YELLOW);
                                answerScore.setTranslateX(360);
                                answerScore.setTranslateY(485);
                                answerScore.setStyle("-fx-font-size: 65px; -fx-font-weight: bold");
                                endPane.getChildren().add(answerScore);

                                //close button
                                Button closeMe = new Button("SAVE AND CLOSE");
                                closeMe.setPrefSize(180, 100);
                                closeMe.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
                                closeMe.setTranslateX(20);
                                closeMe.setTranslateY(650);
                                closeMe.setOnMouseClicked(event -> {
                                      try {
                                    this.StringName = nameTextField.getText();
                                    this.StringScore = answerScore.getText();
                                    File myFile = new File("Records.txt");
                                    
                                    Scanner reader = new Scanner(myFile);
                                    while (reader.hasNextLine()) {
                                        this.temp += reader.nextLine();
                                    }
                                    
                                    FileWriter Records = new FileWriter(myFile);
                                    Records.write("\n" + this.temp + " " + this.StringName + " : " + this.StringScore);
                                    Records.close();
                                } catch (IOException ex) {
                                    System.err.println("error");
                                }
                                    Stage now = (Stage) (scoreLabel.getScene().getWindow());
                                    now.close();
                                });
                                endPane.getChildren().add(closeMe);

                                Stage Current = (Stage) player.getScene().getWindow();
                                Current.setScene(myScene);

                            }
                        }
                    });

                    break;

                case "enemy":

                    if (t > 2) {
                        if (Math.random() < 0.2) {
                            shootEnemy(s);
                        }
                    }

                    break;
            }
        });

        root.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        sprites().forEach(u -> {
            if (u.getUserData().equals("PL")) {
                if (u.dead) {
                    System.out.println("sss");
                }
            }

        });

        if (this.specifyEN == 10) {
            System.out.println("bye bye");
        }
        if (this.specifyPL == 1) {
            System.out.println("hi bye");
        }

        if (t > 2) {
            t = 0;
        }
    }

    private void shootEnemy(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), who.type + "bullet", bulletEnemyImg);
        s.setUserData("ENSH");

        root.getChildren().add(s);

    }

    private void shootPlayer(Sprite who) {
        Sprite v = new Sprite((int) who.getTranslateX() + 20, (int) who.getTranslateY(), who.type + "bullet", bulletPlayerImg);
        v.setUserData("PLSH");
        root.getChildren().add(v);

    }

    Pane firstPane = new Pane();

    public class first {

        public Parent firstParent() {
            try {
                firstPane.setPrefSize(600, 800);
                Image firstPaneBC = new Image("/taw-spaaaaaaaaace.jpg");
                BackgroundImage firstPaneBCBC = new BackgroundImage(firstPaneBC, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                firstPane.setBackground(new Background(firstPaneBCBC));
                ImageView firstButton = new ImageView("/grey_button15.png");
                firstButton.setTranslateX(380);
                firstButton.setTranslateY(80);
                firstButton.setScaleX(1.22);
                firstButton.setScaleY(1.22);
                Label firstButtonLabel = new Label("START");
                firstButtonLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: red ; -fx-font-family: Titr; -fx-font-weight: bold");
                firstButtonLabel.setTranslateX(430);
                firstButtonLabel.setTranslateY(85);
                
                firstButton.setOnMouseClicked((event) -> {
                    Stage current = (Stage) (firstButton.getScene().getWindow());
                    current.setScene(scene);
                });
                firstButtonLabel.setOnMouseClicked((event) -> {
                    Stage current = (Stage) (firstButton.getScene().getWindow());
                    current.setScene(scene);
                });
                
                firstPane.getChildren().add(firstButton);
                firstPane.getChildren().add(firstButtonLabel);
                
                TextArea records = new TextArea();
                records.setPrefSize(300, 600);
                records.setTranslateX(50);
                records.setTranslateY(50);
                records.setVisible(false);
                records.setEditable(false);
                records.setStyle("-fx-font-size: 30px; -fx-font-fill: red; -fx-font-family: B-zar");
                String showLine = "SEE RECORDS: \n";
                File recorder = new File("Records.txt");
                Scanner readMe = new Scanner(recorder);
                while(readMe.hasNext()){
                    showLine += readMe.nextLine() + "";
                }
                records.setText(showLine);
                firstPane.getChildren().add(records);
                
                ImageView secondButton = new ImageView("/grey_button15.png");
                secondButton.setTranslateX(380);
                secondButton.setTranslateY(180);
                secondButton.setScaleX(1.22);
                secondButton.setScaleY(1.22);
                
                secondButton.setOnMouseClicked((Event) ->{
                    
                    records.setVisible(true);
                    
                });
                
                Label secondButtonLabel = new Label("SEE RECORDS");
                secondButtonLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: RED; -fx-font-family: Titr; -fx-font-weight: bold");
                secondButtonLabel.setTranslateX(390);
                secondButtonLabel.setTranslateY(185);
                secondButtonLabel.setOnMouseClicked((Event) ->{
                    records.setVisible(true);
                    
                });
                
                
                firstPane.getChildren().add(secondButton);
                firstPane.getChildren().add(secondButtonLabel);
                
                ImageView thirdButton = new ImageView("/grey_button15.png");
                thirdButton.setTranslateX(380);
                thirdButton.setTranslateY(275);
                thirdButton.setScaleX(1.22);
                thirdButton.setScaleY(1.22);
                Label ThirdButtonLabel = new Label("END");
                ThirdButtonLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: RED; -fx-font-family: Titr; -fx-font-weight: bold");
                ThirdButtonLabel.setTranslateX(440);
                ThirdButtonLabel.setTranslateY(280);
                thirdButton.setOnMouseClicked((event) -> {
                    
                    Stage tempScene = (Stage) secondButtonLabel.getScene().getWindow();
                    tempScene.close();
                });
                ThirdButtonLabel.setOnMouseClicked((event) -> {
                    Stage currnetOne = (Stage) thirdButton.getScene().getWindow();
                    currnetOne.close();
                });
                
                firstPane.getChildren().add(thirdButton);
                firstPane.getChildren().add(ThirdButtonLabel);
                
                return firstPane;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(galaxyWars.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    first ob1 = new first();
    Scene firstScene = new Scene(ob1.firstParent());
    Scene scene = new Scene(createContent());

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(firstScene);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case SPACE:
                    if (!(player.dead)) {
                        shootPlayer(player);
                    }
                    break;
            }
        });

        stage.show();

    }

    private static class Sprite extends ImageView {

        boolean dead = false;
        final String type;

        Sprite(int x, int y, String type, Image image) {

            super(image);
            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 7);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 7);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - 7);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + 7);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
