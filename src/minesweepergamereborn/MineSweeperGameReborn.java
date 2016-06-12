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
        Scene scene = new Scene(rootPane, 650, 700);        
        game = new Board2D(3,10);
        game.generateBoard();
        
        Text text = new Text();
        text.setFont(new Font(20));
        text.setText("Welcome in the mineSweeperGame !");
        
        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(50));       
        
        
        // Board init
        Image image = new Image("./assets/HiddenBox.PNG");
        for(int i=0;i< 10;i++){
            for(int j=0;j< 10;j++){                
                ImageView imgView = new ImageView();
                imgView.setImage(image);
                gPane.add(imgView,i,j);
                
                imgView.setOnMouseClicked(new EventHandler <MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Node source = (Node)event.getSource() ;
                        Integer colIndex = GridPane.getColumnIndex(source);
                        Integer rowIndex = GridPane.getRowIndex(source);
                        Box currentBox = game.getBox(colIndex, rowIndex);               
                        
                        // Right click : we have to add a flag
                        if (event.getButton() == MouseButton.SECONDARY){
                            game.rightClic(currentBox);
                        }
                        
                        // Left click :
                        if (event.getButton() == MouseButton.PRIMARY){
                            if(currentBox.isFlag()){
                                game.leftClic(currentBox);
                            } else {
                                game.discover(colIndex, rowIndex);
                            }
                        }
                    }
                });
           }
        }
        
        game.addObserver( new Observer(){
             @Override
             public void update(Observable o, Object arg) {
                int nbOfNeighborTrapped = 0;
                Image image;
                
                for (Node node : gPane.getChildren()) {
                    if(!(node instanceof ImageView)){
                        continue;
                    }                    
                    int col = GridPane.getColumnIndex(node);
                    int row = GridPane.getRowIndex(node);
                    ImageView imgView = (ImageView)node;
                    
                    if(game.getBox(col,row).isFlag()){
                        image = new Image("./assets/Flag.PNG");
                        imgView.setImage(image);
                    }
                    
                    if(!game.getBox(col,row).isFlag() && !game.getBox(col,row).isVisible()){
                        image = new Image("./assets/HiddenBox.PNG");
                        imgView.setImage(image);
                    }
                        
                    if(game.getBox(col,row).isTrapped() && game.getBox(col, row).isVisible()){
                        image = new Image("./assets/bomb.png");
                        imgView.setImage(image);
                        text.setText("Perdu !!");
                        gPane.setDisable(true);
                    } else {
                        if(game.getBox(col, row).isVisible()) {
                            nbOfNeighborTrapped = game.getBox(col, row).getNumberOfNeighborTrapped();
                            switch (nbOfNeighborTrapped){
                                case 0 :                                        
                                    image = new Image("./assets/EmptyBox.PNG");
                                    imgView.setImage(image);
                                    break;
                                case 1 :
                                    image = new Image("./assets/One.PNG");
                                    imgView.setImage(image);
                                    break;
                                case 2 :
                                    image = new Image("./assets/Two.PNG");
                                    imgView.setImage(image);
                                    break;
                                case 3 :
                                    image = new Image("./assets/Three.PNG");
                                    imgView.setImage(image);
                                    break;
                                case 4 :
                                    image = new Image("./assets/Four.PNG");
                                    imgView.setImage(image);
                                    break;
                                case 5 :
                                    image = new Image("./assets/Five.PNG");
                                    imgView.setImage(image);
                                    break;
                            }
                        }
                    }
                }
                if((game.getNb_box_discovered()) == (game.getNbRowColumns()*game.getNbRowColumns() - game.getNbMineTrapped())){
                    text.setText("Bravo, vous avez gagné !!");
                    gPane.setDisable(true); 
                }
            } 
        });
        
        gPane.setHgap(1);
        gPane.setVgap(1);
        
        BorderPane textPane = new BorderPane();
        textPane.setCenter(text);
        
        rootPane.setCenter(gPane);
        rootPane.setBottom(textPane);
        primaryStage.setTitle("MineSweeperGame");
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
