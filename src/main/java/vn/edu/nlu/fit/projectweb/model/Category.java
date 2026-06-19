package vn.edu.nlu.fit.projectweb.model;

public class Category {
    private int id;
    private String name;
    private int parent_id;
    private String link;
    private String datatarget;

    public Category(int id, String name, int parent_id, String link,String datatarget) {
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
        this.link = link;
        this.datatarget = datatarget;
    }

    public Category() {
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDatatarget() {
        return datatarget;
    }

    public void setDatatarget(String datatarget) {
        this.datatarget = datatarget;
    }
}
