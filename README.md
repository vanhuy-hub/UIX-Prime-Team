D:.
├───.idea
│       .gitignore
│       code mau game arkanoid.iml
│       misc.xml
│       modules.xml
│       vcs.xml
│       workspace.xml
│       
└───UIX-Prime-Team
│   .gitignore
│   mvnw
│   mvnw.cmd
│   pom.xml
│   README.md
│   
├───.idea
│       .gitignore
│       compiler.xml
│       encodings.xml
│       jarRepositories.xml
│       misc.xml
│       vcs.xml
│       workspace.xml
│       
├───.mvn
│   └───wrapper
│           maven-wrapper.jar
│           maven-wrapper.properties
│           
├───src
│   └───main
│       ├───java
│       │   │   module-info.java
│       │   │   
│       │   └───vibe
│       │       └───com
│       │           └───demo
│       │               │   MainApp.java
│       │               │   
│       │               ├───controller
│       │               │       BaseController.java
│       │               │       FormController.java
│       │               │       GameHelpController.java
│       │               │       GameViewController.java
│       │               │       LevelMenuController.java
│       │               │       LobbyController.java
│       │               │       LoginController.java
│       │               │       RankingController.java
│       │               │       ShopController.java
│       │               │       SignupController.java
│       │               │       
│       │               ├───game
│       │               │   ├───animations
│       │               │   │       AnimationManager.java
│       │               │   │       AnimationType.java
│       │               │   │       BrickDestroyAnimation.java
│       │               │   │       SpriteAnimation.java
│       │               │   │       
│       │               │   ├───core
│       │               │   │       CollisionDetector.java
│       │               │   │       GameDataModel.java
│       │               │   │       GameEngine.java
│       │               │   │       GameManager.java
│       │               │   │       Renderer.java
│       │               │   │       
│       │               │   ├───levels
│       │               │   │       LevelConfig.java
│       │               │   │       LevelDesigner.java
│       │               │   │       LevelLoader.java
│       │               │   │       LevelManager.java
│       │               │   │       
│       │               │   ├───objects
│       │               │   │   ├───abstractions
│       │               │   │   │       GameObject.java
│       │               │   │   │       MovableObject.java
│       │               │   │   │       
│       │               │   │   ├───entities
│       │               │   │   │   ├───ball
│       │               │   │   │   │       Ball.java
│       │               │   │   │   │       BallManager.java
│       │               │   │   │   │       
│       │               │   │   │   ├───bricks
│       │               │   │   │   │       Brick.java
│       │               │   │   │   │       ExplosiveBrick.java
│       │               │   │   │   │       NormalBrick.java
│       │               │   │   │   │       StrongBrick.java
│       │               │   │   │   │       UnbreakableBrick.java
│       │               │   │   │   │       
│       │               │   │   │   ├───overlay
│       │               │   │   │   │       OverlayObject.java
│       │               │   │   │   │       
│       │               │   │   │   ├───paddle
│       │               │   │   │   │       Paddle.java
│       │               │   │   │   │       
│       │               │   │   │   └───powerups
│       │               │   │   │           CoinPowerUp.java
│       │               │   │   │           ExpandPaddlePowerUp.java
│       │               │   │   │           ExtraLifePowerUp.java
│       │               │   │   │           FireBallPowerUp.java
│       │               │   │   │           MultiplyBall.java
│       │               │   │   │           PowerUp.java
│       │               │   │   │           PowerUpManager.java
│       │               │   │   │           PowerUpType.java
│       │               │   │   │           SlowBallPowerUp.java
│       │               │   │   │           
│       │               │   │   └───factories
│       │               │   │           AnimationFactory.java
│       │               │   │           BrickFactory.java
│       │               │   │           PowerUpFactory.java
│       │               │   │           
│       │               │   └───utils
│       │               │           GameConstants.java
│       │               │           
│       │               ├───model
│       │               │   ├───game
│       │               │   │       PlayerProgress.java
│       │               │   │       
│       │               │   └───user
│       │               │           User.java
│       │               │           
│       │               └───service
│       │                   │   ServiceLocator.java
│       │                   │   
│       │                   ├───audio
│       │                   │       AudioService.java
│       │                   │       
│       │                   ├───auth
│       │                   │       AuthService.java
│       │                   │       
│       │                   ├───database
│       │                   │   ├───dao
│       │                   │   │   ├───abstraction
│       │                   │   │   │       DaoAbstraction.java
│       │                   │   │   │       
│       │                   │   │   ├───interfaces
│       │                   │   │   │       DaoIn.java
│       │                   │   │   │       
│       │                   │   │   └───objectdao
│       │                   │   │           UserDao.java
│       │                   │   │           
│       │                   │   └───utils
│       │                   │           DatabaseConnection.java
│       │                   │           
│       │                   ├───game
│       │                   │       GameProgressService.java
│       │                   │       
│       │                   └───user
│       │                           UserService.java
│       │                           
│       └───resources
│           └───vibe
│               └───com
│                   └───demo
│                       ├───assets
│                       │   ├───css
│                       │   │       gamehelp.css
│                       │   │       gameview.css
│                       │   │       general.css
│                       │   │       levelmenu.css
│                       │   │       lobby.css
│                       │   │       login.css
│                       │   │       ranking.css
│                       │   │       shop.css
│                       │   │       signup.css
│                       │   │       
│                       │   ├───img
│                       │   │       100.png
│                       │   │       arkanoidLogo2.png
│                       │   │       arrows.png
│                       │   │       background.jpg
│                       │   │       ball_fire.png
│                       │   │       ball_normal.png
│                       │   │       bkGame.png
│                       │   │       blast.png
│                       │   │       break_normal.png
│                       │   │       brick_bom - Copy.png
│                       │   │       brick_bom.png
│                       │   │       brick_break.png
│                       │   │       brick_normal - Copy.png
│                       │   │       brick_normal.png
│                       │   │       brick_normal_fire.png
│                       │   │       brick_strong - Copy.png
│                       │   │       brick_strong.png
│                       │   │       brick_unbreak - Copy.png
│                       │   │       brick_unbreak.png
│                       │   │       coin.png
│                       │   │       corner.png
│                       │   │       dog.png
│                       │   │       exp.png
│                       │   │       exp2.png
│                       │   │       exp_paddle.png
│                       │   │       fire.png
│                       │   │       flash.png
│                       │   │       game.png
│                       │   │       gamearea1.jpg
│                       │   │       gamerLobby.png
│                       │   │       game_big.png
│                       │   │       game_mini.png
│                       │   │       goc.png
│                       │   │       gun.png
│                       │   │       heart.png
│                       │   │       icon_cup_signup.png
│                       │   │       icon_game_login.png
│                       │   │       icon_password.png
│                       │   │       icon_user.png
│                       │   │       idea.png
│                       │   │       lobby.jpg
│                       │   │       lobbyAvatar.png
│                       │   │       love-always-wins.png
│                       │   │       magnet.png
│                       │   │       measure.png
│                       │   │       medal_bronze.png
│                       │   │       medal_gold.png
│                       │   │       medal_silver.png
│                       │   │       multiply_ball.png
│                       │   │       number-1.png
│                       │   │       number-3.png
│                       │   │       paddle1.png
│                       │   │       paddle2.png
│                       │   │       paddle3.png
│                       │   │       paddle4.png
│                       │   │       paddle5.png
│                       │   │       paddle6.png
│                       │   │       paddle7.png
│                       │   │       paddle8.png
│                       │   │       paddle9.png
│                       │   │       ping-pong.png
│                       │   │       power-up.png
│                       │   │       profile.png
│                       │   │       question.png
│                       │   │       ranking.png
│                       │   │       rocket.png
│                       │   │       ruler.png
│                       │   │       shield.png
│                       │   │       shopping-cart.png
│                       │   │       target.png
│                       │   │       trophy.png
│                       │   │       turtle.png
│                       │   │       two.png
│                       │   │       user-guide.png
│                       │   │       wall.png
│                       │   │       
│                       │   └───sounds
│                       │           arkanoid.mp3
│                       │           background.mp3
│                       │           clickSound.mp3
│                       │           collect.mp3
│                       │           explosion.mp3
│                       │           lobbyMusic.mp3
│                       │           lose.mp3
│                       │           mu.mp3
│                       │           playgame.mp3
│                       │           victory.mp3
│                       │           
│                       ├───db
│                       │       arkanoid.sql
│                       │       
│                       └───fxmlFiles
│                               gameHelp.fxml
│                               gameview.fxml
│                               levelmenu.fxml
│                               lobby.fxml
│                               login.fxml
│                               ranking.fxml
│                               shop.fxml
│                               signup.fxml
│                               
└───target
├───classes
│   │   module-info.class
│   │   
│   └───vibe
│       └───com
│           └───demo
│               │   MainApp.class
│               │   
│               ├───assets
│               │   ├───css
│               │   │       gamehelp.css
│               │   │       gameview.css
│               │   │       general.css
│               │   │       levelmenu.css
│               │   │       lobby.css
│               │   │       login.css
│               │   │       ranking.css
│               │   │       shop.css
│               │   │       signup.css
│               │   │       
│               │   ├───img
│               │   │       100.png
│               │   │       arkanoidLogo2.png
│               │   │       arrows.png
│               │   │       background.jpg
│               │   │       ball_fire.png
│               │   │       ball_normal.png
│               │   │       bkGame.png
│               │   │       blast.png
│               │   │       break_normal.png
│               │   │       brick_bom - Copy.png
│               │   │       brick_bom.png
│               │   │       brick_break.png
│               │   │       brick_normal - Copy.png
│               │   │       brick_normal.png
│               │   │       brick_normal_fire.png
│               │   │       brick_strong - Copy.png
│               │   │       brick_strong.png
│               │   │       brick_unbreak - Copy.png
│               │   │       brick_unbreak.png
│               │   │       coin.png
│               │   │       corner.png
│               │   │       dog.png
│               │   │       exp.png
│               │   │       exp2.png
│               │   │       exp_paddle.png
│               │   │       fire.png
│               │   │       flash.png
│               │   │       game.png
│               │   │       gamearea1.jpg
│               │   │       gamerLobby.png
│               │   │       game_big.png
│               │   │       game_mini.png
│               │   │       goc.png
│               │   │       gun.png
│               │   │       heart.png
│               │   │       icon_cup_signup.png
│               │   │       icon_game_login.png
│               │   │       icon_password.png
│               │   │       icon_user.png
│               │   │       idea.png
│               │   │       lobby.jpg
│               │   │       lobbyAvatar.png
│               │   │       love-always-wins.png
│               │   │       magnet.png
│               │   │       measure.png
│               │   │       medal_bronze.png
│               │   │       medal_gold.png
│               │   │       medal_silver.png
│               │   │       multiply_ball.png
│               │   │       number-1.png
│               │   │       number-3.png
│               │   │       paddle1.png
│               │   │       paddle2.png
│               │   │       paddle3.png
│               │   │       paddle4.png
│               │   │       paddle5.png
│               │   │       paddle6.png
│               │   │       paddle7.png
│               │   │       paddle8.png
│               │   │       paddle9.png
│               │   │       ping-pong.png
│               │   │       power-up.png
│               │   │       profile.png
│               │   │       question.png
│               │   │       ranking.png
│               │   │       rocket.png
│               │   │       ruler.png
│               │   │       shield.png
│               │   │       shopping-cart.png
│               │   │       target.png
│               │   │       trophy.png
│               │   │       turtle.png
│               │   │       two.png
│               │   │       user-guide.png
│               │   │       wall.png
│               │   │       
│               │   └───sounds
│               │           clickSound.mp3
│               │           fail.mp3
│               │           gold claim.mp3
│               │           lobbyMusic.mp3
│               │           lose game.mp3
│               │           mu.mp3
│               │           playgame.mp3
│               │           victory sound.mp3
│               │           
│               ├───controller
│               │       BaseController.class
│               │       FormController.class
│               │       GameHelpController.class
│               │       GameViewController.class
│               │       LevelMenuController.class
│               │       LobbyController.class
│               │       LoginController.class
│               │       RankingController.class
│               │       ShopController.class
│               │       SignupController.class
│               │       
│               ├───db
│               │       arkanoid.sql
│               │       
│               ├───fxmlFiles
│               │       gameHelp.fxml
│               │       gameview.fxml
│               │       levelmenu.fxml
│               │       lobby.fxml
│               │       login.fxml
│               │       ranking.fxml
│               │       shop.fxml
│               │       signup.fxml
│               │       
│               ├───game
│               │   ├───animations
│               │   │       AnimationManager.class
│               │   │       AnimationType.class
│               │   │       BallTrailAnimation.class
│               │   │       BrickDestroyAnimation.class
│               │   │       GameAnimation.class
│               │   │       PaddleDestroyAnimation.class
│               │   │       Particle.class
│               │   │       SpriteAnimation.class
│               │   │       TrailParticle.class
│               │   │       
│               │   ├───core
│               │   │       CollisionDetector$CollisionSide.class
│               │   │       CollisionDetector.class
│               │   │       GameDataModel.class
│               │   │       GameEngine$1.class
│               │   │       GameEngine.class
│               │   │       GameManager$GameState.class
│               │   │       GameManager.class
│               │   │       Renderer.class
│               │   │       
│               │   ├───levels
│               │   │       LevelConfig.class
│               │   │       LevelDesigner.class
│               │   │       LevelLoader.class
│               │   │       LevelManager.class
│               │   │       
│               │   ├───objects
│               │   │   ├───abstractions
│               │   │   │       GameObject.class
│               │   │   │       MovableObject.class
│               │   │   │       
│               │   │   ├───entities
│               │   │   │   ├───ball
│               │   │   │   │       Ball.class
│               │   │   │   │       BallManager.class
│               │   │   │   │       
│               │   │   │   ├───bricks
│               │   │   │   │       Brick.class
│               │   │   │   │       ExplosiveBrick.class
│               │   │   │   │       NormalBrick.class
│               │   │   │   │       StrongBrick.class
│               │   │   │   │       UnbreakableBrick.class
│               │   │   │   │       
│               │   │   │   ├───info
│               │   │   │   │       CoinInfo.class
│               │   │   │   │       GameInfoManager.class
│               │   │   │   │       InfoObject.class
│               │   │   │   │       LivesInfo.class
│               │   │   │   │       TimeInfo.class
│               │   │   │   │       
│               │   │   │   ├───overlay
│               │   │   │   │       OverlayObject.class
│               │   │   │   │       
│               │   │   │   ├───paddle
│               │   │   │   │       Paddle.class
│               │   │   │   │       
│               │   │   │   └───powerups
│               │   │   │           CoinPowerUp.class
│               │   │   │           ExpandPaddlePowerUp.class
│               │   │   │           ExtraLifePowerUp.class
│               │   │   │           FireBallPowerUp.class
│               │   │   │           LaserPowerUp.class
│               │   │   │           MagnetPowerUp.class
│               │   │   │           MultiplyBall.class
│               │   │   │           PowerUp.class
│               │   │   │           PowerUpManager.class
│               │   │   │           PowerUpType.class
│               │   │   │           SlowBallPowerUp.class
│               │   │   │           
│               │   │   └───factories
│               │   │           BrickFactory.class
│               │   │           PowerUpFactory.class
│               │   │           
│               │   └───utils
│               │           GameConstants.class
│               │           
│               ├───model
│               │   ├───game
│               │   │       PaddleItem.class
│               │   │       PlayerProgress.class
│               │   │       
│               │   └───user
│               │           User.class
│               │           
│               └───service
│                   │   ServiceLocator.class
│                   │   
│                   ├───audio
│                   │       AudioService.class
│                   │       
│                   ├───auth
│                   │       AuthService.class
│                   │       
│                   ├───database
│                   │   ├───dao
│                   │   │   ├───abstraction
│                   │   │   │       DaoAbstraction.class
│                   │   │   │       
│                   │   │   ├───interfaces
│                   │   │   │       DaoIn.class
│                   │   │   │       
│                   │   │   └───objectdao
│                   │   │           PaddleDao.class
│                   │   │           UserDao.class
│                   │   │           
│                   │   └───utils
│                   │           DatabaseConnection.class
│                   │           
│                   ├───game
│                   │       GameProgressService.class
│                   │       
│                   ├───shop
│                   │       ShopService.class
│                   │       
│                   └───user
│                           UserService.class
│                           
├───generated-sources
│   └───annotations
└───test-classes