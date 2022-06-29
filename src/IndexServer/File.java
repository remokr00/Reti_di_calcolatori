package IndexServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class File {

    /*
    La classe file rappresenta semplicemente l'oggetto che noi vogliamo inviare tra server e client.
    Presenta solamente metodi getter perché non si vuole alterare in alcun modo il contenuto dei file
    che vengono inviati.
     */

    private String fileName;
    private String[] keyWords;
    private String contenuto;

    public File(String nome, String[] parole, String testo){

        fileName = nome;
        keyWords = parole;
        contenuto = testo;

    }

    public String getFileName() {
        return fileName;
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public String getContenuto() {
        return contenuto;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", keyWords=" + Arrays.toString(keyWords) +
                ", contenuto='" + contenuto + '\'' +
                '}';
    }
}
