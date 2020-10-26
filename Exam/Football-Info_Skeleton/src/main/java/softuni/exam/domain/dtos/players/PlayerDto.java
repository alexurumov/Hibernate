package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlayerDto implements Serializable {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private Integer number;

    @Expose
    private BigDecimal salary;

    @Expose
    private String position;

    @Expose
    private PictureJsonDto picture;

    @Expose
    private TeamJsonDto team;

    public PlayerDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PictureJsonDto getPicture() {
        return picture;
    }

    public void setPicture(PictureJsonDto picture) {
        this.picture = picture;
    }

    public TeamJsonDto getTeam() {
        return team;
    }

    public void setTeam(TeamJsonDto team) {
        this.team = team;
    }
}
