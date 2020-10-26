package softuni.exam.domain.dtos.pictures;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureRootDto {

    @XmlElement(name = "picture")
    private List<PictureDto> pictureDtos;

    public PictureRootDto() {
    }

    public List<PictureDto> getPictureDtos() {
        return pictureDtos;
    }

    public void setPictureDtos(List<PictureDto> pictureDtos) {
        this.pictureDtos = pictureDtos;
    }
}
