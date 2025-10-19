package vibe.com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vibe.com.demo.MainApp;
import vibe.com.demo.model.user.User;
import vibe.com.demo.service.ServiceLocator;
import vibe.com.demo.service.game.GameProgressService;

public class ShopController implements BaseController {

    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();
    private User currentUser = ServiceLocator.getInstance().getAuthService().getCurrentUser();

    @FXML
    private VBox shopGroup;

    private MainApp mainApp;
    @FXML
    private Button btnBack;

    @FXML
    private HBox coinBox;

    @FXML
    private Label coinLabel;

    private List<Button> buyButtonList = new ArrayList<>();
    private List<String> idPurchasedPaddles = new ArrayList<>();
    private String idCurrentPaddle;
    private Button currentPaddleBtn;

    @FXML
    private void initialize() {
        setDataBinding();
        //init 
        for (Node buyButtonNode : shopGroup.lookupAll(".btn-buy")) {
            Button buyButton = (Button) buyButtonNode;
            buyButtonList.add(buyButton);
            System.out.println(buyButton.getId());
            buyButton.setOnMouseClicked(e -> {
                handleBuyButtonClick(buyButton);
                System.out.println(gameProgressService.getCoinsProperty());
            });
        }
        loadUserData();
    }

    public void loadUserData() {
        idPurchasedPaddles = gameProgressService.getPurchasedPaddles(currentUser);
        idCurrentPaddle = gameProgressService.getIdCurrentPaddle(currentUser);

        boolean found = false;
        for (Button buyButton : buyButtonList) {
            if (idPurchasedPaddles.contains(buyButton.getId())) {
                offEquiped(buyButton);
            }
            if (found) {
                continue;
            }
            if (idCurrentPaddle.equals(buyButton.getId())) {
                onEquiped(buyButton);
                currentPaddleBtn = buyButton;
            }
        }
    }

    public void onEquiped(Button buyButton) {
        buyButton.setDisable(true);
        buyButton.setText("Equiped");
        buyButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("equip"), false);
        buyButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("equiped"), true);
    }

    public void offEquiped(Button buyButton) {
        buyButton.setDisable(false);
        buyButton.setText("Equip");
        buyButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("equiped"), false);
        buyButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("equip"), true);
    }

    /**
     * Hàm xử lý sự kiện click
     */
    public void handleBuyButtonClick(Button clickedBuyButton) {
        boolean isSuccess = true;
        //xử lý lấy giá và trừ tiền nếu thỏa mãn điều kiện 
        if (clickedBuyButton.getText().trim().equals("Buy Now")) {
            VBox shopItem = (VBox) clickedBuyButton.getParent();//lấy ra cha của node 
            Label priceLabel = (Label) shopItem.lookup(".item-price");
            int cost = Integer.valueOf(priceLabel.getText());
            if (gameProgressService.canBuyPaddle(currentUser, cost)) {
                gameProgressService.buyPaddle(currentUser, Integer.valueOf(priceLabel.getText()));
                System.out.println("Them moi");
                gameProgressService.addNewPaddle(currentUser, clickedBuyButton.getId());
            } else {
                isSuccess = false;
            }
        }
        //xử lý chuyển đổi nếu thành công (thành công là khi đủ tiền mua nếu text = Buy Now hoặc đã mua rồi  sẵn thì thành công)
        if (isSuccess) {
            offEquiped(currentPaddleBtn);
            currentPaddleBtn = clickedBuyButton;
            onEquiped(currentPaddleBtn);
            gameProgressService.setIdCurrentPaddle(currentUser, currentPaddleBtn.getId());
        }

    }

    public void setDataBinding() {
        gameProgressService.setCoins(gameProgressService.getCoins(currentUser));
        coinLabel.textProperty().bind(gameProgressService.getCoinsProperty().asString());
    }

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    @FXML
    public void handleBackButton() {
        this.mainApp.loadLobbyView();
    }
}
