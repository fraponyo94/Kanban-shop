package com.shop.production.shop.views;

import com.shop.production.shop.component.SpringFXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CreateView {

    public void createView(SpringFXMLLoader springFXMLLoader,String fileName,String title ,Stage stage) throws IOException{
        Parent root = springFXMLLoader.load(fileName);
        Stage stage1 = stage;
         stage.setTitle(title);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }


}
