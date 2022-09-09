

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;



public class Output 
{
    HashMap<String,String>table;
    String source;
    private byte []byteTable,byteText;
    public static int number_of_nulls=0, number_of_edinits=0;

    public static int numberofallerrors = 0;

    public Output(HashMap<String, String> table, String source) {
        this.table = table;
        this.source = source;
        
        byteTable= tableToDecoder(table);

        byteText=stringToDecoder(table,source);
    }

    public byte[] tableToDecoder(HashMap<String,String> table)
    {
        byte[] outBytes = new byte[0];

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutput output=null;
        try {
            output=new ObjectOutputStream(bos);
            output.writeObject(table);
            output.flush();
            outBytes =bos.toByteArray();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return outBytes;
    }



    public byte[] stringToDecoder(HashMap<String,String> table, String source)
    {
        StringBuilder readyString= new StringBuilder();

        for (int i = 0; i < source.length()/Main.blockLenMinusOne; i++) 
        {
            readyString.append(table.get(source.substring(Main.blockLenMinusOne*i,Main.blockLenMinusOne*(i+1))));
        }
        readyString.append(table.get(Huffman.eof));

// Переделать, т.к. будем передавать текст с ошибкой
        //if(Main.hasErrors)readyString=new StringBuilder(makeError(readyString.toString()));

       
        for (int i = 0; i < readyString.length(); i++) 
        {
            if(readyString.toString().toCharArray()[i]=='0') number_of_nulls++;
            else number_of_edinits++;        
        }


        if(readyString.length()<100000000)System.out.println(readyString+"\n");
        else System.out.println("Бинарная строка слишком длинная!!!");

        byte[] forRet=new byte[readyString.length()/8+(readyString.length()%8!=0?1:0)];

        for (int i = 0; i < readyString.length(); i++) {
            forRet[i/8]+=(((byte)readyString.charAt(i)=='1'?1:0)<<(7-(i%8)));
        }
        return forRet;
    }


    public String makeError(String binary) {

        Random random = new Random();

        char[] characters=binary.toCharArray();
        

        System.out.println("Кодирование:       "+binary);
        for (int i = 0; i < binary.length(); i++) {
            if(random.nextDouble()<Main.criticalChanse){

                numberofallerrors++;
                if (characters[i]=='0')characters[i]='1';
                else characters[i]='0';
            }
        }

        binary=String.copyValueOf(characters);
        System.out.println("Кодирование + шум: "+binary);

        return binary;
    }

    public byte[] getByteTable() {
        return byteTable;
    }

    public byte[] getByteText() {
        return byteText;
    }

}

