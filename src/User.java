public class User {
    private int id;
    private String username;
    private String name;

    public User(String username, String name, int id) {
        this.username = username;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getUsername(){
        return username;
    }

    public int getId() {
        return id;
    }
}