package test.junit.org.optimizationBenchmarking.utils.io;

import java.net.URI;
import java.util.HashSet;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.FileTypeRegistry;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;

/**
 * A test for the file type registry.
 */
public class FileTypeRegistryTest {

  /** the constructor */
  public FileTypeRegistryTest() {
    super();
  }

  /**
   * get the file type classes
   *
   * @return the file type classes
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private static final ArrayListView<Class<? extends IFileType>> __getClasses() {
    return new ArrayListView(
        new Class[] {
            org.optimizationBenchmarking.utils.text.ETextFileType.class,//
            org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat.class,//
            org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType.class,//
            org.optimizationBenchmarking.utils.io.xml.XMLFileType.class,//
            org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML.class,//
            org.optimizationBenchmarking.utils.io.EArchiveType.class,//
            org.optimizationBenchmarking.utils.graphics.EFontType.class,//
            org.optimizationBenchmarking.utils.document.impl.xhtml10.EWebFileType.class,//
            org.optimizationBenchmarking.utils.config.ConfigurationXML.class });

  }

  /** check if we can find the file types belonging to the given suffixes */
  @Test(timeout = 3600000)
  public void testSuffixes() {
    final HashSet<String> done;
    String suffix;

    done = new HashSet<>();

    for (final Class<? extends IFileType> clazz : FileTypeRegistryTest
        .__getClasses()) {
      for (final IFileType ift : clazz.getEnumConstants()) {
        suffix = ift.getDefaultSuffix();
        if ((suffix != null) && (done.add(suffix))) {
          Assert.assertEquals(FileTypeRegistry.getInstance()
              .getTypeForSuffix(suffix), ift);
        }
      }
    }
  }

  /** check if we can find the file types belonging to the given mime types */
  @Test(timeout = 3600000)
  public void testMimeTypes() {
    final HashSet<String> done;
    String mime;

    done = new HashSet<>();

    for (final Class<? extends IFileType> clazz : FileTypeRegistryTest
        .__getClasses()) {
      for (final IFileType ift : clazz.getEnumConstants()) {
        mime = ift.getMIMEType();
        if ((mime != null) && (done.add(mime))) {
          Assert.assertEquals(FileTypeRegistry.getInstance()
              .getTypeForMimeType(mime), ift);
        }
      }
    }
  }

  /** check if we can find the file types belonging to the given namespace */
  @Test(timeout = 3600000)
  public void testNamespaces() {
    final HashSet<String> done;
    String ns;

    done = new HashSet<>();

    for (final Class<? extends IFileType> clazz : FileTypeRegistryTest
        .__getClasses()) {
      for (final IFileType ift : clazz.getEnumConstants()) {
        if (ift instanceof IXMLFileType) {
          ns = ((IXMLFileType) ift).getNamespace();
          if ((ns != null) && (done.add(ns))) {
            Assert.assertEquals(FileTypeRegistry.getInstance()
                .getTypeForNamespace(ns), ift);
          }
        }
      }
    }
  }

  /** check if we can find the file types belonging to the given namespace */
  @Test(timeout = 3600000)
  public void testNamespacesURI() {
    final HashSet<URI> done;
    URI ns;

    done = new HashSet<>();

    for (final Class<? extends IFileType> clazz : FileTypeRegistryTest
        .__getClasses()) {
      for (final IFileType ift : clazz.getEnumConstants()) {
        if (ift instanceof IXMLFileType) {
          ns = ((IXMLFileType) ift).getNamespaceURI();
          if ((ns != null) && (done.add(ns))) {
            Assert.assertEquals(FileTypeRegistry.getInstance()
                .getTypeForNamespace(ns), ift);
          }
        }
      }
    }
  }

  /** check if we cannot find non-existing types */
  @Test(timeout = 3600000)
  public void testNoneExisting() {
    final HashSet<String> all;
    final Random random;
    String rand;
    int i;

    all = new HashSet<>();
    for (final Class<? extends IFileType> clazz : FileTypeRegistryTest
        .__getClasses()) {
      for (final IFileType ift : clazz.getEnumConstants()) {
        all.add(ift.getDefaultSuffix());
        all.add(ift.getName());
        all.add(ift.getMIMEType());
        if (ift instanceof IXMLFileType) {
          all.add(((IXMLFileType) ift).getNamespace());
        }
      }
    }

    random = new Random();

    for (i = 100; i >= 0; i--) {
      do {
        rand = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
            random.nextLong());
      } while (all.contains(rand));
      Assert.assertNull(FileTypeRegistry.getInstance().getTypeForSuffix(
          rand));
      Assert.assertNull(FileTypeRegistry.getInstance().getTypeForMimeType(
          rand));
      Assert.assertNull(FileTypeRegistry.getInstance()
          .getTypeForNamespace(rand));
    }
  }
}
