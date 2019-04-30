package com.shop.production.shop.controller;

import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.component.Validation;
import com.shop.production.shop.entity.CustomerInfo;

import com.shop.production.shop.repository.CustomerInfoRepository;
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
import java.util.*;

@Controller
public class CustomerController implements Initializable {


    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private CreateView createView;

    @Autowired
    private Validation validation;



    //Entity Fields
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label customerId;



    @FXML
    private MenuBar menuBar;



    //Table columns
    @FXML
    private TableColumn<CustomerInfo,String> colName;

    @FXML
    private TableColumn<CustomerInfo,String > colAddress;
    @FXML
    private TableColumn<CustomerInfo,String>  colPhoneNumber;
    @FXML
    private TableColumn<CustomerInfo, Boolean> colEdit;



    @FXML
    private TableView<CustomerInfo> tableView;


    private ObservableList<CustomerInfo> customerInfos = FXCollections.observableArrayList();




    //Save Employee Object
    @FXML
    private  void saveCustomer(ActionEvent event){
        if (validation.validate("Customer Name",name.getText(),validation.getStrPattern())
        && validation.validatePhoneNumber("Phone Number",phoneNumber.getText())
        && validation.emptyValidation("Address",address.getText() == null)) {


            if (customerId.getText() == null || customerId.getText() == "") {
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setPhoneNumber(phoneNumber.getText());
                customerInfo.setName(name.getText());
                customerInfo.setAddress(address.getText());
                customerInfoRepository.save(customerInfo);
                saveAlert(customerInfo);

            } else {
                CustomerInfo customerInfo = customerInfoRepository.findById(UUID.fromString(customerId.getText())).get();

                customerInfo.setPhoneNumber(phoneNumber.getText());
                customerInfo.setName(name.getText());
                customerInfo.setAddress(address.getText());
                customerId.setText("");
                customerInfoRepository.save(customerInfo);


                updateAlert(customerInfo);
            }

        }


        clearFields();
        retrieveCustomerData();


    }

    @FXML
    private void clear(ActionEvent event){
        clearFields();

    }

    //To clear fields once they are submitted
    private void clearFields() {
        address.setText(null);
        name.clear();

        phoneNumber.clear();

    }


    //Create alert on successful action
    private void saveAlert(CustomerInfo customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer"+customerInfo.getName()+" has been saved successfully ");
        alert.showAndWait();


    }
    //Allert on Update
    private void updateAlert(CustomerInfo customerInfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Customer"+customerInfo.getName()+" has been updated successfully ");
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
        retrieveCustomerData();

    }


    //Set cell Value Factory for Table Columns
    private void initTableColProperties() {

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEdit.setCellFactory(cellFactory);

    }

Callback<TableColumn<CustomerInfo, Boolean>, TableCell<CustomerInfo, Boolean>> cellFactory
        = new Callback<TableColumn<CustomerInfo, Boolean>, TableCell<CustomerInfo, Boolean>>() {
    @Override
    public TableCell<CustomerInfo, Boolean> call(final TableColumn<CustomerInfo, Boolean> param) {
        final TableCell<CustomerInfo, Boolean> cell = new TableCell<CustomerInfo, Boolean>() {
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
                        CustomerInfo customerInfo = getTableView().getItems().get(getIndex());
                           updateCustomer(customerInfo);

                    });

                    setGraphic(edit);
                    edit.setAlignment(Pos.CENTER);
                    edit.setText("Edit");

                    //Disable employeeId from being displayed
                    customerId.setVisible(false);


                }
            }


            private void updateCustomer(CustomerInfo customerInfo) {


                    name.setText(customerInfo.getName());
                    customerId.setText(customerInfo.getId().toString());
                    address.setText(customerInfo.getAddress());

                    phoneNumber.setText(customerInfo.getPhoneNumber());

                }

        };
        return cell;
    }
};






    //Retrieve Employees Data from the database
    private void retrieveCustomerData(){
        customerInfos.clear();
        List<CustomerInfo> customerInfoListList = new ArrayList<>();
        customerInfoListList = customerInfoRepository.findAll();
        if (!customerInfoListList .isEmpty()){
            customerInfos.addAll(customerInfoListList );
            tableView.setItems(customerInfos);
        }
    }



    @FXML
    private void delete(ActionEvent event){
        List<CustomerInfo> customers = tableView.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected customers?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK) customerInfoRepository.deleteInBatch(customers);

        retrieveCustomerData();
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
