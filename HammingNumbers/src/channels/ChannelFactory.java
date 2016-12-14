package channels;

/**
 * Contains factory methods for clients to use to build implementations of the
 * Channel interface
 * 
 * @author Peter Swantek
 *
 */
public class ChannelFactory {

    private ChannelFactory() {
    }

    /**
     * Builds a data channel to connect nodes in the data flow network
     * 
     * @return a new data channel
     */
    public static Channel buildChannel() {
        return new DataChannel();
    }

}
