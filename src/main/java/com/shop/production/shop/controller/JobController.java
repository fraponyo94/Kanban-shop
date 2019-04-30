package com.shop.production.shop.controller;


import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.component.Validation;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Controller
public class JobController implements Initializable {


    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private CreateView createView;

    @Autowired
    private Validation validation;


    @FXML
    private Button button;



    @FXML
    private TextField jobName;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label jobId;




    @FXML
    private TextArea briefInfo;



    @FXML
    private TableView<Job> tableView;

    @FXML
    private TableColumn<Job, String> colJobName;

    @FXML
    private TableColumn<Job, String> colCurrentStage;


    @FXML
    private TableColumn<Job, Boolean> colEdit;



    private ObservableList<Job> jobInfos = FXCollections.observableArrayList();






    //Save Employee Object
    @FXML
    private  void saveJob(ActionEvent event) {
        if (validation.validate("Job Name",jobName.getText(),validation.getStrPattern())
        && validation.validate("Brief Information",briefInfo.getText(),validation.getStrPattern())){
            if (jobId.getText() == null || jobId.getText() == "") {
                Job job = new Job();
                job.setJobName(jobName.getText());
                job.setBriefInfo(briefInfo.getText());
                job.setCurrentStage(JobStages.Pre_Production);

                jobInfoRepository.save(job);
                saveAlert(job);

            } else {
                Job job = jobInfoRepository.findById(UUID.fromString(jobId.getText())).get();

                job.setJobName(jobName.getText());
                job.setBriefInfo(briefInfo.getText());
                job.setCurrentStage(job.getCurrentStage());

                jobInfoRepository.save(job);
                jobId.setText("");


                updateAlert(job);
            }
         }


        clearFields();
        retrieveJobData();


    }

    @FXML
    private void clear(ActionEvent event){
        clearFields();

    }

    //To clear fields once they are submitted
    private void clearFields() {
        jobName.setText(null);
        briefInfo.clear();



    }

    //Go Back to Pre Production home page
    @FXML
    private void getComponent(ActionEvent event) throws IOException {
        Stage stage = (Stage)button.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/preProduction.fxml","Pre production",stage  );



    }


    //Create alert on successful action
    private void saveAlert(Job customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Job saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer"+customerInfo.getJobName()+" has been saved successfully ");
        alert.showAndWait();


    }
    //Allert on Update
    private void updateAlert(Job customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Job updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer"+customerInfo.getJobName()+" has been updated successfully ");
        alert.showAndWait();


    }

    //Initialize params in conntroller

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        initTableView();

    }






    private void initTableView(){
        initTableColProperties();
        retrieveJobData();

    }


    //Set cell Value Factory for Table Columns
    private void initTableColProperties() {

        colJobName.setCellValueFactory(new PropertyValueFactory<>("jobName"));
        colCurrentStage.setCellValueFactory(new PropertyValueFactory<>("currentStage"));
        colEdit.setCellFactory(cellFactory);

    }

Callback<TableColumn<Job, Boolean>, TableCell<Job, Boolean>> cellFactory
        = new Callback<TableColumn<Job, Boolean>, TableCell<Job, Boolean>>() {
    @Override
    public TableCell<Job, Boolean> call(final TableColumn<Job, Boolean> param) {
        final TableCell<Job, Boolean> cell = new TableCell<Job, Boolean>() {
            //  Image editImage = new Image(getClass().getResourceAsStream("resources/icons/edit.png"));
            final Button edit = new Button();

            @Override
            public void updateItem(Boolean check, boolean empty) {
                super.updateItem(check, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    edit.setOnAction(e -> {
                        Job job = getTableView().getItems().get(getIndex());
                           updateCustomer(job);

                    });


                    setGraphic(edit);
                    edit.setAlignment(Pos.CENTER);
                    edit.setText("Edit");

                    //Disable employeeId from being displayed
                    jobId.setVisible(false);


                }
            }


            private void updateCustomer(Job jobInfo) {

                 jobId.setText(jobInfo.getId().toString());
                 jobName.setText(jobInfo.getJobName());
                 briefInfo.setText(jobInfo.getBriefInfo());


                }

        };
        return cell;
    }
};






    //Retrieve Job Data from the database
    private void retrieveJobData(){
        jobInfos.clear();
        List<Job> jobInfoListList = new ArrayList<>();
        jobInfoListList = jobInfoRepository.findAll();
        if (!jobInfoListList .isEmpty()){
            jobInfos.addAll(jobInfoListList);
            tableView.setItems(jobInfos);
        }
    }



    @FXML
    private void delete(ActionEvent event){
        List<Job> customers = tableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected jobs?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK) jobInfoRepository.deleteInBatch(customers);

        retrieveJobData();
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
