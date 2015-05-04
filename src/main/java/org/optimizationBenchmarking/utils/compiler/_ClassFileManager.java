package org.optimizationBenchmarking.utils.compiler;

import java.io.IOException;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

import org.optimizationBenchmarking.utils.collections.maps.StringMap;

/**
 * A class manager loading compiled objects.
 */
class _ClassFileManager extends
ForwardingJavaFileManager<StandardJavaFileManager> {
  /**
   * Instance of JavaClassObject that will store the compiled bytecode of
   * our class
   */
  private final StringMap<_ByteArrayJavaFileObject> m_classes;

  /**
   * Will initialize the manager with the specified standard java file
   * manager
   *
   * @param standardManager
   *          the manager we forward to
   */
  _ClassFileManager(final StandardJavaFileManager standardManager) {
    super(standardManager);
    this.m_classes = new StringMap<>();
  }

  /**
   * obtain a class loader with all the classes loaded into it
   *
   * @return the class loader
   */
  public final ClassLoader loadClasses() {
    _InternalClassLoader l;

    l = new _InternalClassLoader();

    for (final Map.Entry<String, _ByteArrayJavaFileObject> e : this.m_classes) {
      l._add(e.getKey(), e.getValue());
    }

    for (final Map.Entry<String, _ByteArrayJavaFileObject> e : this.m_classes) {
      l._load(e.getKey());
    }

    return l;
  }

  /**
   * Gives the compiler an instance of the JavaClassObject so that the
   * compiler can write the byte code into it.
   */
  @Override
  public final _ByteArrayJavaFileObject getJavaFileForOutput(
      final Location location, final String className, final Kind kind,
      final FileObject sibling) throws IOException {
    _ByteArrayJavaFileObject bajfo;

    synchronized (this.m_classes) {
      bajfo = this.m_classes.get(className);
      if (bajfo == null) {
        bajfo = new _ByteArrayJavaFileObject(className, kind);
        this.m_classes.put(className, bajfo);
      }
    }
    return bajfo;
  }

}
