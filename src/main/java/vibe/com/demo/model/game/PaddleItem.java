package vibe.com.demo.model.game;

public class PaddleItem {

    private String id;
    private String url;
    private String paddleName;
    private String paddleDesc;

    public PaddleItem(String id, String url, String paddleName, String paddleDesc) {
        this.id = id;
        this.url = url;
        this.paddleName = paddleName;
        this.paddleDesc = paddleDesc;
    }

    public PaddleItem(String id) {
        this.id = id;
    }

    public PaddleItem(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public PaddleItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPaddleName() {
        return paddleName;
    }

    public void setPaddleName(String paddleName) {
        this.paddleName = paddleName;
    }

    public String getPaddleDesc() {
        return paddleDesc;
    }

    public void setPaddleDesc(String paddleDesc) {
        this.paddleDesc = paddleDesc;
    }
}
