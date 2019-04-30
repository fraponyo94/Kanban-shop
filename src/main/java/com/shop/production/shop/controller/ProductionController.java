package com.shop.production.shop.controller;

import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.entity.Job;
import com.shop.production.shop.entity.enumerated.JobStages;
import com.shop.production.shop.repository.JobInfoRepository;
import com.shop.production.shop.views.CreateView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ProductionController implements Initializable {

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private CreateView createView;


    @FXML
    private TableView<Job> checkOut;

    @FXML
    private TableColumn<Job, String> colJob;

    @FXML
    private TableColumn<Job, String> colDescription;

    @FXML
    private TableColumn<Job,Boolean> colCloseOut;


    @FXML
    private MenuBar menuBar;

    private ObservableList<Job> jobs = FXCollections.observableArrayList();


    //Retrieve Job Data from the database
    private void retrieveJobData(){
        jobs.clear();
        List<Job> jobList = new ArrayList<>();

        jobList = jobInfoRepository.findByCurrentStage(JobStages.Production);
        if (!jobList.isEmpty()){

            jobs.addAll(jobList);
            checkOut.setItems(jobs);
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
        colCloseOut.setCellFactory(cellFactory);

    }

    Callback<TableColumn<Job, Boolean>, TableCell<Job, Boolean>> cellFactory
            = new Callback<TableColumn<Job, Boolean>, TableCell<Job, Boolean>>() {
        @Override
        public TableCell<Job, Boolean> call(final TableColumn<Job, Boolean> param) {
            final TableCell<Job, Boolean> cell = new TableCell<Job, Boolean>() {
                //  Image editImage = new Image(getClass().getResourceAsStream("resources/icons/edit.png"));
                final Button close = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        close.setOnAction(e -> {
                            Job job = getTableView().getItems().get(getIndex());
                            updateJob(job);

                        });


                        setGraphic(close);
                        close.setAlignment(Pos.CENTER);
                        close.setText("Close Out");




                    }
                }


                private void updateJob(Job jobIn) {
                   jobIn.setCurrentStage(JobStages.Close_Out);
                   updateAlert(jobIn);


                }

            };
            return cell;
        }
    };


    //Allert on Update
    private void updateAlert(Job customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Job updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Job "+customerInfo.getJobName()+" moved to close-out successfully ");
        alert.showAndWait();


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
    void handleProductAction(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/product.fxml","Product",stage  );

    }


}
