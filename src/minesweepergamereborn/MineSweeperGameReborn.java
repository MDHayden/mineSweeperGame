package minesweepergamereborn;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    
    @Override
    public void start(Stage primaryStage) {      
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane, 600, 800);        
        game = new Board2D(20,10);
        game.generateBoard();
        
        Text text = new Text();
        text.setFont(new Font(20));
        text.setText("Welcome in the mineSweeperGame !");
        
        Image imageSmiley = new Image("./assets/NeutralSmiley.PNG");
        ImageView imgViewSmiley = new ImageView(imageSmiley);
        
        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(50));       
        
        
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
                    text.setText("Perdu !!");
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
                text.setText("Bravo, vous avez gagné !!");
                gPane.setDisable(true);
                imageSmiley1 = new Image ("./assets/HappySmiley.PNG");
                imgViewSmiley.setImage(imageSmiley1);
            } 
        });
        
        gPane.setHgap(1);
        gPane.setVgap(1);
        
        BorderPane topPane = new BorderPane();
        topPane.setPadding(new Insets(20, 20, 10, 0));
        
        topPane.setCenter(imgViewSmiley);
        
        BorderPane textPane = new BorderPane();
        textPane.setCenter(text);
        
        rootPane.setTop(topPane);
        rootPane.setCenter(gPane);
        rootPane.setBottom(textPane);
        rootPane.setPadding(new Insets(0, 20, 20, 0));
        primaryStage.setTitle("MineSweeperGame || By Mélanie DUBREUIL");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        launch(args);
    }    
}
