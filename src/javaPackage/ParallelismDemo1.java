package javaPackage;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
public class ParallelismDemo1 extends RecursiveAction
{
    final int THRESHOLD = 2;
    double[] numbers;
    int indexStart, indexLast;
    
    ParallelismDemo1 (double[]n, int s, int l)
    {
        numbers = n;
        indexStart = s;
        indexLast = l;
    }
    
    protected void compute ()
    {
        if ((indexLast - indexStart) > THRESHOLD)
            for (int i = indexStart; i < indexLast; i++)
             numbers[i] = numbers[i] + Math.random ();
        else
            invokeAll (new ParallelismDemo1 (numbers,
           indexStart,
           (indexStart - indexLast) / 2),
   new ParallelismDemo1 (numbers, (indexStart - indexLast) / 2,
           indexLast));
    }
    public static void main (String[]args)
    {
        final int SIZE = 10;
        ForkJoinPool pool = new ForkJoinPool ();
        double na[] = new double[SIZE];
        System.out.println ("initialized random values :");
        for (int i = 0; i < na.length; i++)
        {
            na[i] = (double) i + Math.random ();
            System.out.format ("%.4f ", na[i]);
        }
        System.out.println ();
        ParallelismDemo1 task = new ParallelismDemo1 (na, 0, na.length);
        pool.invoke (task);
        System.out.println ("Changed values :");
        for (int i = 0; i < 10; i++)
            System.out.format ("%.4f ", na[i]);
        System.out.println ();
    }
}
