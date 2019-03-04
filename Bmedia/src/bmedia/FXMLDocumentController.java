/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmedia;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


/**
 *
 * @author Bibhusmita
 */
public class FXMLDocumentController implements Initializable {
    
    
    private MediaPlayer mediaplayer;
    
    
    @FXML
    private ToggleButton mute,playPause;

        
    @FXML
    private MediaView mediaView;
    
    
    @FXML
    private Slider volm; 
    
    @FXML
    private Slider seeks; 
    
    
    private String filepath;
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
     FileChooser filechooser =new FileChooser();
     FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
        filechooser.getExtensionFilters().add(filter);
        File file = filechooser.showOpenDialog(null);
        filepath =file.toURI().toString();
        
        if( filepath != null)
        {
        Media media = new Media(filepath);
        mediaplayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaplayer);
               DoubleProperty width =mediaView.fitWidthProperty();
               DoubleProperty hight =mediaView.fitHeightProperty();
        
               width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
               hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));
               
               volm.setValue(mediaplayer.getVolume()*100);
               volm.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaplayer.setVolume(volm.getValue()/100);
            }
        });
               
            mediaplayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                seeks.setValue(newValue.toMinutes());
        });
        
            seeks.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                 mediaplayer.seek(Duration.minutes(seeks.getValue()));
            }
        });
        
            
        mute.setOnAction(ae ->{
          if(mute.isSelected()==false)
          mediaplayer.setMute(true);
      else
           mediaplayer.setMute(false);
          
      });    
            
        
        mediaplayer.play();
        }
        
    }
    
        
    @FXML
    private void stopVideo(ActionEvent ae)
    {
        mediaplayer.stop();
    }
    
    @FXML
    private void fastVideo(ActionEvent ae)
    {
        mediaplayer.setRate(1.5);
    }
    
    @FXML
    private void fasterVideo(ActionEvent ae)
    {
        mediaplayer.setRate(2);
    }
    
    @FXML
    private void slowVideo(ActionEvent ae)
    {
        mediaplayer.setRate(.75);
    }
    
    @FXML
    private void slowerVideo(ActionEvent ae)
    {
        mediaplayer.setRate(.5);
    }
    
    @FXML
    private void exitVideo(ActionEvent ae)
    {
        System.exit(0);
    }
    
       
     @FXML
    private void playPauseVideo(ActionEvent e)
    {
       playPause.setOnAction(ae ->{
          mediaplayer.setRate(1);
          if(playPause.isSelected()==false)
          mediaplayer.pause();
      else
           mediaplayer.play();
          
      });        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
