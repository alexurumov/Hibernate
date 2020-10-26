package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;

public class PictureJsonDto {

    @Expose
    private String url;

    public PictureJsonDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
