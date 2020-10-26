package softuni.jsonexercise.util.impl;

import softuni.jsonexercise.util.FileUtil;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String fileContent(String filePath) throws IOException {

        File file = new File(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
