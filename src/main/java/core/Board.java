package core;

public class Board {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Board(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
