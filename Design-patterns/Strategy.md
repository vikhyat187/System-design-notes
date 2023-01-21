
<img width="722" alt="Screenshot 2023-01-14 at 12 22 47 AM" src="https://user-images.githubusercontent.com/52795644/212397045-cc463488-ab7a-4cfb-b591-9c08d21b9dc8.png">
https://www.java67.com/2014/12/strategy-pattern-in-java-with-example.html


Behavourial design pattern 
Eg from JDK 

## Collection.sort(List, Comparator)
```
public class Test {

    public static void main(String args[]) throws InterruptedException {
        
        // we can provide any strategy to do the sorting 
        int[] var = {1, 2, 3, 4, 5 };
        Context ctx = new Context(new BubbleSort());
        ctx.arrange(var);
        
        // we can change the strategy without changing Context class
        ctx = new Context(new QuickSort());
        ctx.arrange(var);
    }

}

interface Strategy {
    public void sort(int[] numbers);
}

class BubbleSort implements Strategy {

    @Override
    public void sort(int[] numbers) {
        System.out.println("sorting array using bubble sort strategy");

    }

}

class InsertionSort implements Strategy {

    @Override
    public void sort(int[] numbers) {
        System.out.println("sorting array using insertion sort strategy");

    }
}

class QuickSort implements Strategy {

    @Override
    public void sort(int[] numbers) {
        System.out.println("sorting array using quick sort strategy");

    }
}

class MergeSort implements Strategy {

    @Override
    public void sort(int[] numbers) {
        System.out.println("sorting array using merge sort strategy");

    }
}

class Context {
    private final Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void arrange(int[] input) {
        strategy.sort(input);
    }
}
```
Output
sorting array using bubble sort strategy
sorting array using quick sort strategy


Read more: https://www.java67.com/2014/12/strategy-pattern-in-java-with-example.html#ixzz7r1FUyUY0
