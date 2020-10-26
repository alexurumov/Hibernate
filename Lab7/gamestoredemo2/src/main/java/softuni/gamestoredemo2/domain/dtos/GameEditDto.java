package softuni.gamestoredemo2.domain.dtos;

public class GameEditDto {
    private int id;
    private String[] params;

    public GameEditDto() {
    }

    public GameEditDto(int id, String[] params) {
        this.id = id;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public String[] getParams() {
        return params;
    }
}
