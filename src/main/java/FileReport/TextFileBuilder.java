package FileReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
        Singleton Pattern because only need one object to create and write to files for ease
 */
public class TextFileBuilder {
    private static TextFileBuilder instance;
    private File file;
    private TextFileBuilder(){}

    /**
     * @return Single instance of the builder class
     */
    public static TextFileBuilder getInstance(){
        if (instance==null){
            instance = new TextFileBuilder();
        }
        return instance;
    }

    /**
     * @param fileName is the name of the text file to make or return if exists
     * @return "filename".txt
     */
    public File getFile(String fileName){
        if (this.file==null){
            file = new File(fileName+".txt");
            try{
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("getFile() ERROR");
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    public void writeToFile(File file, String toWrite){
        String fileName = file.getName();
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(toWrite);
            myWriter.close();
        } catch (IOException e){
            System.out.println("writeToFile() ERROR");
            throw new RuntimeException();
        }
    }

}
