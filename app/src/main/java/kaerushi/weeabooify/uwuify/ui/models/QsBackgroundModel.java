package kaerushi.weeabooify.uwuify.ui.models;

public class QsBackgroundModel {

    String title;
    int background;

    public QsBackgroundModel(String title, int background) {
        this.title = title;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
