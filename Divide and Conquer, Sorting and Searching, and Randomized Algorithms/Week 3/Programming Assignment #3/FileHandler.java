package Week3;

import java.io.*;

public class FileHandler {

    /*
     * Function to read a file from a specific path and returns a String
     * */
    public String ReadFile(String filePath) throws IOException
    {
        String line;
        StringBuilder file = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        /* check if the line is empty */
        while((line = reader.readLine()) != null)
        {
            file.append(line).append("\n");
        }
        reader.close();

        return file.toString();
    }

    /*
     * Function to write the new formatted file into a specific path
     * */
    public void WriteFile(String inString, String outputFilePath) throws IOException
    {
        /* check if the inputPath file is empty */
        if ((inString == null) || (inString.trim().length() == 0))
            return;

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        writer.write(inString);
        writer.close();
    }
}
