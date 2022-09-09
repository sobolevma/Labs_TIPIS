

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;




public class Huffman 
{
    private String fromsource;
   
    private HashMap <String,Integer> statics =new HashMap<>(); /*Статистика (символ - сколько раз встречается в тексте) */

    private ArrayList<Node> tree=new ArrayList<>(); /*Дерево кодировки */

    private HashMap<String,String> table=new HashMap<>(); /* Таблица кодировки*/

    public static String eof; /* Конец строки*/
    
    public static double Lsr; /* Средняя длина кодового слова*/

    private int sum = 0; 



    Huffman(String data)
    {               
        fromsource = data;

        countStatictics(); // Подсчитываем сколько раз встречается кадлый символ в тексте.

        if(Main.uniCodes)
        {
            for (String a : statics.keySet()) {
                statics.put(a,1);
            }
        }


        createAndCompBinaryTree(); // Создаем бинарное дерево

        while (tree.size()>1){
            tree.sort(Comparator.comparingInt(o -> o.cost));
            unionObjects(); // объединяем два узла с наименьшими вероятностями в один узел.
        }

        treeToBin(tree.get(0),"","~");

        if(fromsource.length() < 20000)System.out.println("Исходный текст:  "+fromsource+"\n");
        else System.out.println("Исходный текст слишком большой!");
        

        System.out.println("Таблица кодировки:\n");

        for (String a:statics.keySet())        
                        sum += statics.get(a);
              
        for (String a:table.keySet()) 
        {
            System.out.print("|" + a + "-->"+table.get(a)+"-->"+statics.get(a)+"|\n");
           
            Lsr += (double)table.get(a).length()*statics.get(a)/sum;
        }   
       
       
        System.out.println("\n\n");
    }




    private void createAndCompBinaryTree()
    {
        for (String c:statics.keySet()) 
        {
            tree.add(new Node(c,statics.get(c)));
        }

        tree.sort(Comparator.comparingInt(o -> o.cost)); // Сортируем дерево по количеству каждого символа.
    }





    private void unionObjects()
    {
        if(tree.size()>1){
            tree.add(new Node("~",tree.get(0).cost+tree.get(1).cost,tree.get(0),tree.get(1))); //Объединяем два узла в 1 и даем указатель на каждый из узлов.
            
            // Удаляем объединенные узлы из дерева.
            tree.remove(1);
            tree.remove(0);
        }
    }





    private void treeToBin(Node local,String way,String a){
        if (local.getLeft()==null&&local.getRight()==null)return;

        if (local.getLeft()!=null)
        {
            if (local.getLeft().Letter != "~") table.put(local.getLeft().Letter, way + "0");
            else treeToBin(local.getLeft(),way+"0","~");
        }

        if (local.getRight()!=null){
            if (local.getRight().Letter != "~") table.put(local.getRight().Letter, way + "1");
            else treeToBin(local.getRight(),way+"1","~");
        }
    }





    private void countStatictics()
    {        
        for (int i = 0; i < fromsource.length()/Main.blockLenMinusOne; i++) 
        {
            String Ai=fromsource.substring(Main.blockLenMinusOne*i,Main.blockLenMinusOne*(i+1));

            if(statics.containsKey(Ai))
            {
                statics.put(Ai,statics.get(Ai)+1);
            }
            else
            {
                statics.put(Ai,1);
            }
        }

        eof =fromsource.substring((fromsource.length()/Main.blockLenMinusOne)*Main.blockLenMinusOne,fromsource.length())+"/eof/";

        if(statics.containsKey(eof))
        {
            statics.put(eof,statics.get(eof)+1);
        }
        else
        {
            statics.put(eof,1);
        }

    }








    HashMap<String, String> getTable() 
    {
        return table;
    }



    String getMainText() 
    {
        return fromsource;
    }
  

    HashMap<String, Integer> getStatics() 
    {
        return statics;
    }
}
