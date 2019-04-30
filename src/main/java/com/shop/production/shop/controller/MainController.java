package com.shop.production.shop.controller;

import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.views.CreateView;
import com.sun.javafx.iio.ios.IosDescriptor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MainController implements Initializable {

    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private CreateView createView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button preProduction;

    @FXML
    private Button production;

    @FXML
    private Button closeOut;
    @FXML
    private MenuBar menuBar;


    @FXML
    private void goToCloseOut(ActionEvent event) throws IOException {
        Stage stage = (Stage)closeOut.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/closeOut.fxml","Close Out",stage  );
    }


    @FXML
    private void goToProduction(ActionEvent event) throws IOException {
        Stage stage = (Stage)production.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/production.fxml","Production",stage  );

    }


    @FXML
   private void gotoPreproduction(ActionEvent event) throws IOException {
        Stage stage = (Stage)preProduction.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/preProduction.fxml","Preproduction",stage  );


    }

    @FXML
    void handleEmployeeAction(ActionEvent event) throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/employee1.fxml","Employess",stage  );
    }

    @FXML
    void handleCloseOutAction(ActionEvent event)  throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/closeOut.fxml","Close Out",stage  );

    }

    @FXML
    void handleComponentsAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/component.fxml","Components",stage  );

    }

    @FXML
    void handleCustomersAction(ActionEvent event) throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/customer.fxml","Customer",stage  );

    }


    @FXML
    void handleJobsAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/job.fxml","Jobs",stage  );

    }

    @FXML
    void handleProductionAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/production.fxml","Production",stage  );

    }

    @FXML
    void handleProductAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/product.fxml","Product",stage  );

    }


}
