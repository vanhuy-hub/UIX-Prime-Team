package vibe.com.demo;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import vibe.com.demo.controller.BaseController;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.audio.AudioService;

public class MainApp extends Application {

    private Stage stage;
    private StackPane contentPane;//vung chua SubScene
    private AudioService audioService = ServiceLocator.getInstance().getAudioService();

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
        stage.show();
        //load Login scene đầu tiên 
        loadLoginView();
        audioService.playBackgroundMusic("background");
        audioService.setMusicVolume(0.5);
    }

    //lập trình tổng quát ~ generics 
    public <T extends BaseController> void loadView(String pathFxmlFile, String pathCssFile) {
        try {

            contentPane.getChildren().clear();//xoa cac phan tu truoc
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathFxmlFile));
            Parent view = loader.load();
            view.getStylesheets().add(getClass().getResource(pathCssFile).toExternalForm());
            contentPane.getChildren().setAll(view);
            //Lấy controller ~ chỉ những lớp được implements từ interface BaseController 
            //Pass reference this to LobbyController
            T controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLobbyView() {

        loadView("fxmlFiles/lobby.fxml", "assets/css/lobby.css");
    }

    public void loadLoginView() {
        loadView("fxmlFiles/login.fxml", "assets/css/login.css");
    }

    public void loadSignUpView() {
        loadView("fxmlFiles/signup.fxml", "assets/css/signup.css");
    }

    public void loadGameHelpView() {

        loadView("fxmlFiles/gameHelp.fxml", "assets/css/gamehelp.css");

    }

    public void loadLevelMenuView() {
        loadView("fxmlFiles/levelmenu.fxml", "assets/css/levelmenu.css");
    }

    public void loadGameView() {
        loadView("fxmlFiles/gameview.fxml", "assets/css/gameview.css");

    }

    public void loadShopView() {
        loadView("fxmlFiles/shop.fxml", "assets/css/shop.css");

    }

    public void loadRankingView() {
        loadView("fxmlFiles/ranking.fxml", "assets/css/ranking.css");
    }

    // day la ham main
    public static void main(String[] args) throws SQLException {
        launch(args);

    }

}
