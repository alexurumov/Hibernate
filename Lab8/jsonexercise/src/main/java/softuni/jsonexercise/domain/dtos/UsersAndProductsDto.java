package softuni.jsonexercise.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class UsersAndProductsDto implements Serializable {

    @Expose
    private int usersCount;

    @Expose
    private List<UserWithOneProductDto> users;

    public UsersAndProductsDto() {
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithOneProductDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithOneProductDto> users) {
        this.users = users;
    }
}
