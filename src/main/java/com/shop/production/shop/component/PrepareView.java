//package com.shop.production.shop.component;
//
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
//
//@Component
//public class PrepareView {
//
//    private final Stage stage;
//
//    private final SpringFXMLLoader springFXMLLoader;
//
//    @Autowired
//    public PrepareView(SpringFXMLLoader springFXMLLoader) {
//        this.stage = new Stage();
//        this.springFXMLLoader = springFXMLLoader;
//    }
//
//    /*
//    * Prepare a class to be used for sitching scenes
//    * */
//    public void switchScene(String fxmlFilePath,String viewTitle) {
//        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(fxmlFilePath);
//        show(viewRootNodeHierarchy, viewTitle);
//    }
//
//    /*
//    * Stage show
//    * @Return void
//    * */
//    private void show(final Parent rootnode, String title) {
//        Scene scene = prepareScene(rootnode);
//
//        stage.setTitle(title);
//        stage.setScene(scene);
//        stage.sizeToScene();
//        stage.centerOnScreen();
//
//        try {
//            stage.show();
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
//    }
//
//    /*
//    * Prepare javfx scene
//    * @Return Scene
//    * */
//    private Scene prepareScene(Parent rootnode){
//        Scene scene = stage.getScene();
//
//        if (scene == null) {
//            scene = new Scene(rootnode);
//        }
//        scene.setRoot(rootnode);
//        return scene;
//    }
//
//    /**
//     * Loads the object hierarchy from a FXML document and returns to root node
//     * of that hierarchy.
//     *
//     * @return Parent root node of the FXML document hierarchy
//     */
//    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
//        Parent rootNode = null;
//        try {
//            rootNode = springFXMLLoader.load(fxmlFilePath);
//            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rootNode;
//    }
//}
