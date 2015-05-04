package test.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Ignore;

/** the base class for tests */
@Ignore
public class TestBase {

  /** the constructor */
  protected TestBase() {
    super();
  }

  /**
   * Serialize {@code o}, then deserialize it
   *
   * @param o
   *          the object
   * @return the de-serialized serialized version
   */
  public static final Object serializeDeserialize(final Object o) {
    byte[] bdata;
    Object o2;

    o2 = null;
    try {
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
          oos.writeObject(o);
          oos.flush();
        }
        bos.flush();
        bdata = bos.toByteArray();
      }

      try (ByteArrayInputStream iis = new ByteArrayInputStream(bdata)) {
        try (ObjectInputStream ois = new ObjectInputStream(iis)) {
          o2 = ois.readObject();
        }
      }
    } catch (final Throwable tt) {
      Assert.fail(tt.toString());
    }

    if (o == null) {
      Assert.assertNull(o2);
    } else {
      Assert.assertNotNull(o2);
    }

    return o2;
  }

  /**
   * Get a logger which discards all its log output
   *
   * @return the logger
   */
  public static final Logger getNullLogger() {
    final Logger logger;
    final Handler[] handlers;

    logger = Logger.getAnonymousLogger();
    logger.setLevel(Level.OFF);
    logger.setFilter(new Filter() {
      /** {@inheritDoc} */
      @Override
      public final boolean isLoggable(final LogRecord record) {
        return false;
      }
    });

    logger.setUseParentHandlers(false);
    handlers = logger.getHandlers();
    if (handlers != null) {
      for (final Handler handler : handlers) {
        logger.removeHandler(handler);
      }
    }
    return logger;
  }
}
