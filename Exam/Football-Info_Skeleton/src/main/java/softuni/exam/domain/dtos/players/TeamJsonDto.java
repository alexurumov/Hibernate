package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlElement;

public class TeamJsonDto {

    @Expose
    private String name;

    @Expose
    private PictureJsonDto picture;

    public TeamJsonDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureJsonDto getPicture() {
        return picture;
    }

    public void setPicture(PictureJsonDto picture) {
        this.picture = picture;
    }
}
