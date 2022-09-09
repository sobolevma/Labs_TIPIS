

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Source 
{
    static File file;

    public static String getFileText(String filename)
        {
            file=new File(filename);

            Scanner in = null;

            if (file.exists())
                try { in=new Scanner(file.getAbsoluteFile()); /* Пытаемся записать данные из файла по абсолютному имени файла*/}
                catch (FileNotFoundException e) 
                    {e.printStackTrace();}

        //Абсолютный путь к файлу.
        System.out.println(file.getAbsolutePath());

        String toReturn="";

        while (in.hasNext())
            toReturn+=in.nextLine();
        

        return toReturn;
    }
}
