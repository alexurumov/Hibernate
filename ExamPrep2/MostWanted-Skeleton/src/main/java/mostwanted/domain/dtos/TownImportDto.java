package mostwanted.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class TownImportDto implements Serializable {

    @Expose
    private String name;

    public TownImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
