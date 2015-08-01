package examples.snippets;

import java.net.InetAddress;

import org.optimizationBenchmarking.utils.net.LocalHost;

/**
 * With this program, I test the functionality of the local host class.
 * That class is intended to provide us some information about the
 * IP-address and name of the current computer and to enable to decide
 * whether an incoming connection comes from the local computer or from
 * another one.
 */
public class LocalHostTest {

  /**
   * the main method
   *
   * @param args
   *          the command line arguments
   */
  public static final void main(final String[] args) {
    for (final InetAddress addr : LocalHost.getLocalAddresses()) {
      System.out.println(addr.toString() + '\t'
          + LocalHost.getInetAddressName(addr));
    }
    System.out.println(" xxxxxxxxxxxxxxxxxxxxxx "); //$NON-NLS-1$
    for (final InetAddress addr : LocalHost.getGlobalAddresses()) {
      System.out.println(addr.toString() + '\t'
          + LocalHost.getInetAddressName(addr));
    }
    System.out.println(" xxxxxxxxxxxxxxxxxxxxxx "); //$NON-NLS-1$
    System.out.println(LocalHost.getMostLikelyPublicAddress());
    System.out.println(LocalHost.getMostLikelyPublicName());
  }
}
