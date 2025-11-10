package infrastructure.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FilerManager {


    public List<String> lerTodasLinhas(String caminho){

        if (!Files.exists(Paths.get(caminho))){
            return List.of();}
        try {
            return Files.readAllLines(Paths.get(caminho));
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void escreverLinhas(String caminho, List<String> linhas){

        try {
            Path path = Paths.get(caminho);
            Files.write(path,linhas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
