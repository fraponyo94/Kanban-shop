package com.shop.production.shop.component;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validation {

    static final String strPattern =  "[a-zA-Z]+";
    static  final String emailPattern ="[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+";
    static  final String doublePattern ="[0-9]+(\\.){0,1}[0-9]*";

    /*
     * Validations
     */
    public boolean validate(String field, String value, String pattern){
        if(!value.isEmpty()){
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if(m.find() && m.group().equals(value)){
                return true;
            }else{
                validationAlert(field, false);
                return false;
            }
        }else{
            validationAlert(field, true);
            return false;
        }
    }

    public boolean emptyValidation(String field, boolean empty){
        if(!empty){
            return true;
        }else{
            validationAlert(field, true);
            return false;
        }
    }

    public void validationAlert(String field, boolean empty){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
            if(empty) {
                alert.setContentText("Please Enter "+ field);
            }
            else {
                alert.setContentText("Please Enter Valid "+ field);
            }

        alert.showAndWait();
    }

    public  boolean phoneNumber(String phoneNo) {

        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;

    }

    public  boolean validatePhoneNumber(String field, String value) {
        if (!value.isEmpty()) {
          boolean  phoneNumber = phoneNumber(value);
            if (phoneNumber) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    public  String getStrPattern() {
        return strPattern;
    }

    public String getEmailPattern() {
        return emailPattern;
    }

    public String getDoublePattern() {
        return doublePattern;
    }
}
