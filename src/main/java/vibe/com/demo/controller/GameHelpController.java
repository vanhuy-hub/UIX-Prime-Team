package vibe.com.demo.controller;

import vibe.com.demo.MainApp;

public class GameHelpController implements BaseController {

    private MainApp mainApp;

    @Override
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
