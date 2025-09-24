package vibe.com.demo;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import vibe.com.demo.controller.LoginController;
import vibe.com.demo.controller.SignupController;

public class MainApp extends Application {

    private Stage stage;
    private StackPane contentPane;//vung chua SubScene

    @Override
    public void start(Stage stage) {
        this.stage = stage;//gán stage của chương trình trong start Method là giá trị của trường stage ~ stage của đối tượng MainApp, đẻ khi import sang Controller còn dùng được stage này truy cập vào Stage chính của ứng dụng hiển thị lên  
        Image icon = new Image(getClass().getResourceAsStream("assets/img/arkanoidLogo2.png"));
        stage.getIcons().add(icon);
        contentPane = new StackPane();
        contentPane.getStyleClass().add("root-background");
        contentPane.getStylesheets().add(getClass().getResource("assets/css/general.css").toExternalForm());

        Scene mainScene = new Scene(contentPane, 1300, 800);
        stage.setScene(mainScene);
        loadLoginView();
        stage.show();

        //load Login scene đầu tiên 
    }

    public void loadLoginView() {
        try {
            contentPane.getChildren().clear();//xóa các node trước để chỉ hiển thị Login-Form 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/login.fxml"));
            Parent loginContent = loader.load();
            loginContent.getStylesheets().add(getClass().getResource("assets/css/login.css").toExternalForm());
            contentPane.getChildren().setAll(loginContent);
            //Pass Reference cho controller 
            LoginController loginController = loader.getController(); // Lấy controller ĐÃ ĐƯỢC TẠO
            loginController.setMainApp(this); // Truyền reference
            //Truyền tham chiếu đối tượng MainApp hiện tại đến hàm khởi tạo của LoginController để có thể switch 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSignUpView() {
        try {
            contentPane.getChildren().clear();//xóa các node trước để chỉ hiển thị SignUp-Form 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/signup.fxml"));
            Parent signUpContent = loader.load();
            signUpContent.getStylesheets().add(getClass().getResource("assets/css/signup.css").toExternalForm());
            contentPane.getChildren().add(signUpContent);
            //Truyền tham chiếu đối tượng MainApp hiện tại đến hàm khởi tạo của LoginController để có thể switch 
            SignupController signupController = loader.getController();
            signupController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // day la ham main
    public static void main(String[] args) {
        launch();
    }
}
