package engine;


import bte.component.jaxb.BTEEnigma;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class LoadManager {
    private static final String JAXB_XML_GAME_PACKAGE_NAME = "bte.component.jaxb";

    public void LoadXmlToObject(String xmlNameFile) {
        try {
            InputStream inputStream = new FileInputStream(new File(xmlNameFile));
            BTEEnigma machine = deserializeFrom(inputStream);
            System.out.println("Successfully loaded XML to object: " + machine);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static BTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (BTEEnigma) u.unmarshal(in);
    }

    public boolean isFileExistsAndIsXml(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && isXmlFile(filePath);
    }

    public boolean isXmlFile(String filePath) {
        return filePath.toLowerCase().endsWith(".xml");
    }
};
