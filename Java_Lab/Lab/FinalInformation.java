

import java.util.HashMap;

public class FinalInformation 
{
    public static void countSize(String incomingText,byte[] byteTable, byte[] byteText,
                                 HashMap<String,Integer> statics){

        System.out.println("\n Длина закодированных данных в байтах= " + byteText.length + "\n Объем памяти, занимаемой таблицей в байтах = " + byteTable.length);
        System.out.println(" Длина исходных данных = 1(byte) * L = " + incomingText.length());

        System.out.println(" Средняя длина кодового слова: " + (double)((int)(Huffman.Lsr*10000))/10000);
        System.out.println(" Количество нулей: " + Output.number_of_nulls+"\n Количество единиц: "+Output.number_of_edinits);
        System.out.println(" Количество переданных битов: "+(Output.number_of_nulls+Output.number_of_edinits));

        if(Main.makeCorrections)
        {
            System.out.println("\n Найдены и успешно изменены "+SafeDecoder.countOfCorrectedErrors+" ошибки в safeBinaryText.\n");
        
            System.out.println("\n Количество всех ошибок: "+Output.numberofallerrors+" \n");
        }
        double idMemory = 0;
        double chanse;
        for (String a : statics.keySet()) {
            chanse = (double)statics.get(a)/incomingText.length();
            idMemory += chanse*(Math.log(chanse)/Math.log(2.0));
        }
        idMemory *= -(incomingText.length()/8);
        System.out.println("Количество памяти при идеальном кодировании в байтах = " + idMemory);
    }
}
