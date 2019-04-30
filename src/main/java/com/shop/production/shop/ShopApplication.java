package com.shop.production.shop;


import com.shop.production.shop.component.SpringFXMLLoader;
import javafx.application.Application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShopApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(ShopApplication.class);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        rootNode = springContext.getBean(SpringFXMLLoader.class).load("/fxml/main.fxml");
        primaryStage.setTitle("KanBard");
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }


}
