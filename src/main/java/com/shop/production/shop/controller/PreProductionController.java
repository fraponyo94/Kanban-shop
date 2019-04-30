package com.shop.production.shop.controller;

import com.shop.production.shop.component.SpringFXMLLoader;
import com.shop.production.shop.entity.CustomerInfo;
import com.shop.production.shop.entity.Employee;
import com.shop.production.shop.entity.Job;
import com.shop.production.shop.entity.ProductComponent;
import com.shop.production.shop.repository.CustomerInfoRepository;
import com.shop.production.shop.repository.EmployeeRepository;
import com.shop.production.shop.repository.JobInfoRepository;
import com.shop.production.shop.repository.ProductComponentRepository;
import com.shop.production.shop.views.CreateView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class PreProductionController implements Initializable {
    private CustomerInfoRepository customerInfoRepository;
    private EmployeeRepository employeeRepository;
    private JobInfoRepository jobInfoRepository;
    private ProductComponentRepository componentRepository;

    @Autowired
    public PreProductionController(CustomerInfoRepository customerInfoRepository, EmployeeRepository employeeRepository, JobInfoRepository jobInfoRepository, ProductComponentRepository componentRepository) {
        this.customerInfoRepository = customerInfoRepository;
        this.employeeRepository = employeeRepository;
        this.jobInfoRepository = jobInfoRepository;
        this.componentRepository = componentRepository;
    }

    @Autowired
    private SpringFXMLLoader springFXMLLoader;
    @Autowired
    private CreateView createView;



    @FXML
    private MenuBar menuBar;

    @FXML
    private Button employees;

    @FXML
    private Button customer;

    @FXML
    private Button JOB;



    @FXML
    private Button component;
    @FXML
    private Button product;





    private List<CustomerInfo> customerInfoList =null;
    private List<Job> jobs = null;
    private List<Employee> employeess = null;
    private List<ProductComponent> productComponents = null;
    /*
     * Check if Initialise parameters
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerInfoList = customerInfoRepository.findAll();
        jobs =jobInfoRepository.findAll();
        employeess = employeeRepository.findAll();
        productComponents = componentRepository.findAll();

    }

    //Create alert on successful action
    private void saveAlert(String object){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missing Parameter");
        alert.setHeaderText(null);
        alert.setContentText("Initial "+object+" Missing,please provide first before proceeding");
        alert.showAndWait();



    }


   /*
   * AddComponent
   * @Return void
   * */
    @FXML
    void addComponent(ActionEvent event) throws IOException {
        Stage stage = (Stage)component.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/component.fxml","Component",stage  );

    }

    /*
    * Add component
    * @Return void
    * */
    @FXML
    void addCustomer(ActionEvent event)  throws IOException{
        Stage stage = (Stage)customer.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/customer.fxml","Customers",stage  );

    }

    @FXML
    void addEmployess(ActionEvent event) throws IOException{
        Stage stage = (Stage)employees.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/employee1.fxml","Employess",stage  );


    }

    @FXML
    void addJob(ActionEvent event)  throws IOException{
        Stage stage = (Stage)JOB.getScene().getWindow();//event.getSource()).getScene().getWindow();
        createView.createView(springFXMLLoader,"/fxml/job.fxml","Jobs",stage  );

    }


    @FXML
    void createProduct(ActionEvent event) throws IOException{
        if(employeess.isEmpty()){

            saveAlert("Employee");
        }else if (jobs.isEmpty()){
            saveAlert("Job");
        }else if (customerInfoList.isEmpty()){
            saveAlert("Customer");
        }else if(productComponents.isEmpty()){
            saveAlert("Product Components");
        }else {
            Stage stage = (Stage)product.getScene().getWindow();//event.getSource()).getScene().getWindow();
            createView.createView(springFXMLLoader,"/fxml/product.fxml","Product",stage  );
        }

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
