package kaerushi.weeabooify.uwuify.ui.models;

public class ExpandRVModel {

    String name;
    private int background;
    boolean expanded;

    public ExpandRVModel(String name, int background) {
        this.name = name;
        this.background = background;
        this.expanded = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
