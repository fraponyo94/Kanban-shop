package com.shop.production.shop.controller;


import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.component.Validation;
import com.shop.production.shop.entity.Employee;
import com.shop.production.shop.repository.EmployeeRepository;

import com.shop.production.shop.views.CreateView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class EmployeeController implements Initializable {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private CreateView createView;
    @Autowired
    private Validation validation;


    //Entity Fields
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;
    @FXML
    private Label employeeId;
    @FXML
    private Button clear;
    @FXML
    private Button back;



  //Table columns
    @FXML
    private TableColumn<Employee,String> colFirstName;
    @FXML
    private TableColumn<Employee,String > colLastName;
    @FXML
    private TableColumn<Employee,String > colEmail;
    @FXML
    private TableColumn<Employee,String>  colPhoneNumber;
    @FXML
    private TableColumn<Employee, Boolean> colEdit;

    @FXML
    private TableColumn<Employee,Button> colDel;

    @FXML
    private TableView<Employee> tableView;
    @FXML
    private MenuBar menuBar;

   private ObservableList<Employee> employees = FXCollections.observableArrayList();




   //Save Employee Object
    @FXML
    private  void saveEmployee(ActionEvent event){
        if (validation.validate("First Name",firstName.getText(),validation.getStrPattern())
          && validation.validate("Last Name",lastName.getText(),validation.getStrPattern())
         && validation.validate("Email",email.getText(),validation.getEmailPattern())
        && validation.validatePhoneNumber("Phone Number",phoneNumber.getText())) {
            if (employeeId.getText() == null || employeeId.getText() == "") {
                Employee employee = new Employee();
                employee.setEmail(email.getText());
                employee.setFirstName(firstName.getText());
                employee.setLastName(lastName.getText());
                employee.setPhoneNumber(phoneNumber.getText());
                repository.save(employee);
                saveAlert(employee);

            } else {
                Employee employee = repository.findById(email.getText()).get();
                employee.setFirstName(firstName.getText());
                employee.setLastName(lastName.getText());
                employee.setPhoneNumber(phoneNumber.getText());
                employeeId.setText("");
                repository.save(employee);

                updateAlert(employee);
            }
        }

        clearFields();
       retrieveEmployeeData();


    }


    @FXML
    private void clear(ActionEvent event){
       clearFields();

    }

    //To clear fields once they are submitted
    private void clearFields() {
        email.setText(null);
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();

    }


    //Create alert on successful action
    private void saveAlert(Employee employee){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employee saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Employee "+employee.getFirstName()+" has been saved successfully ");
        alert.showAndWait();


    }
    //Allert on Update
    private void updateAlert(Employee employee){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Employee updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The Employee with email"+employee.getEmail()+"has been updated successfully ");
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
       retrieveEmployeeData();

    }


    //Set cell Value Factory for Table Columns
    private void initTableColProperties() {

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEdit.setCellFactory(cellFactory);

    }

    Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>> cellFactory
           = new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
        @Override
        public TableCell<Employee, Boolean> call(final TableColumn<Employee, Boolean> param) {
            final TableCell<Employee, Boolean> cell = new TableCell<Employee, Boolean>() {
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
                            Employee employee = getTableView().getItems().get(getIndex());
                            updateEmployee(employee);

                        });


                        setGraphic(edit);
                        edit.setAlignment(Pos.CENTER);
                        edit.setText("Edit");

                        //Disable employeeId from being displayed
                        employeeId.setVisible(false);
                        //Disable email textField from being edited
                        //edit.setOnMouseClicked(email.setEditable(false));
                       email.setEditable(true);

                    }
                }


                private void updateEmployee(Employee employee) {
                    email.setEditable(false);
                    employeeId.setText(null);
                    employeeId.setText(employee.getEmail());
                    firstName.setText(employee.getFirstName());
                    lastName.setText(employee.getLastName());
                    email.setText(employee.getEmail());
                    phoneNumber.setText(employee.getPhoneNumber());

                }
            };
            return cell;
        }
    };




    //Retrieve Employees Data from the database
    private void retrieveEmployeeData(){
        employees.clear();
        List<Employee> employeeList = new ArrayList<>();
        employeeList = repository.findAll();
        if (!employeeList.isEmpty()){
            employees.addAll(employeeList);
            tableView.setItems(employees);
        }
    }



@FXML
private void deleteEmployee(ActionEvent event){
    List<Employee> employees = tableView.getSelectionModel().getSelectedItems();

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to delete selected employees?");
    Optional<ButtonType> action = alert.showAndWait();

    if(action.get() == ButtonType.OK) repository.deleteInBatch(employees);

      retrieveEmployeeData();
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
