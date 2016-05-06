package demo.app.rssviewer.model;

public class ItemRss {

    private int id;
    private String name;
    private String description;
    private String published;
    private String url;

    public ItemRss() {
    }

    @Override
    public String toString() {
        return "\n id=" + id + ", name=" + name + ", description=" + description + ", published=" + published;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
