package softuni.modelmapperdemo.dtos;

public class StudentDto {

    private String fullName;
    private String city;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
                "fullName='" + fullName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
