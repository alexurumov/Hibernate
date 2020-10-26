package softuni.xmlparsingdemo.domain.dtos;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersDto implements Serializable {

    @XmlElement(name = "user")
    private List<UserDto> userDtos;

    public UsersDto() {
        this.userDtos = new ArrayList<>();
    }

    public List<UserDto> getUserDtos() {
        return userDtos;
    }

    public void setUserDtos(List<UserDto> userDtos) {
        this.userDtos = userDtos;
    }
}
