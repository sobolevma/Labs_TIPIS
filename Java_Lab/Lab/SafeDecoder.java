



import java.io.IOException;
import java.util.Random;

public class SafeDecoder {
    int [][] matrix;
    int m=4,n=7;
    byte[] ByteText,ByteTable;
    static int countOfCorrectedErrors=0;
    static int countOfCorrectedFounded=0;



    public byte[] getByteText() {
        return ByteText;
    }

    public byte[] getByteTable() {
        return ByteTable;
    }

    public SafeDecoder(StringBuilder safeBinaryTable,StringBuilder safeBinaryText_from_Decoder) throws IOException {
        setMatrix();

        StringBuilder safeBinaryText=new StringBuilder();
        
        safeBinaryText=safeBinaryText_from_Decoder;
        

        if (Main.makeCorrections)safeBinaryText=countAndCorrectErrors(safeBinaryText);

        StringBuilder binaryTable=new StringBuilder();//+(safeBinaryTable.length()%n!=0?1:0)
        for (int i = 0; i < safeBinaryTable.length()/n+(safeBinaryTable.length()%n!=0?1:0); i++) {
            binaryTable.append(safeBinaryTable.substring(i * n,i*n+m));
        }

        StringBuilder binaryText=new StringBuilder();
        for (int i = 0; i < safeBinaryText.length()/n+(safeBinaryText.length()%n!=0?1:0); i++) {
            binaryText.append(safeBinaryText.substring(i * n,i*n+m));
        }

        System.out.println("\n -------------------------------\n");
        System.out.println("\nSafe Decoder изменяет safeBinary код текста на нормальный = ");
        System.out.println("\n>>"+binaryText);
        System.out.println("\nКонец работы safeDecoder\n");




 
        this.ByteText=new byte[binaryText.length()/8+(binaryText.length()%8!=0?1:0)];
        for (int i = 0; i < binaryText.length(); i++) {
            this.ByteText[i/8]+=(((byte)binaryText.charAt(i)=='1'?1:0)<<(7-(i%8)));
        }

        this.ByteTable=new byte[binaryTable.length()/8+(binaryTable.length()%8!=0?1:0)];
        for (int i = 0; i < binaryTable.length(); i++) {
            this.ByteTable[i/8]+=(((byte)binaryTable.charAt(i)=='1'?1:0)<<(7-(i%8)));
        }



    }

    public StringBuilder countAndCorrectErrors(StringBuilder safeBinaryText) throws IOException {
        String block;
        System.out.println("\n_______________________________\n Начать проверку ошибок!!");

        for (int i = 0; i < safeBinaryText.length()/7+(safeBinaryText.length()%7!=0?1:0); i++) {
            block=safeBinaryText.substring(i*7,(i+1)*7);
            StringBuilder addition = new StringBuilder();

            int result=0;
            for (int k = 0; k < 3; k++) {
                result = 0;
                for (int j = 0; j < 7; j++) {
                    result += matrix[j][k] * (Character.getNumericValue(block.charAt(j)));
                }
                result = result % 2;
                addition.append(result);
            }

            if(!addition.toString().equals("000")){
                countOfCorrectedErrors++;
                StringBuilder localValue;
                for (int j = 0; j < 7; j++) {
                    localValue=new StringBuilder();
                    for (int k = 0; k < 3; k++) {
                        localValue.append(matrix[j][k]);
                    }
                    if (localValue.toString().equals(addition.toString())){
                        if ((block.charAt(j))=='0')safeBinaryText.setCharAt(i*7+j,'1');
                        else safeBinaryText.setCharAt(i*7+j,'0');
                    }
                }
                System.out.println(addition+"|"+i+"C    ");
            }
            else System.out.println(addition+"|"+i+"    ");


            {
                if(i%10==0) System.out.println("\n.\n");
            }
        }
        
        return safeBinaryText;
    }


   
    public void showMatrix() throws IOException 
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m-1; j++) 
            {
                System.out.println(matrix[i][j]+" ");
            }
            System.out.println("\n.\n");
        }
    }


    private void setMatrix(){
        matrix=new int[n][m-1];
        for (int i = 0; i < m; i++) 
            {
                for (int j = 0; j < m - 1; j++) 
                {
                    if (3 - j == i) matrix[i][j] = 0;
                        else matrix[i][j] = 1;
                }
        }

        for (int i = 4; i < 7; i++) 
            {
                for (int j = 0; j < m-1; j++) 
                {
                    if ((i-4)==(j))matrix[i][j]=1;
                    else matrix[i][j]=0;
                }
            }
    }

}

