package minesweepergamereborn;


import java.util.Observable;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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
        
        btnEasy.setMaxWidth(100);
        btnMedium.setMaxWidth(100);
        btnHard.setMaxWidth(100);
        
        BorderPane.setAlignment(btnEasy, Pos.CENTER);
        BorderPane.setAlignment(btnMedium, Pos.CENTER);
        BorderPane.setAlignment(btnHard, Pos.CENTER);

        Text txtChooseDifficulty = new Text();
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
        
        Text txtCredits = new Text();
        txtCredits.setText("By Mélanie DUBREUIL, 3APP");
        BorderPane.setAlignment(txtCredits, Pos.CENTER);
        
        gridMenuPane.setAlignment(Pos.CENTER);
        menuPane.setTop(imgViewLogo);
        menuPane.setCenter(gridMenuPane);
        
       
        menuPane.setBottom(txtCredits);
        
        primaryStage.setTitle("MineSweeperGame || By Mélanie DUBREUIL");
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }
    
    public void launchGame(Stage primaryStage){
        Scene scene = new Scene(rootPane, 600, 850);
        StackPane sPane = new StackPane();
        primaryStage.setScene(scene);
        game.generateBoard();
        
        Image imgLogo = new Image("./assets/GameLogo.png");
        ImageView imgViewLogo = new ImageView(imgLogo);
        BorderPane.setAlignment(imgViewLogo, Pos.CENTER);
        
        Image imageSmiley = new Image("./assets/NeutralSmiley.PNG");
        ImageView imgViewSmiley = new ImageView(imageSmiley);
        
        Text endOfGame = new Text("");
        
        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(50));
        gPane.setHgap(1);
        gPane.setVgap(1);
        
        
        
        
        BorderPane topPane = new BorderPane();
        topPane.setPadding(new Insets(20, 20, 10, 0));
        
        topPane.setCenter(imgViewSmiley);
        
        BorderPane textPane = new BorderPane();
        
        
        // Board init
        Image image = new Image("./assets/HiddenBox.PNG");
        for(int i=0;i< 10;i++){
            for(int j=0;j< 10;j++){                
                ImageView imgView = new ImageView();
                imgView.setImage(image);
                gPane.add(imgView,i,j);
                
                imgView.setOnMouseClicked((MouseEvent event) -> {
                    Node source = (Node)event.getSource() ;
                    Integer colIndex = GridPane.getColumnIndex(source);
                    Integer rowIndex = GridPane.getRowIndex(source);
                    Box currentBox = game.getBox(colIndex, rowIndex);
                    Image imageSmiley1;
                    if (event.getButton() == MouseButton.SECONDARY) {
                        game.rightClic(currentBox);
                        imageSmiley1 = new Image ("./assets/NeutralSmiley.PNG");
                        imgViewSmiley.setImage(imageSmiley1);
                    }
                    if (event.getButton() == MouseButton.PRIMARY) {
                        if (currentBox.isFlag()) {
                            game.leftClic(currentBox);
                            imageSmiley1 = new Image ("./assets/NeutralSmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        } else if (currentBox.isTrapped()) {
                            game.discover(colIndex, rowIndex);
                            imageSmiley1 = new Image ("./assets/SadSmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        } else {
                            game.discover(colIndex, rowIndex);
                            imageSmiley1 = new Image ("./assets/HappySmiley.PNG");
                            imgViewSmiley.setImage(imageSmiley1);
                        }
                    }
                });
           }
        }
        
        game.addObserver( (Observable o, Object arg) -> {
            
            int nbOfNeighborTrapped = 0;
            Image image1;
            Image imageSmiley1;
            for (Node node : gPane.getChildren()) {
                if(!(node instanceof ImageView)){
                    continue;
                }
                int col = GridPane.getColumnIndex(node);
                int row = GridPane.getRowIndex(node);
                ImageView imgView = (ImageView)node;
                if (game.getBox(col,row).isFlag()) {
                    image1 = new Image("./assets/Flag.PNG");
                    imgView.setImage(image1);
                }
                if (!game.getBox(col,row).isFlag() && !game.getBox(col,row).isVisible()) {
                    image1 = new Image("./assets/HiddenBox.PNG");
                    imgView.setImage(image1);
                }
                if (game.getBox(col,row).isTrapped() && game.getBox(col, row).isVisible()) {
                    image1 = new Image("./assets/bomb.png");
                    imgView.setImage(image1);
                    endOfGame.setText("Perdu");
                    gPane.setDisable(true);
                } else {
                    if (game.getBox(col, row).isVisible()) {
                        nbOfNeighborTrapped = game.getBox(col, row).getNumberOfNeighborTrapped();
                        switch (nbOfNeighborTrapped) {
                            case 0:
                                image1 = new Image("./assets/EmptyBox.PNG");
                                imgView.setImage(image1);
                                break;
                            case 1:
                                image1 = new Image("./assets/One.PNG");
                                imgView.setImage(image1);
                                break;
                            case 2:
                                image1 = new Image("./assets/Two.PNG");
                                imgView.setImage(image1);
                                break;
                            case 3:
                                image1 = new Image("./assets/Three.PNG");
                                imgView.setImage(image1);
                                break;
                            case 4:
                                image1 = new Image("./assets/Four.PNG");
                                imgView.setImage(image1);
                                break;
                            case 5:
                                image1 = new Image("./assets/Five.PNG");
                                imgView.setImage(image1);
                                break;
                        }
                    }
                }
            }
            if ((game.getNb_box_discovered()) == (game.getNbRowColumns()*game.getNbRowColumns() - game.getNbMineTrapped())) {
                gPane.setDisable(true);
                imageSmiley1 = new Image ("./assets/HappySmiley.PNG");
                endOfGame.setText("Gagné");
                imgViewSmiley.setImage(imageSmiley1);
            } 
        });
        
        sPane.getChildren().add(gPane);
        sPane.getChildren().add(endOfGame);
        
        rootPane.setTop(topPane);
        rootPane.setCenter(sPane);
        rootPane.setBottom(imgViewLogo);
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }    
}
