

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Decoder {
    private byte []byteTable,byteText;
    private HashMap<String,String> table;
    private String message="";

    private void decodeTable() {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteTable);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            table = (HashMap<String, String>) in.readObject();
            in.close();
        }
        catch (IOException | ClassNotFoundException ignored){}

        for (String a:table.keySet()) {
            System.out.print("|" + a + "-->" + table.get(a) + "|\n");
        }
        System.out.println("\n в Decoder");
    }

    private void decodeString(StringBuilder input){
        int iterationOnTheEnd=0;
        String tableA = null;
        while((input.length()!=0)){//пока еще есть код
            for (String a:table.keySet()){//для всей таблицы
                tableA=table.get(a);
                boolean match=true;
                boolean allow=tableA.length()<=input.length();
                for (int i = 0; i < tableA.length(); i++) {//проверка-первых Н букв соотвествия
                    if (allow&&(tableA.charAt(i)!=input.charAt(i))) {match= false;break;}
                }
                if(match&&allow){
                    message+=a;
                    for (int i = 0; i < tableA.length(); i++)input.deleteCharAt(0);
                    break;
                }
            }
            if(input.length()<7){
                iterationOnTheEnd++;
                if (iterationOnTheEnd>10)break;
            }
            if (tableA== Huffman.eof) break;
        }

        if(message.length()<100000000)System.out.println("\n"+"Результат:\n  "+message);
        else System.out.println("Сообщение слишком длинное!!!");

        if(iterationOnTheEnd>100000000)System.out.println("\n Прерывание цикла!!!");
    }

    private StringBuilder decodeToString(){
        StringBuilder binaryText= new StringBuilder();
        for (int i = 0; i < byteText.length; i++) {
            for(int j=0;j<=7;j++) binaryText.append (((byteText[i] & (byte)(128 / Math.pow(2, j))) != 0)?1:0);
        }

        if(binaryText.length()<100000000)System.out.println("\n"+binaryText);
        else System.out.println("Бинарный текст слишком длинный!");
        return binaryText;
    }

    Decoder(byte[] byteTable, byte[] byteText) throws IOException {
        this.byteTable = byteTable;
        this.byteText = byteText;
        decodeTable();
        decodeString(decodeToString());
    }

}
