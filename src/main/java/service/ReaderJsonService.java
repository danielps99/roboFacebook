package service;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ContaFacebook;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReaderJsonService {

    public static List<ContaFacebook> buscarUsuario() {
        ContaFacebook contaFacebook = new ContaFacebook();
        List<ContaFacebook> contas = Lists.newArrayList();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File f = new File("contas.json");
//            contaFacebook = mapper.readValue(f, ContaFacebook.class);
            contas = mapper.readValue(f, mapper.getTypeFactory().constructCollectionType(List.class, ContaFacebook.class));
//            List<ContaFacebook> contas = mapper.readValue(f,
//                    new TypeReference<List<ContaFacebook>>() {
//                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contas;
    }
}