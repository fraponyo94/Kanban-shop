package com.shop.production.shop.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpringFXMLLoader {

    @Autowired
    private ApplicationContext springContext ;

    public Parent load(String resourceName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
        loader.setControllerFactory(springContext::getBean);
        return loader.load();
    }
}