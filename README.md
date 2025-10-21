<h1 align="center" style="color:#5A67D8;">🎮 VIBE Game Project</h1>

<p align="center">
    <em>Một dự án game JavaFX được tổ chức theo mô hình module rõ ràng và có khả năng mở rộng cao.</em>
</p>

<hr>

<h2>📁 Cấu trúc thư mục</h2>

<div style="background-color:#1e1e1e; color:#dcdcdc; font-family:Consolas, monospace; padding:16px; border-radius:8px; line-height:1.5;">
├───<span style="color:#4ec9b0;">src</span><br>
│&nbsp;&nbsp;├───<span style="color:#4ec9b0;">main</span><br>
│&nbsp;&nbsp;│&nbsp;&nbsp;├───<span style="color:#4ec9b0;">java</span><br>
│&nbsp;&nbsp;│&nbsp;&nbsp;│&nbsp;&nbsp;│&nbsp;&nbsp;module-info.java<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;│&nbsp;&nbsp;<span style="color:#9cdcfe;">└───vibe/com/demo</span><br>
│&nbsp;&nbsp;│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├───<span style="color:#dcdcaa;">MainApp.java</span><br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├───controller<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;Chứa các controller quản lý giao diện: LoginController, ShopController, GameViewController,...<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├───game<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───animations → Xử lý hiệu ứng (BallTrail, BrickDestroy, ...)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───core → Thành phần cốt lõi của engine game (Engine, Renderer, CollisionDetector)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───levels → Cấu hình và quản lý màn chơi (LevelConfig, LevelManager)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───objects → Định nghĩa các đối tượng trong game (Ball, Brick, Paddle, PowerUp, ...)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;└───factories → Tạo các đối tượng (BrickFactory, PowerUpFactory)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├───model → Quản lý dữ liệu người chơi và vật phẩm (User, PaddleItem, PlayerProgress)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└───service → Dịch vụ xử lý logic (AuthService, DatabaseService, ShopService, ...)<br>
│&nbsp;&nbsp;│<br>
│&nbsp;&nbsp;├───<span style="color:#4ec9b0;">resources</span><br>
│&nbsp;&nbsp;│&nbsp;&nbsp;└───vibe/com/demo<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├───assets<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───css → Các file định dạng giao diện (gameview.css, login.css, ...)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;├───img → Tài nguyên hình ảnh (background, icons, sprites, ...)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;└───sounds → Hiệu ứng âm thanh (click, victory, lose, ...)<br>
│&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└───fxmlFiles → Các layout FXML cho UI (login.fxml, shop.fxml, gameview.fxml, ...)<br>
<br>
└───<span style="color:#4ec9b0;">README.md</span><br>
</div>

<hr>

<h2>🧩 Giới thiệu tổng quan</h2>

<ul style="line-height:1.7;">
  <li><strong>Ngôn ngữ:</strong> Java 17+</li>
  <li><strong>Framework:</strong> JavaFX</li>
  <li><strong>Mục tiêu:</strong> Xây dựng game Arkanoid phiên bản hiện đại với hiệu ứng, giao diện và hệ thống người dùng.</li>
  <li><strong>Kiến trúc:</strong> MVC (Model - View - Controller) kết hợp Service Layer và Factory Pattern.</li>
</ul>

<hr>

<h2>🚀 Chạy dự án</h2>

<pre style="background-color:#1e1e1e; color:#9cdcfe; padding:12px; border-radius:6px;">
# Clone dự án
git clone https://github.com/username/vibe-game.git

# Mở trong IntelliJ / Eclipse
# Chạy MainApp.java trong vibe/com/demo/
</pre>

<hr>

<h2>📜 Ghi chú</h2>

<ul style="line-height:1.6;">
  <li>Tất cả tài nguyên hình ảnh, âm thanh, CSS và FXML được lưu trong thư mục <code>resources/vibe/com/demo</code>.</li>
  <li>Game có thể mở rộng dễ dàng bằng cách thêm level, power-up hoặc animation mới.</li>
  <li>Mọi file Java đều được đặt đúng theo package để đảm bảo khả năng tái sử dụng và bảo trì.</li>
</ul>

<hr>

<p align="center" style="font-size:14px; color:gray;">
  © 2025 VIBE Game Project — Built with ❤️ in JavaFX
</p>
