

import javax.swing.*;
import java.awt.event.ActionListener;


public class CalculateTimer 
{

    Integer integer=0;

    Timer timer;

    CalculateTimer() 
    {
        timer=new Timer(100, (ActionListener) e -> integer++);
        timer.start();
    }


    void showTime()
    {
        System.out.println("\n\n Время: "+integer+"100 ms");
    }

}
