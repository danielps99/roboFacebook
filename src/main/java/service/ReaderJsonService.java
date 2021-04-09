package service;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ContaFacebook;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReaderJsonService {

    public static ContaFacebook buscarContaFacebook() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JFileChooser fileChooser = new JFileChooser();
            configurarJFileChooser(fileChooser);
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                return mapper.readValue(fileChooser.getSelectedFile(), ContaFacebook.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void configurarJFileChooser(JFileChooser fileChooser) {
        fileChooser.setCurrentDirectory(new File("./../"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter(){
            @Override
            public boolean accept(File file){
                return ((!file.isDirectory() && file.getName().toLowerCase().endsWith(".json")));
            }
            @Override
            public String getDescription() {
                return "Mostrando somente arquivos.json";
            }
        });
    }
}