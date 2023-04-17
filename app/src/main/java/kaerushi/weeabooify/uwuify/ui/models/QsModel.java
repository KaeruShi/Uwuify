package kaerushi.weeabooify.uwuify.ui.models;

public class QsModel {

    String name, url;
    private boolean expanded;

    public QsModel(String name, String url) {
        this.name = name;
        this.url = url;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
