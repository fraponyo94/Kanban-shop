package com.shop.production.shop.controller;

import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.entity.Job;
import com.shop.production.shop.entity.enumerated.JobStages;
import com.shop.production.shop.repository.JobInfoRepository;
import com.shop.production.shop.views.CreateView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CloseOutController implements Initializable {

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private CreateView createView;

    @FXML
    private TableView<Job> jobTable;

    @FXML
    private TableColumn<Job, String> colJob;

    @FXML
    private TableColumn<Job, String > colDescription;

    @FXML
    private TableColumn<Job, String> colCloseOut;

    @FXML
    private Button Pro;

    @FXML
    private MenuBar menuBar;



    private ObservableList<Job> jobs = FXCollections.observableArrayList();


    //Retrieve Job Data from the database
    private void retrieveJobData(){
        jobs.clear();
        List<Job> jobList = new ArrayList<>();

        jobList = jobInfoRepository.findByCurrentStage(JobStages.Close_Out);
        if (!jobList.isEmpty()){

            jobs.addAll(jobList);
            jobTable.setItems(jobs);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableColProperties();
        retrieveJobData();
    }

    //Set cell Value Factory for Table Columns
    private void initTableColProperties() {

        colJob.setCellValueFactory(new PropertyValueFactory<>("jobName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("briefInfo"));
        colCloseOut.setCellValueFactory(new PropertyValueFactory<>("cost"));

    }



    @FXML
    void handleEmployeeAction(javafx.event.ActionEvent event) throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/employee1.fxml","Employess",stage  );
    }

    @FXML
    void handleCloseOutAction(javafx.event.ActionEvent event)  throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/closeOut.fxml","Close Out",stage  );

    }

    @FXML
    void handleComponentsAction(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/component.fxml","Components",stage  );

    }

    @FXML
    void handleCustomersAction(javafx.event.ActionEvent event) throws IOException{
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/customer.fxml","Customer",stage  );

    }


    @FXML
    void handleJobsAction(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/job.fxml","Jobs",stage  );

    }

    @FXML
    void handleProductionAction(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/production.fxml","Production",stage  );

    }

    @FXML
    void handleProductAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/product.fxml","Product",stage  );

    }




}
