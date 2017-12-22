package sample;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
/**
 * Created by Oliver on 12/4/2016.
 * Simple gui for navigation
 * Navigation implemented by storing and accessing SCENES in an exciting style
 * Can be used by just by calling its static method Navigation.handle_nav(3 params) in every Scene class in your application
 * Requires the master pane used in your scene class, primaryStage, the scene itself as inputs
 *
 */

public class Navigation{

    private static int counter = 0;
    private static ArrayList<Scene> list = new ArrayList<Scene>();;
    private static Navigation nav = null;

    /**
     * This method should be called foremost
     * Displays styled forward and back buttons for navigation
     * Handles click on forward and back buttons
     * @param p places navigation buttons at the top left corner of the specified pane
     * @param primaryStage specifies the overall window to work with
     * @param value        specifies the current scene to work with
     **/
    public static void handle_nav(HBox p, Stage primaryStage, Scene value){

        /* makes sure only one instance of Navigation is created */
        if(nav == null){
            nav = new Navigation();
        }

        /* adds the scene to the navigation list */
        nav.addPane(value);


        Button back = new Button("<");
        Button forward = new Button(">");
        back.setTextFill(Color.WHITE);
        forward.setTextFill(Color.WHITE);
        forward.setStyle("-fx-background-radius: 100; -fx-color: cornflowerblue; ");
        back.setStyle("-fx-background-radius: 100; -fx-color: cornflowerblue;");

        // handles click on the back and forward buttons
        back.setOnAction(event -> {
            try{
                if(value != null){
                    nav.addPane(value);
                    nav.counterUpByOne();
                }
                nav.setCaretToCurrent(value);
                primaryStage.setScene(nav.back());
                primaryStage.show();
            }
            catch(IndexOutOfBoundsException e){
                System.out.println(e.toString());
            }
        });

        forward.setOnAction(event -> {
            try{
                nav.setCaretToCurrent(value);
                primaryStage.setScene(nav.forward());
                primaryStage.show();
            }
            catch(IndexOutOfBoundsException e){
                System.out.println(e.toString());
            }
        });

        forward.setOnMouseEntered(e->{

            forward.setStyle("-fx-background-radius: 100; -fx-color: white;") ;
            forward.setTextFill(Color.BLACK);
        });
        forward.setOnMouseExited(e->{

            forward.setStyle("-fx-background-radius: 100; -fx-color: cornflowerblue;");
            forward.setTextFill(Color.WHITE);
        });
        back.setOnMouseEntered(e->{

            back.setStyle("-fx-background-radius: 100; -fx-color: white;") ;
            back.setTextFill(Color.BLACK);
        });
        back.setOnMouseExited(e->{

            back.setStyle("-fx-background-radius: 100; -fx-color: cornflowerblue;");
            back.setTextFill(Color.WHITE);
        });

        p.getChildren().addAll(back, forward);
    }


    /**
     * checks if the navigation hierarchy has changed
     * Checks for counter < list.size-1 and the input being equal to the scene at the current counter
     * if- yes, there's an adjustment
     * else , we check for non existence and add normally
     * @param value: Takes a scene as input
     */
    private static void addPane(Scene value){

        boolean go_ahead = true;
        if(counter < list.size()-1 && value != list.get(counter)){
            counterUpByOne();
            list.set(counter, value);
	    for(int i = list.size()-1; i > counter; i--){
		list.remove(i);
	    }
            go_ahead = false;
        }
        /* check for existence and add or not */
             check_and_add(value, go_ahead);
    }


    /**
     *
     * checks if given value is in list
     * adds the value if not
     * @param value
     * @param go_ahead approval from calling method to start cuz it couldn't take care of it
     */
    private static void check_and_add(Scene value, boolean go_ahead) {
        if(go_ahead){
            boolean check = true;
            for(Scene x: list){
                if(x != null){
                    if (value.getRoot() == x.getRoot()) {
                        check = false;
                        break;
                    }
                }
            }
            if(check){
                list.add(value);   counterUpByOne();
            }
        }
    }


    /**
     * @return returns the previous pane in the list
     * @throws IndexOutOfBoundsException iff no pane exists in the list
     */
    private   Scene back() throws IndexOutOfBoundsException{

           if(counter == 0){
               throw new IndexOutOfBoundsException("No pane in the list");
           }
           else {
               counter -= 1;
               return list.get(counter);
           }
    }


    /**
     * @return returns the next scene in the list
     * @throws IndexOutOfBoundsException if no scene exists in the list of list is full
     */
    private Scene forward() throws IndexOutOfBoundsException{

        if(list.size() == 0){
            throw new IndexOutOfBoundsException("No pane in the list");
        }
        else if(counter == list.size()){
            throw new IndexOutOfBoundsException("List is full");
        }
        else {
            counterUpByOne();
        }
            return list.get(counter);
    }


    /**
     * Sets the caret/counter to the specified current scene given
     * @param currentScene
     */
    public void setCaretToCurrent(Scene currentScene){
           int i = 0;
           for(Scene x : this.list){
               if(x != null){
                  if(currentScene.getRoot().equals(x.getRoot())) {
                      this.counter = i;
                      break;
                  }
                            }
               i += 1;
           }
    }

    /**
     * increments counter by 1
     */
    private static void counterUpByOne(){
        counter += 1;
    }

    /**
     * @return returns the counter
     */
    public int getCounter(){
        return counter;
    }

    /**
     * returns the navigation list
     */
    public ArrayList<Scene> getList(){ return list;}


}













