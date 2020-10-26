package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.pictures.PictureDto;
import softuni.exam.domain.dtos.pictures.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURES_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Exam/Football-Info_Skeleton/src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPictures() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PictureRootDto pictureRootDto = xmlParser.importXMl(PictureRootDto.class, PICTURES_XML_FILE_PATH);

        for (PictureDto dto : pictureRootDto.getPictureDtos()) {
            Picture picture = mapper.map(dto, Picture.class);

            if (picture.getUrl() == null) {
                sb.append(String.format(Constants.INVALID_INPUT, picture.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            if (!validatorUtil.isValid(picture)) {
                sb.append(String.format(Constants.INVALID_INPUT, picture.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            pictureRepository.saveAndFlush(picture);
            sb.append(String.format(Constants.SUCCESSFUL_INPUT, picture.getClass().getSimpleName(), picture.getUrl())).append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile(PICTURES_XML_FILE_PATH);
    }

}
