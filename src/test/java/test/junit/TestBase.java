package test.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
}
