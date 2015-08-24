package org.optimizationBenchmarking.utils.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.collections.iterators.EnumerationIterator;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The local host class provides information about the local host on which
 * this software is running. It can give us some information about the
 * IP-address and name of the current computer and to enable to decide
 * whether an incoming connection comes from the local computer or from
 * another one.
 */
public final class NetworkUtils {

  /** the local host address */
  public static final String LOCAL_HOST = "localhost"; //$NON-NLS-1$

  /**
   * Obtain the set of internet addresses assigned to the local host. These
   * addresses are ordered such that the address which is most likely known
   * to the outside world comes first. If there are no other local
   * addresses than the loopback address, this method returns an empty
   * list.
   *
   * @return the set of internet addresses assigned to the local host.
   */
  public static final ArrayListView<InetAddress> getLocalAddresses() {
    return __LocalAddresses.LOCAL_ADDRESSES;
  }

  /**
   * Obtain the set of internet addresses of this host as seen from the
   * outside world.
   *
   * @return the set of internet addresses under which this host is seen by
   *         the outside world.
   */
  public static final ArrayListView<InetAddress> getGlobalAddresses() {
    return __GlobalAddresses.GLOBAL_ADDRESSES;
  }

  /**
   * Get the most-likely public address of this computer, i.e., the address
   * which most-likely can be used to access server processes running on
   * this computer from the outside world. There is no guarantee that this
   * is actually the IP address which can be used for accessing the server.
   *
   * @return the most likely used public address of this server
   * @see #getMostLikelyPublicName()
   */
  public static final InetAddress getMostLikelyPublicAddress() {
    return __PublicAddress.PUBLIC_ADDRESS;
  }

  /**
   * Get the most-likely public name of this computer.
   *
   * @return the most-likely public name of this computer.
   * @see #getMostLikelyPublicAddress()
   */
  public static final String getMostLikelyPublicName() {
    return __PublicName.PUBLIC_NAME;
  }

  /**
   * Check whether this address belongs to the local computer.
   *
   * @param addr
   *          the address
   * @return {@code true} if this address belongs to one of the network
   *         interfaces of this computer, {@code false} otherwise
   */
  public static final boolean isLocalAddress(final InetAddress addr) {
    if (addr == null) {
      throw new IllegalArgumentException(
          "Cannot check if null address is local."); //$NON-NLS-1$
    }
    if (addr.isLoopbackAddress()) {
      return true;
    }
    return NetworkUtils.getLocalAddresses().contains(addr);
  }

  /**
   * Get a server name for the internet address
   *
   * @param addr
   *          the internet address
   * @return the server name for the internet address
   */
  public static final String getInetAddressName(final InetAddress addr) {

    if (addr == null) {
      throw new IllegalArgumentException(//
          "Internet address cannot be null."); //$NON-NLS-1$
    }

    if (addr.isLoopbackAddress()) {
      return NetworkUtils.LOCAL_HOST;
    }

    try {
      return addr.getCanonicalHostName();
    } catch (final Throwable error) {
      return addr.getHostAddress();
    }
  }

  /**
   * Obtain the URL for a given host and port.
   *
   * @param host
   *          the host
   * @param port
   *          the port
   * @return the URL
   */
  public static final URL getServerBaseURL(final String host,
      final int port) {
    final InetAddress addr;
    final String useHost;

    try {
      addr = InetAddress.getByName(host);
    } catch (final Throwable error) {
      throw new IllegalArgumentException("Host '" + host + //$NON-NLS-1$
          "' is invalid."); //$NON-NLS-1$
    }
    if (addr instanceof Inet6Address) {
      useHost = '[' + addr.getHostAddress() + ']';
    } else {
      useHost = host;
    }

    try {
      return new URL((("http://" + useHost) + ':' + port) + '/'); //$NON-NLS-1$
    } catch (final MalformedURLException error) {
      throw new IllegalArgumentException(((((((//
          "Error while creating URL for host '" + host) //$NON-NLS-1$
          + " (represented as '") + useHost + //$NON-NLS-1$
          "') and port ")//$NON-NLS-1$
          + port) + '\'') + '.'), error);
    }
  }

  /** The holder class for the local addresses */
  private static final class __LocalAddresses {

    /** the local addresses */
    static final ArrayListView<InetAddress> LOCAL_ADDRESSES = __LocalAddresses
        .__createLocalAddresses();

    /**
     * Create the local addresses
     *
     * @return the local addresses
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final ArrayListView<InetAddress> __createLocalAddresses() {
      final HashSet<InetAddress> addresses;
      final InetAddress[] addrs;
      final int size;
      InetAddress addr;

      // http://stackoverflow.com/questions/2939218/

      addresses = new HashSet<>();
      try {
        try {
          addr = InetAddress.getLocalHost();
          if ((addr != null) && (!(addr.isLoopbackAddress()))) {
            addresses.add(addr);
          }
        } catch (final Throwable lerror) {
          ErrorUtils
              .logError(Configuration.getGlobalLogger(),
                  Level.WARNING,//
                  "Recoverable/ignoreable error while obtaining the local host.", //$NON-NLS-1$
                  lerror, false, RethrowMode.DONT_RETHROW);
        }

        for (final NetworkInterface netint : new EnumerationIterator<>(
            NetworkInterface.getNetworkInterfaces())) {
          try {
            for (final InetAddress inetAddress : new EnumerationIterator<>(
                netint.getInetAddresses())) {
              try {
                if (inetAddress == null) {
                  continue;
                }
                if (inetAddress.isLoopbackAddress()) {
                  continue;
                }
                if (inetAddress.isMulticastAddress()) {
                  continue;
                }

                addresses.add(inetAddress);

              } catch (final Throwable error) {
                ErrorUtils.logError(Configuration.getGlobalLogger(),
                    Level.WARNING,//
                    "Recoverable/ignoreable error while dealined with InetAddress "//$NON-NLS-1$
                        + inetAddress + " of network interfaces.", //$NON-NLS-1$
                    error, false, RethrowMode.DONT_RETHROW);
              }
            }
          } catch (final Throwable error) {
            ErrorUtils
                .logError(Configuration.getGlobalLogger(),
                    Level.WARNING,//
                    "Recoverable/ignoreable error while InetAddresses for network interfaces " + netint, //$NON-NLS-1$
                    error, false, RethrowMode.DONT_RETHROW);
          }
        }
      } catch (final Throwable error) {
        ErrorUtils
            .logError(Configuration.getGlobalLogger(),
                Level.WARNING,//
                "Recoverable/ignoreable error while obtaining network interfaces.", //$NON-NLS-1$
                error, false, RethrowMode.DONT_RETHROW);
      }

      size = addresses.size();
      if (size <= 0) {
        return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
      }
      addrs = addresses.toArray(new InetAddress[size]);
      Arrays.sort(addrs, new __InetCmp());
      return new ArrayListView<>(addrs);
    }
  }

  /** The holder class for the global addresses */
  private static final class __GlobalAddresses {

    /** the global addresses */
    static final ArrayListView<InetAddress> GLOBAL_ADDRESSES = __GlobalAddresses
        .__createGlobalAddresses();

    /**
     * Create the global addresses
     *
     * @return the global addresses
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final ArrayListView<InetAddress> __createGlobalAddresses() {
      final HashSet<InetAddress> addresses;
      final ExecutorService serv;
      final String[] hosts;
      final int size;
      final InetAddress[] addrs;

      // http://superuser.com/questions/420969
      // http://stackoverflow.com/questions/2939218/

      addresses = new HashSet<>();
      hosts = new String[] { "http://checkip.amazonaws.com/", //$NON-NLS-1$
          "http://icanhazip.com/", //$NON-NLS-1$
          "http://curlmyip.com/", //$NON-NLS-1$
          "http://ipecho.net/plain", //$NON-NLS-1$
          "http://wtfismyip.com/text", //$NON-NLS-1$
      };
      serv = Executors.newFixedThreadPool(hosts.length);
      try {
        for (final String urlStr : hosts) {
          serv.submit(new __AddressGetterJob(addresses, urlStr));
        }
        serv.shutdown();
        serv.awaitTermination(5, TimeUnit.MINUTES);
      } catch (final Throwable error) {
        ErrorUtils
            .logError(Configuration.getGlobalLogger(),
                Level.WARNING,//
                "Recoverable/ignoreable error while trying to obtain the internet addresses under which we are visible to the outside world.", //$NON-NLS-1$
                error, false, RethrowMode.DONT_RETHROW);
      }

      synchronized (addresses) {
        size = addresses.size();
        if (size <= 0) {
          return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
        }
        addrs = addresses.toArray(new InetAddress[size]);
      }
      Arrays.sort(addrs, new __InetCmp());
      return new ArrayListView<>(addrs);
    }
  }

  /** an address getter job */
  private static final class __AddressGetterJob implements Runnable {
    /** the set */
    private final HashSet<InetAddress> m_set;
    /** the host */
    private final String m_host;

    /**
     * create the address getter job
     *
     * @param set
     *          the target set
     * @param host
     *          the host to query
     */
    __AddressGetterJob(final HashSet<InetAddress> set, final String host) {
      super();
      this.m_set = set;
      this.m_host = host;
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      final URL url;
      final URLConnection connect;
      final InetAddress addr;
      String ip, z;
      int index;

      try {
        url = new URL(this.m_host);

        ip = null;

        connect = url.openConnection();
        connect.setDoOutput(false);
        connect.setAllowUserInteraction(false);
        connect.setDoInput(true);
        connect.setUseCaches(false);
        connect.setConnectTimeout(20000);
        connect.setDefaultUseCaches(false);
        connect.setReadTimeout(20000);
        connect.connect();
        try {
          try (final InputStream is = connect.getInputStream()) {
            try (final InputStreamReader isr = new InputStreamReader(is)) {
              try (final BufferedReader br = new BufferedReader(isr)) {
                ip = TextUtils.prepare(br.readLine());
              }
            }
          }
        } finally {
          if (connect instanceof HttpURLConnection) {
            ((HttpURLConnection) connect).disconnect();
          }
        }

        if (ip != null) {
          index = ip.lastIndexOf(' ');
          if (index > 0) {
            z = TextUtils.prepare(ip.substring(index + 1));
            if (z != null) {
              ip = z;
            }
          }

          try {
            addr = InetAddress.getByName(ip);
          } catch (final Throwable parse) {
            ErrorUtils
                .logError(
                    Configuration.getGlobalLogger(),
                    Level.WARNING,//
                    ((("Recoverable/ignoreable error while trying to convert the internet address '"//$NON-NLS-1$
                        + ip + //
                        "' obtained from the website '" + //$NON-NLS-1$
                    this.m_host) + '\'') + '.'), parse, false,
                    RethrowMode.DONT_RETHROW);
            return;
          }

          if (addr != null) {
            synchronized (this.m_set) {
              this.m_set.add(addr);
            }
          }
        }
      } catch (final Throwable inner) {
        ErrorUtils
            .logError(
                Configuration.getGlobalLogger(),
                Level.WARNING,//
                ((("Recoverable/ignoreable error while trying to obtain the internet address by using the website '" + //$NON-NLS-1$
                this.m_host) + '\'') + '.'), inner, false,
                RethrowMode.DONT_RETHROW);
      }
    }
  }

  /** The holder class for the public address */
  private static final class __PublicAddress {

    /** the public address */
    static final InetAddress PUBLIC_ADDRESS = __PublicAddress
        .__getPublicAddress();

    /**
     * Compute the public address
     *
     * @return the public address
     */
    private static final InetAddress __getPublicAddress() {
      ArrayListView<InetAddress> local, global;

      local = NetworkUtils.getLocalAddresses();
      global = NetworkUtils.getGlobalAddresses();

      for (final InetAddress addr : global) {
        if (local.contains(global)) {
          return addr;
        }
      }

      if (local.isEmpty()) {
        try {
          return InetAddress.getLocalHost();
        } catch (final Throwable lerror) {
          ErrorUtils
              .logError(Configuration.getGlobalLogger(),
                  Level.WARNING,//
                  "Recoverable/ignoreable error while obtaining the local host.", //$NON-NLS-1$
                  lerror, false, RethrowMode.DONT_RETHROW);
        }
        return InetAddress.getLoopbackAddress();
      }

      return local.get(0);
    }
  }

  /** The holder class for the public name */
  private static final class __PublicName {
    /** the public name */
    static final String PUBLIC_NAME = NetworkUtils
        .getInetAddressName(__PublicAddress.PUBLIC_ADDRESS);
  }

  /** A comparator for internet addressess */
  private static final class __InetCmp implements Comparator<InetAddress> {

    /** create */
    __InetCmp() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public final int compare(final InetAddress o1, final InetAddress o2) {
      return Integer.compare(__InetCmp.__getVal(o1),
          __InetCmp.__getVal(o2));
    }

    /**
     * get the value of an internet address for comparison
     *
     * @param a
     *          the address
     * @return the value
     */
    private static final int __getVal(final InetAddress a) {
      int val;
      val = 0;
      try {
        if (a == null) {
          return Integer.MAX_VALUE;
        }
        if (a.isLoopbackAddress()) {
          val |= 128;
        }
        if (a.isMulticastAddress()) {
          val |= 64;
        }
        if (a.isMCNodeLocal()) {
          val |= 32;
        }
        if (a.isMCLinkLocal()) {
          val |= 16;
        }
        if (a.isMCGlobal()) {
          val |= 8;
        }
        if (a.isSiteLocalAddress()) {
          val |= 4;
        }
        if (a.isLinkLocalAddress()) {
          val |= 2;
        }
        if (a.isAnyLocalAddress()) {
          val |= 1;
        }
        return val;
      } catch (final Throwable error) {
        return (Integer.MAX_VALUE - 1);
      }
    }
  }
}
