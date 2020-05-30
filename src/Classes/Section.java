package Classes;

public class Section {
    private int id;
    private String name;
    private String description;

    public Section(int idSection, String n, String d){
        id = idSection;
        name = n;
        description = d;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }
}
