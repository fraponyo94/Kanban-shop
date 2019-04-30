package com.shop.production.shop.controller;


import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.component.Validation;
import com.shop.production.shop.entity.ProductComponent;

import com.shop.production.shop.repository.ProductComponentRepository;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;



@Controller
public class ComponentsController implements Initializable {
    private NumberFormat formatter = new DecimalFormat("#0.00");

    @Autowired
    private ProductComponentRepository productComponentRepository;
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Autowired
    private CreateView createView;

    @Autowired
    private Validation validation;


    @FXML
    private TextField componentName;

    @FXML
    private Label componentId;



    @FXML
    private MenuBar menuBar;



    @FXML
    private TextArea briefInfo;

    @FXML
    private TextField wholeSalePrice;





    @FXML
    private TableView<ProductComponent> tableView;

    @FXML
    private TableColumn<ProductComponent, String> colComponentName;

    @FXML
    private TableColumn<ProductComponent, String> colWholeSalePrice;


    @FXML
    private TableColumn<ProductComponent, Boolean> colEdit;


    private ObservableList<ProductComponent> componentInfos = FXCollections.observableArrayList();




    //Save Employee Object
    @FXML
    private  void saveJob(ActionEvent event) {
        if (validation.validate("Component Name",componentName.getText(),validation.getStrPattern())
        && validation.validate("Brief Description",briefInfo.getText(),validation.getStrPattern())
        && validation.validate("WholeSale Price",wholeSalePrice.getText(),validation.getDoublePattern())) {
            if (componentId.getText() == null || componentId.getText() == "") {
                ProductComponent componentInfo = new ProductComponent();
                componentInfo.setComponentName(componentName.getText());
                componentInfo.setDescription(briefInfo.getText());
                componentInfo.setWholeSalePrice(Double.parseDouble(wholeSalePrice.getText()));

                productComponentRepository.save(componentInfo);
                saveAlert(componentInfo);

            } else {
                ProductComponent componentInfo = productComponentRepository.findById(UUID.fromString(componentId.getText())).get();
                componentInfo.setComponentName(componentName.getText());
                componentInfo.setDescription(briefInfo.getText());
                componentInfo.setWholeSalePrice(Double.parseDouble(wholeSalePrice.getText()));

                productComponentRepository.save(componentInfo);
                componentId.setText("");


                updateAlert(componentInfo);
            }
        }


        clearFields();
        retrieveComponentData();


    }

    @FXML
    private void clear(ActionEvent event){
        clearFields();

    }

    //To clear fields once they are submitted
    private void clearFields() {
        componentName.setText(null);

         wholeSalePrice.setText(null);
        briefInfo.clear();


    }



    //Create alert on successful action
    private void saveAlert(ProductComponent customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Component saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer"+customerInfo.getComponentName()+" has been saved successfully ");
        alert.showAndWait();


    }
    //Allert on Update
    private void updateAlert(ProductComponent customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Product Component updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Component "+customerInfo.getComponentName()+" has been updated successfully ");
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
        retrieveComponentData();

    }


    //Set cell Value Factory for Table Columns
    private void initTableColProperties() {

        colComponentName.setCellValueFactory(new PropertyValueFactory<>("componentName"));
          colWholeSalePrice.setCellValueFactory(new PropertyValueFactory<>("wholeSalePrice"));


        colEdit.setCellFactory(cellFactory);

    }



    Callback<TableColumn<ProductComponent, Boolean>, TableCell<ProductComponent, Boolean>> cellFactory
        = new Callback<TableColumn<ProductComponent, Boolean>, TableCell<ProductComponent, Boolean>>() {
    @Override
    public TableCell<ProductComponent, Boolean> call(final TableColumn<ProductComponent, Boolean> param) {
        final TableCell<ProductComponent, Boolean> cell = new TableCell<ProductComponent, Boolean>() {
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
                        ProductComponent jobInfo = getTableView().getItems().get(getIndex());
                           updateCustomer(jobInfo);

                    });


                    setGraphic(edit);
                    edit.setAlignment(Pos.CENTER);
                    edit.setText("Edit");

                    //Disable employeeId from being displayed
                    componentId.setVisible(false);


                }
            }


            private void updateCustomer(ProductComponent jobInfo) {

                 componentId.setText(jobInfo.getId().toString());
                 componentName.setText(jobInfo.getComponentName());
                 briefInfo.setText(jobInfo.getDescription());

                 wholeSalePrice.setText(Double.toString(jobInfo.getWholeSalePrice()));


                }

        };
        return cell;
    }
};






    //Retrieve Job Data from the database
    private void retrieveComponentData(){
        componentInfos.clear();
        List<ProductComponent> jobInfoListList = new ArrayList<>();
        jobInfoListList = productComponentRepository.findAll();
        if (!jobInfoListList .isEmpty()){
            componentInfos.addAll(jobInfoListList);
            tableView.setItems(componentInfos);
        }
    }



    @FXML
    private void delete(ActionEvent event){
        List<ProductComponent> components = tableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected Components?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK) productComponentRepository.deleteInBatch(components);

        retrieveComponentData();
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
