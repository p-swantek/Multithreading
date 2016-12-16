package driver;

import java.util.ConcurrentModificationException;

import stack.Stack;
import stack.StackBuilder;

/**
 * Driver program that uses the thread safe stack
 * 
 * @author Peter Swantek
 *
 */
public class StackMain {

    private static final StackBuilder<Integer> builder = new StackBuilder<>();

    private static void print(String message) {
        System.out.println(message);
    }

    private static void emptyStack(Stack<Integer> s) {
        while (s.size() != 0) {
            s.pop();
        }
    }

    private static void pushAll(Stack<Integer> s, Integer... args) {
        for (int item : args) {
            s.push(item);
        }
    }

    public static void main(String[] args) {

        Stack<Integer> safeStack = builder.getSafeStack();

        assert safeStack.size() == 0;
        print("Pushing items onto the stack...");
        pushAll(safeStack, 1, 2, 3, 4);
        assert safeStack.size() == 4;

        print("Stack currently contains:");
        for (Integer item : safeStack) {
            print("\t" + item);
        }

        print("Clearing all items from the stack...");
        emptyStack(safeStack);
        assert safeStack.size() == 0;

        print("Pushing items on the stack, testing to make sure the iterator fails fast during concurrent modification...");
        pushAll(safeStack, 1, 2, 3, 4);

        try {
            for (Integer item : safeStack) {
                print("\t" + item);
                safeStack.push(6);
            }
        } catch (ConcurrentModificationException e) {
            print("Exception was successfully thrown when stack was modified during iteration");
        }
        assert safeStack.size() == 4;

    }

}
