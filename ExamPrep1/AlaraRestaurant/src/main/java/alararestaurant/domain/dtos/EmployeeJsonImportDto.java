package alararestaurant.domain.dtos;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class EmployeeJsonImportDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private Integer age;

    @Expose
    private String position;

    public EmployeeJsonImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
