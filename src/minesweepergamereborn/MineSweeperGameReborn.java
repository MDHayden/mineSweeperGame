package minesweepergamereborn;

import java.util.Observable;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Board2D;
import model.Box;

/**
 *
 * @author Mélanie DUBREUIL 3APP
 * 
 */

public class MineSweeperGameReborn extends Application {    
    Board2D game;
    BorderPane rootPane = new BorderPane();
   
    @Override
    public void start(Stage primaryStage) {
        BorderPane menuPane = new BorderPane();
        GridPane gridMenuPane = new GridPane();
        Scene menuScene = new Scene(menuPane, 600, 500);
        
        Button btnEasy = new Button();
        btnEasy.setText("EASY");
        btnEasy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = new Board2D(5,10);
                launchGame(primaryStage);
            }
        });
        
        Button btnMedium = new Button();
        btnMedium.setText("MEDIUM");
        btnMedium.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = new Board2D(15,10);
                launchGame(primaryStage);
            }
        });
        
        Button btnHard = new Button();
        btnHard.setText("HARD");
        btnHard.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = new Board2D(25,10);
                launchGame(primaryStage);
            }
        });
        
        btnEasy.setMaxWidth(200);
        btnEasy.setFont(Font.loadFont(MineSweeperGameReborn.class.getResourceAsStream("GameCube.ttf"), 20));
        btnMedium.setMaxWidth(200);
        btnMedium.setFont(Font.loadFont(MineSweeperGameReborn.class.getResourceAsStream("GameCube.ttf"), 20));
        btnHard.setMaxWidth(200);
        btnHard.setFont(Font.loadFont(MineSweeperGameReborn.class.getResourceAsStream("GameCube.ttf"), 20));
        
        BorderPane.setAlignment(btnEasy, Pos.CENTER);
        BorderPane.setAlignment(btnMedium, Pos.CENTER);
        BorderPane.setAlignment(btnHard, Pos.CENTER);

        Text txtChooseDifficulty = new Text();
        txtChooseDifficulty.setFont(Font.loadFont(MineSweeperGameReborn.class.getResourceAsStream("GameCube.ttf"), 20));
        txtChooseDifficulty.setText("Choose your difficulty level :");
        
        gridMenuPane.add(txtChooseDifficulty,0,0);
        gridMenuPane.add(btnEasy,0,1);
        gridMenuPane.add(btnMedium,0,2);
        gridMenuPane.add(btnHard,0,3);
        
        gridMenuPane.setHgap(10);
        gridMenuPane.setVgap(12);
        GridPane.setHalignment(btnEasy, HPos.CENTER);
        GridPane.setHalignment(btnMedium, HPos.CENTER);
        GridPane.setHalignment(btnHard, HPos.CENTER);
        
        Image imgLogo = new Image("./assets/GameLogo.png");
        ImageView imgViewLogo = new ImageView(imgLogo);
        BorderPane.setAlignment(imgViewLogo, Pos.CENTER);
        
        gridMenuPane.setAlignment(Pos.CENTER);
        menuPane.setTop(imgViewLogo);
        menuPane.setCenter(gridMenuPane);
                
        primaryStage.setTitle("MineSweeperGame || By Mélanie DUBREUIL");
        primaryStage.getIcons().add(new Image("./assets/bomb.png"));
        primaryStage.setX(600);
        primaryStage.setY(50);
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    
    /**
     * public void launchGame
     * @param primaryStage
     * Generates second window which is the game
     */
    public void launchGame(Stage primaryStage){
        Scene scene = new Scene(rootPane, 600, 700);
        primaryStage.setScene(scene);
        game.generateBoard();
        
        // TOP
        Image imageSmiley = new Image("./assets/NeutralSmiley.PNG");
        ImageView imgViewSmiley = new ImageView(imageSmiley);
        BorderPane borderPaneTop = new BorderPane();
        borderPaneTop.setPadding(new Insets(20, 20, 10, 0));
        borderPaneTop.setCenter(imgViewSmiley);
        
        StackPane sPaneCenter = new StackPane();
        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(50));
        gPane.setHgap(1);
        gPane.setVgap(1);
        Text endOfGame = new Text("");
        endOfGame.setFont(Font.loadFont(MineSweeperGameReborn.class.getResourceAsStream("GameCube.ttf"), 60));
        BorderPane.setAlignment(endOfGame, Pos.CENTER);
        BorderPane textPane = new BorderPane();
        
        // Board initiation
        Image image = new Image("./assets/HiddenBox.PNG");
        for(int i=0;i< 10;i++){
            for(int j=0;j< 10;j++){                
                ImageView imgView = new ImageView();
                imgView.setImage(image);
                gPane.add(imgView,i,j);
                
                game.getBox(i, j).addObserver((Observable o, Object arg) -> {
                    int nbOfNeighborTrapped = 0;
                    Image image1, imageSmiley1;
                    
                    for (Node node : gPane.getChildren()) {
                        
                        // we check that the node is an imageView : if not we don't want to apply a treatment on the box
                        if(!(node instanceof ImageView)){
                            continue;
                        }
                        
                        int col = GridPane.getColumnIndex(node);
                        int row = GridPane.getRowIndex(node);
                        ImageView caseView = (ImageView)node;

                        // Case : the box is a flag
                        if (game.getBox(col,row).isFlag()) {
                            image1 = new Image("./assets/Flag.PNG");
                            caseView.setImage(image1);
                        }

                        // Case : the box is hidden
                        if (!game.getBox(col,row).isFlag() && !game.getBox(col,row).isVisible()) {
                            image1 = new Image("./assets/HiddenBox.PNG");
                            caseView.setImage(image1);
                        }

                        // Case : the box is a mine
                        if (game.getBox(col,row).isTrapped() && game.getBox(col, row).isVisible()) {
                            image1 = new Image("./assets/bomb.png");
                            caseView.setImage(image1);
                            endOfGame.setFill(Color.RED);
                            endOfGame.setText("GAME OVER");
                            gPane.setDisable(true);
                        } else {
                            if (game.getBox(col, row).isVisible()) {
                                nbOfNeighborTrapped = game.getBox(col, row).getNumberOfNeighborTrapped();
                                switch (nbOfNeighborTrapped) {
                                    case 0:
                                        image1 = new Image("./assets/EmptyBox.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 1:
                                        image1 = new Image("./assets/One.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 2:
                                        image1 = new Image("./assets/Two.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 3:
                                        image1 = new Image("./assets/Three.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 4:
                                        image1 = new Image("./assets/Four.jpg");
                                        caseView.setImage(image1);
                                        break;
                                    case 5:
                                        image1 = new Image("./assets/Five.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 6:
                                        image1 = new Image("./assets/Six.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 7:
                                        image1 = new Image("./assets/Seven.PNG");
                                        caseView.setImage(image1);
                                        break;
                                    case 8:
                                        image1 = new Image("./assets/Eight.PNG");
                                        caseView.setImage(image1);
                                        break;
                                }
                            }
                        }
                    }
                    
                    // Case : player won the game
                    if ((game.getNb_box_discovered()) == (game.getNbRowColumns()*game.getNbRowColumns() - game.getNbMineTrapped())) {
                        gPane.setDisable(true);
                        imageSmiley1 = new Image ("./assets/HappySmiley.PNG");
                        endOfGame.setFill(Color.GREEN);
                        endOfGame.setText("YOU WON");
                        imgViewSmiley.setImage(imageSmiley1);
                    } 
                });
                
                imgView.setOnMouseClicked((MouseEvent event) -> {
                    Node source = (Node)event.getSource() ;
                    Integer colIndex = GridPane.getColumnIndex(source);
                    Integer rowIndex = GridPane.getRowIndex(source);
                    Box currentBox = game.getBox(colIndex, rowIndex);
                    Image imageSmiley1;
                    
                    if (event.getButton() == MouseButton.SECONDARY) {
                        currentBox.rightClic();
                        imageSmiley1 = new Image ("./assets/NeutralSmiley.PNG");
                        imgViewSmiley.setImage(imageSmiley1);
                    }
                    if (event.getButton() == MouseButton.PRIMARY){
                        if (currentBox.isFlag()) {
                            currentBox.leftClic();
                            imageSmiley1 = new Image ("./assets/NeutralSmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        } else if (currentBox.isTrapped()) {
                            game.getBox(colIndex, rowIndex).discover(currentBox);
                            imageSmiley1 = new Image ("./assets/SadSmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        } else {
                            game.getBox(colIndex, rowIndex).discover(currentBox);
                            imageSmiley1 = new Image ("./assets/HappySmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        }
                    }
                });
            }
        }
        sPaneCenter.getChildren().add(gPane);
        sPaneCenter.getChildren().add(endOfGame);
        
        rootPane.setTop(borderPaneTop);
        rootPane.setCenter(sPaneCenter);
    }

    /**
     * public static void main
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
