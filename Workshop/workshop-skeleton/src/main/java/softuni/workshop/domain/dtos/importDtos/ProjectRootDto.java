package softuni.workshop.domain.dtos.importDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectRootDto {

    @XmlElement(name = "project")
    List<ProjectDto> projectDtos;

    public ProjectRootDto() {
    }

    public List<ProjectDto> getProjectDtos() {
        return projectDtos;
    }

    public void setProjectDtos(List<ProjectDto> projectDtos) {
        this.projectDtos = projectDtos;
    }
}
