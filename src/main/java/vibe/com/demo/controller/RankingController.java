package vibe.com.demo.controller;

import java.util.List;

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

public class RankingController implements BaseController {

    @FXML
    private Label myTrophies;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblRank;

    @FXML
    private VBox leaderboardBox;

    /*======= GameProgress */
    private MainApp mainApp;
    private GameProgressService gameProgressService = ServiceLocator.getInstance().getGameProgressService();
    private User currentUser = ServiceLocator.getInstance().getAuthService().getCurrentUser();

    private List<User> topPlayers = gameProgressService.getTopPlayers();

    @Override
    public void setMainApp(MainApp mainApp) {
        // TODO Auto-generated method stub
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        // Hint: initialize() will be called when the associated FXML has been completely loaded.
        setUpCurrentUserData();
        setUpLeagueTable();
    }

    public void setUpCurrentUserData() {
        myTrophies.setText(String.valueOf(currentUser.getPlayerProgress().getTrophies()));
        lblRank.setText("#" + String.valueOf(gameProgressService.getUserRanking(currentUser)));
    }

    public void setUpLeagueTable() {
        int index = 0;
        for (Node node : leaderboardBox.lookupAll(".player-name")) {
            Label playerNameLabel = (Label) node;
            setRowData(playerNameLabel, index++);
        }
    }

    public void setRowData(Label playerNameLabel, int index) {
        HBox playerRow = (HBox) playerNameLabel.getParent();
        Label trophyValueLabel = (Label) playerRow.lookup(".trophy-value");
        playerNameLabel.setText(topPlayers.get(index).getPlayerName());
        trophyValueLabel.setText(String.valueOf(topPlayers.get(index).getPlayerProgress().getTrophies()));
        //nếu bản thân trong top 10 thì thêm css để nhận biết 
        if (playerNameLabel.getText().equals(currentUser.getPlayerName())) {
            playerNameLabel.setText("You");
            playerRow.getStyleClass().add("current-player");
        }
    }

    @FXML
    public void handleBacktoMenu() {
        this.mainApp.loadLobbyView();
    }
}
