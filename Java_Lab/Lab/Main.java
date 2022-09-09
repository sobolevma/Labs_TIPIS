// Проверить все с первого пункта 1 ЛР.

import java.io.IOException;

public class Main 
{

    //Флаги
    //criticalChanse определяет вероятность ошибки
    public static double criticalChanse=0.005;

    //blockLen задает длину блока
    public static int blockLenMinusOne=1;

    //Флаг uniCodes определяет будет ли кодирование равномерным
    public static boolean uniCodes=false;

    //Флаг hasErrors определяет возможность добавления ошибок
    public static boolean hasErrors=true;

    //Флаг makeCorrections определяет возможность исправления ошибок
    public static boolean makeCorrections=true;

    public static void main(String[] args) throws IOException 
    {
        String text=Source.getFileText("inputtext");

        Union union=new Union(text);

        union.decodeProcess();

    }
}
