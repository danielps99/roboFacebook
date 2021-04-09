package service;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ContaFacebook;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReaderJsonService {

    public static List<ContaFacebook> buscarUsuario() {
        List<ContaFacebook> contas = Lists.newArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./../"));
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                contas = mapper.readValue(selectedFile, mapper.getTypeFactory().constructCollectionType(List.class, ContaFacebook.class));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contas;
    }
}