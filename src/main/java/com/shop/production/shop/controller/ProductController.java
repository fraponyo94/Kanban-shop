package com.shop.production.shop.controller;



import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.entity.CustomerInfo;
import com.shop.production.shop.entity.Job;
import com.shop.production.shop.entity.Product;
import com.shop.production.shop.entity.ProductComponent;
import com.shop.production.shop.entity.enumerated.JobStages;
import com.shop.production.shop.repository.JobInfoRepository;
import com.shop.production.shop.repository.ProductComponentRepository;
import com.shop.production.shop.repository.ProductRepository;
import com.shop.production.shop.views.CreateView;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;

import java.util.*;

@Controller
public class ProductController implements Initializable {


    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private ProductComponentRepository componentRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private CreateView createView;


    @FXML
    private TableView<ProductComponent> componentTable;

    @FXML
    private TableColumn<ProductComponent, String> colComponentName;

    @FXML
    private TableColumn<ProductComponent, String> colQuantity;

    @FXML
    private TableColumn<ProductComponent, Boolean> add;

    @FXML
    private Label componentTitle;

    @FXML
    private Button create;

    @FXML
    private HBox h;

    @FXML
    private TextField componentName;

    @FXML
    private TextField quantity;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label componentLabel;

    @FXML
    private Label jobId;

    @FXML
    private Label componentId;

    @FXML
    private ComboBox<String> jobCombo;









    //private ObservableList<Job> jobInfos = FXCollections.observableArrayList();
    private ObservableList<String> jobList = FXCollections.observableArrayList();
    private ObservableList<ProductComponent> components = FXCollections.observableArrayList();


    //Initialize params in conntroller

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        componentTable.setVisible(false);
        h.setVisible(false);
        componentLabel.setText("");
        jobId.setVisible(false);
        componentId.setText(null);
        jobId.setText(null);
        retrieveJobData();

        initCol();

    }


    private void initCol() {
        colComponentName.setCellValueFactory(new PropertyValueFactory<>("componentName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        add.setCellFactory(cellFactory);


    }

    Callback<TableColumn<ProductComponent, Boolean>, TableCell<ProductComponent, Boolean>> cellFactory
            = new Callback<TableColumn<ProductComponent, Boolean>, TableCell<ProductComponent, Boolean>>() {
        @Override
        public TableCell<ProductComponent, Boolean> call(final TableColumn<ProductComponent, Boolean> param) {
            final TableCell<ProductComponent, Boolean> cell = new TableCell<ProductComponent, Boolean>() {

                final Button addProduct = new Button();

                @Override
                public void updateItem(Boolean check, boolean empty) {
                    super.updateItem(check, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        addProduct.setOnAction(e -> {
                            ProductComponent productComponent = getTableView().getItems().get(getIndex());
                            updateComponent(productComponent);

                        });


                        setGraphic(addProduct);
                        addProduct.setAlignment(Pos.CENTER);
                        addProduct.setText("Add");




                    }
                }


                private void updateComponent( ProductComponent productComponent) {
                    h.setVisible(true);
                    componentLabel.setVisible(true);

                    componentName.setEditable(false);
                    quantity.setText("0");
                    quantity.focusedProperty();
                    componentLabel.setText("Please specify quantity of the component selected");


                }


            };
            return cell;
        }
    };






    @FXML
     private void nextToCreateJob(ActionEvent event) {
        Job jobSelected = jobInfoRepository.findByJobName(jobCombo.getSelectionModel().getSelectedItem());
        if (jobSelected != null) {
            jobId.setText(jobSelected.getId().toString());
            populateComponent();
            componentTable.setVisible(true);

            componentTitle.setText("Select Component for " + jobSelected.getJobName() + " selected");

        }

    }


    @FXML
    private void addToProduct(ActionEvent event) {
        //Find Job
        if (jobId.getText() != null && componentId.getText() != null) {
            Job job = jobInfoRepository.findById(UUID.fromString(jobId.getText())).get();          //Find component
            ProductComponent productComponent = componentRepository.findById(UUID.fromString(componentId.getText())).get();

            productComponent.setQuantity(Double.parseDouble(quantity.getText()));
            Product product = new Product();

            //calculate cost
            Double cost = productComponent.getWholeSalePrice() + (13.4 / 100 * productComponent.getWholeSalePrice());
            product.setCost(cost);

            List<ProductComponent> list = new ArrayList<>();
            list.add(productComponent);
            product.setProductComponent(list);
            product.setJob(job);
            job.setCurrentStage(JobStages.Production);


            productRepository.save(product);
            h.setVisible(false);
            jobId.setText(null);
            componentId.setText(null);
            saveAlert();


        }else {
            failAlert();
        }
    }


    //Create alert on successful action
    private void saveAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The product has been saved successfully ");
        alert.showAndWait();


    }

    //Create alert on successful action
    private void failAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Either job Id or component Id missing,please provide to proceed");
        alert.showAndWait();


    }



        //Retrieve Job Data for all jobs at preproduction stage

        private void retrieveJobData () {
            jobList.clear();

            List<Job> jobInfoListList = new ArrayList<>();
            List<String> jobs = new ArrayList<>();

            jobInfoListList = jobInfoRepository.findAll();
            if (!jobInfoListList.isEmpty()) {
                for (Job job : jobInfoListList
                ) {
                    jobs.add(job.getJobName());
                }
                jobList.addAll(jobs);
                jobCombo.setItems(jobList);
            }
        }


        private void populateComponent () {
            components.clear();
            List<ProductComponent> productComponents = componentRepository.findAll();
            if (!productComponents.isEmpty()) {
                components.addAll(productComponents);
                componentTable.setItems(components);
            }
        }


    @FXML
    void handleEmployeeAction(ActionEvent event) throws IOException {
        Stage stage = (Stage)menuBar.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/employee1.fxml","Employees",stage  );
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











