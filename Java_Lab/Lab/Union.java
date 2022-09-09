

import java.io.IOException;

public class Union 
{
    private String textToDecode="";
    
    static CalculateTimer timer=new CalculateTimer();

    Union(String textToDecode) 
    {
        this.textToDecode = textToDecode;
             
    }

    void decodeProcess() throws IOException {

        StringBuilder errorsafeBinaryText;

        Huffman huffman =new Huffman(textToDecode);

        Output output=new Output(huffman.getTable(), huffman.getMainText());

        SafeCoder safeCoder=new SafeCoder(output.getByteTable(),output.getByteText());

        if(Main.hasErrors) 
        {
            errorsafeBinaryText = new StringBuilder(output.makeError(safeCoder.getSafeBinaryText().toString()));
        }       
        else
        {
            errorsafeBinaryText = safeCoder.getSafeBinaryText();
        }

        SafeDecoder safeDecoder=new SafeDecoder(safeCoder.getSafeBinaryTable(), errorsafeBinaryText);

        Decoder decoder=new Decoder(output.getByteTable(),safeDecoder.getByteText());

        timer.showTime();

        FinalInformation.countSize(textToDecode,output.getByteTable(),output.getByteText(), huffman.getStatics());

        timer.timer.stop();

    }
}
