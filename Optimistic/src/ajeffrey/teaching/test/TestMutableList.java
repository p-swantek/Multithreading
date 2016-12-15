package ajeffrey.teaching.test;

import ajeffrey.teaching.debug.Debug;
import ajeffrey.teaching.util.list.Iterator;
import ajeffrey.teaching.util.list.MutableList;
import ajeffrey.teaching.util.list.OptimisticMutableList;

/**
 * Driver that runs a test by having 2 threads add and get data from a
 * MutableList
 * 
 * @author Alan Jeffrey, Peter Swantek
 *
 */
public class TestMutableList {

    public static void main(final String[] args) throws InterruptedException {

        final MutableList list = OptimisticMutableList.factory.build(); //use the factory to create the OptimisticMutableList implementation

        final Thread t1 = new Thread() {
            public void run() {
                Debug.out.breakPoint("Starting t1");
                for (int i = 0; i < args.length; i++) {
                    list.add(args[i]);
                }
                Debug.out.println("Done t1");
            }
        };

        final Thread t2 = new Thread() {
            public void run() {
                Debug.out.breakPoint("Starting t2");
                while (t1.isAlive() || list.size() > 0) {
                    Debug.out.println("Getting...");
                    for (final Iterator i = list.iterator(); i.hasNext();) {
                        final String gotten = (String) (i.next());
                        Debug.out.println("Got: " + gotten);
                        System.out.println("Got: " + gotten);
                        Debug.out.println("Removing: " + gotten);
                        list.remove(gotten);
                    }
                }
                Debug.out.println("Done t2");
            }
        };

        t1.start();
        t2.start();
    }

}
