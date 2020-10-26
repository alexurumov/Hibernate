package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.teams.TeamDto;
import softuni.exam.domain.dtos.teams.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAMS_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Exam/Football-Info_Skeleton/src/main/resources/files/xml/teams.xml";

    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importTeams() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        TeamRootDto teamRootDto = xmlParser.importXMl(TeamRootDto.class, TEAMS_XML_FILE_PATH);

        for (TeamDto teamDto : teamRootDto.getTeamDtos()) {
            Team team = mapper.map(teamDto, Team.class);

            Picture picture = pictureRepository.findByUrl(team.getPicture().getUrl()).orElse(null);

            if (picture == null) {
                sb.append(String.format(Constants.INVALID_INPUT, team.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            team.setPicture(picture);

            if (!validatorUtil.isValid(team)) {
                sb.append(String.format(Constants.INVALID_INPUT, team.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            teamRepository.saveAndFlush(team);
            sb.append(String.format(Constants.SUCCESSFUL_INPUT, team.getClass().getSimpleName(), team.getName())).append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return fileUtil.readFile(TEAMS_XML_FILE_PATH);
    }
}
