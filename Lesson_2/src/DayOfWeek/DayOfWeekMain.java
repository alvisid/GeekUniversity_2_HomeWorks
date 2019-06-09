package DayOfWeek;

import DayOfWeek.DayOfWeek.*;

public class DayOfWeekMain
{
    public static void main(String[] args)
    {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
        System.out.println(getWorkingHours(DayOfWeek.SUNDAY));
    }

     private static String getWorkingHours(Enum dayOfWeek)
    {
        String result ="";

        for (DayOfWeek o : DayOfWeek.values())
        {
            if (o.equals(dayOfWeek))
            {
                result =  o.getWorkingHours();
                break;
            }
        }

        return result;
    }
}

