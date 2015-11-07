package org.optimizationBenchmarking.utils.io;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.collections.ImmutableAssociation;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.xml.IXMLFileType;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A registry of file types */
public final class FileTypeRegistry {

  /** the default mime type */
  private static final String DEFAULT_MIME_TYPE = "application/octet-stream"; //$NON-NLS-1$

  /**
   * the map associating file suffixes, namespaces, and mime types to file
   * types
   */
  private final HashMap<Object, IFileType> m_typeMap;

  /** the queue of pending file types */
  private final ArrayList<Object> m_queue;

  /** create the file type registry */
  FileTypeRegistry() {
    super();
    this.m_typeMap = new HashMap<>();
    this.m_queue = new ArrayList<>();

    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.text.ETextFileType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.io.xml.XMLFileType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.document.impl.xhtml10.XHTML"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.io.EArchiveType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.graphics.EFontType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.document.impl.xhtml10.EWebFileType"); //$NON-NLS-1$
    this.m_queue.add(//
        "org.optimizationBenchmarking.utils.config.ConfigurationXML"); //$NON-NLS-1$
  }

  /**
   * Register a class via its name.
   *
   * @param className
   *          the class name to register
   */
  public final void registerClass(final String className) {
    final String use;
    use = TextUtils.prepare(className);
    if (use == null) {
      throw new IllegalArgumentException(//
          "Class name cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + className + "' does."); //$NON-NLS-1$
    }
    synchronized (this.m_queue) {
      this.m_queue.add(use);
    }
  }

  /**
   * validate a class
   *
   * @param clazz
   *          the clazz
   */
  private static final void __validateClass(final Class<?> clazz) {
    if (clazz == null) {
      throw new IllegalArgumentException("Class cannot be null.");//$NON-NLS-1$
    }
    if (!(clazz.isEnum())) {
      throw new IllegalArgumentException(
          "Class must be an instance of Enum, but is "//$NON-NLS-1$
              + clazz);
    }
    if (!(IFileType.class.isAssignableFrom(clazz))) {
      throw new IllegalArgumentException(
          "Class must be a sub-class of IFileType, but is "//$NON-NLS-1$
              + clazz);
    }
  }

  /**
   * Register a file type class, which must be an {@code enum} and extend
   * {@link IFileType}.
   *
   * @param clazz
   *          the class to register
   */
  public final void registerClass(final Class<? extends IFileType> clazz) {
    FileTypeRegistry.__validateClass(clazz);
    synchronized (this.m_queue) {
      this.m_queue.add(clazz);
    }
  }

  /**
   * Get the type belonging to a given file suffix
   *
   * @param fileSuffix
   *          the file name suffix / extension (such as {@code tex},
   *          {@code png}, or {@code java})
   * @return the type, or {@code null} if none could be found belonging to
   *         the given file suffix
   */
  public final IFileType getTypeForSuffix(final String fileSuffix) {
    return this.__getTypeForID(FileTypeRegistry
        .__validateSuffix(fileSuffix));
  }

  /**
   * Validate a file suffix
   *
   * @param fileSuffix
   *          the file suffix
   * @return the formatted version
   */
  private static final String __validateSuffix(final String fileSuffix) {
    final String str;
    str = TextUtils.prepare(fileSuffix);
    if (str == null) {
      throw new IllegalArgumentException(//
          "File suffix cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + fileSuffix + "' does."); //$NON-NLS-1$
    }
    return TextUtils.toUpperCase(str);
  }

  /**
   * Get the type belonging to a given namespace
   *
   * @param namespace
   *          the XML namespace
   * @return the type, or {@code null} if none could be found belonging to
   *         the given namespace
   */
  public final IFileType getTypeForNamespace(final String namespace) {
    return this.__getTypeForID(FileTypeRegistry
        .__validateNamespace(namespace));
  }

  /**
   * validate a namespace
   *
   * @param namespace
   *          the namespace to validate
   * @return the formatted namespace
   */
  private static final String __validateNamespace(final String namespace) {
    final String str;

    str = TextUtils.prepare(namespace);
    if (str == null) {
      throw new IllegalArgumentException(//
          "File suffix cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + namespace + "' does."); //$NON-NLS-1$
    }
    return TextUtils.toUpperCase(str);

  }

  /**
   * Get the type belonging to a given namespace
   *
   * @param namespace
   *          the XML namespace
   * @return the type, or {@code null} if none could be found belonging to
   *         the given namespace
   */
  public final IFileType getTypeForNamespace(final URI namespace) {
    return this.__getTypeForID(FileTypeRegistry
        .__validateNamespace(namespace));
  }

  /**
   * validate a namespace
   *
   * @param namespace
   *          the namespace to validate
   * @return the formatted namespace
   */
  private static final URI __validateNamespace(final URI namespace) {
    if (namespace == null) {
      throw new IllegalArgumentException("Namespace cannot be null"); //$NON-NLS-1$
    }
    return namespace.normalize();
  }

  /**
   * validate a file type
   *
   * @param type
   *          the file type
   */
  private static final void __validateFileType(final IFileType type) {
    if (type == null) {
      throw new IllegalArgumentException("File type cannot be null.");//$NON-NLS-1$
    }
  }

  /**
   * Associate a file suffix with a file type
   *
   * @param suffix
   *          the suffix
   * @param type
   *          the file type
   */
  public final void associateSuffix(final String suffix,
      final IFileType type) {
    final String str;

    FileTypeRegistry.__validateFileType(type);
    str = FileTypeRegistry.__validateSuffix(suffix);
    synchronized (this.m_queue) {
      this.m_queue.add(new ImmutableAssociation<>(str, type));
    }
  }

  /**
   * Associate a mime type with a file type
   *
   * @param mimeType
   *          the mimeType
   * @param type
   *          the file type
   */
  public final void associateMimeType(final String mimeType,
      final IFileType type) {
    final String str;

    FileTypeRegistry.__validateFileType(type);
    str = FileTypeRegistry.__validateMimeType(mimeType);
    synchronized (this.m_queue) {
      this.m_queue.add(new ImmutableAssociation<>(str, type));
    }
  }

  /**
   * Associate a namespace with a file type
   *
   * @param namespace
   *          the namespace
   * @param type
   *          the file type
   */
  public final void associateNamespace(final String namespace,
      final IFileType type) {
    final String str;

    FileTypeRegistry.__validateFileType(type);
    str = FileTypeRegistry.__validateNamespace(namespace);
    synchronized (this.m_queue) {
      this.m_queue.add(new ImmutableAssociation<>(str, type));
    }
  }

  /**
   * Associate a namespace with a file type
   *
   * @param namespace
   *          the namespace
   * @param type
   *          the file type
   */
  public final void associateNamespace(final URI namespace,
      final IFileType type) {
    final URI str;

    FileTypeRegistry.__validateFileType(type);
    str = FileTypeRegistry.__validateNamespace(namespace);
    synchronized (this.m_queue) {
      this.m_queue.add(new ImmutableAssociation<>(str, type));
    }
  }

  /**
   * Register a file type
   *
   * @param type
   *          the type
   */
  public final void register(final IFileType type) {
    FileTypeRegistry.__validateFileType(type);
    synchronized (this.m_queue) {
      this.m_queue.add(type);
    }
  }

  /**
   * Get the type belonging to a given mime type
   *
   * @param mimeType
   *          the mime type
   * @return the type, or {@code null} if none could be found belonging to
   *         the given mime type
   */
  public final IFileType getTypeForMimeType(final String mimeType) {
    return this.__getTypeForID(FileTypeRegistry
        .__validateMimeType(mimeType));
  }

  /**
   * Get the file type belonging to a given path.
   *
   * @param path
   *          the path
   * @return the corresponding file type, or {@code null} if none could be
   *         found belonging to the given mime type
   */
  @SuppressWarnings("unused")
  public final IFileType getTypeForPath(final Path path) {
    final String suffix;
    String mimeType;
    IFileType type;

    suffix = PathUtils.getFileExtension(path);
    if (suffix != null) {
      type = this.getTypeForSuffix(suffix);
      if (type != null) {
        return type;
      }
    }

    try {
      mimeType = Files.probeContentType(path);
    } catch (final Throwable error) {
      mimeType = null;
    }

    if (mimeType != null) {
      return this.getTypeForMimeType(mimeType);
    }

    return null;
  }

  /**
   * Get the mime type belonging to a given path.
   *
   * @param path
   *          the path
   * @return the corresponding mime type
   */
  @SuppressWarnings("unused")
  public final String getMimeTypeForPath(final Path path) {
    final String suffix;
    String mimeType;
    IFileType type;

    suffix = PathUtils.getFileExtension(path);
    if (suffix != null) {
      type = this.getTypeForSuffix(suffix);
      if (type != null) {
        mimeType = type.getMIMEType();
        if (mimeType != null) {
          return mimeType;
        }
      }
    }

    try {
      mimeType = Files.probeContentType(path);
    } catch (final Throwable error) {
      mimeType = null;
    }

    if (mimeType != null) {
      return mimeType;
    }

    return FileTypeRegistry.DEFAULT_MIME_TYPE;
  }

  /**
   * Validate a mime type
   *
   * @param mimeType
   *          the mime type
   * @return the formatted version
   */
  private static final String __validateMimeType(final String mimeType) {
    final String str;

    str = TextUtils.prepare(mimeType);
    if (str == null) {
      throw new IllegalArgumentException(//
          "Mime type cannot be null, empty, or just consist of white space, but '" //$NON-NLS-1$
              + mimeType + "' does."); //$NON-NLS-1$
    }
    return TextUtils.toUpperCase(str);
  }

  /**
   * Get the type of the given id. This method lazily loads the file types
   * from a queue. It tries to load as few as possible.
   *
   * @param id
   *          the id object
   * @return the type, or {@code null} if none could be found
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final IFileType __getTypeForID(final Object id) {
    final HashMap<Object, IFileType> map;
    final ArrayList<Object> queue;
    Iterator iterator;
    IFileType got;
    Object current;
    Class<?> clazz;
    ImmutableAssociation<Object, IFileType> assoc;
    boolean b;

    map = this.m_typeMap;
    synchronized (map) {
      got = map.get(id);
    }
    if (got != null) {
      return got;
    }

    queue = this.m_queue;
    synchronized (queue) {
      queueLoop: for (;;) {
        if (queue.isEmpty()) {
          return null;
        }

        current = queue.get(0);
        if (current instanceof IFileType) {
          queue.remove(0);
          synchronized (map) {
            if (FileTypeRegistry.__put(map, ((IFileType) current))) {
              got = map.get(id);
            }
          }
          if (got != null) {
            return got;
          }
          continue queueLoop;
        }

        if (current instanceof Iterator) {
          iterator = ((Iterator) current);
          if (iterator.hasNext()) {
            queue.add(0, iterator.next());
            continue queueLoop;
          }
          queue.remove(0);
        }

        if (current instanceof Iterable) {
          queue.set(0, ((Iterable) current).iterator());
          continue queueLoop;
        }

        if (current instanceof Enum[]) {
          queue.remove(0);
          b = false;
          synchronized (map) {
            for (final Enum o : ((Enum[]) current)) {
              if (FileTypeRegistry.__put(map, ((IFileType) o))) {
                b = true;
              }
            }
            if (b) {
              got = map.get(id);
            }
          }
          if (got != null) {
            return got;
          }
          continue queueLoop;
        }

        if (current instanceof Class<?>) {
          queue.remove(0);
          clazz = ((Class) current);
          FileTypeRegistry.__validateClass(clazz);
          queue.add(0, clazz.getEnumConstants());
          continue queueLoop;
        }

        if (current instanceof ImmutableAssociation) {
          queue.remove(0);
          assoc = ((ImmutableAssociation) current);
          current = assoc.getKey();
          if (current != null) {
            b = false;
            synchronized (map) {
              if (map.get(current) == null) {
                map.put(current, assoc.getValue());
                b = true;
              }
            }
            if (b && (current.equals(id))) {
              return assoc.getValue();
            }
          }
          continue queueLoop;
        }

        if (current instanceof String) {
          queue.remove(0);
          try {
            queue.add(0,
                ReflectionUtils.findClass(((String) current), Enum.class));
          } catch (final Throwable error) {
            ErrorUtils.logError(Configuration.getGlobalLogger(),//
                "Error trying to find class for file type set.", //$NON-NLS-1$
                error, false, RethrowMode.DONT_RETHROW);
          }
        }
      }
    }
  }

  /**
   * put a string into the map
   *
   * @param str
   *          the string
   * @param dest
   *          the map
   * @param type
   *          the file type
   * @return {@code true} if the map changed
   */
  private static final boolean __put(
      final HashMap<Object, IFileType> dest, final String str,
      final IFileType type) {
    String use;

    use = TextUtils.prepare(str);
    if (use != null) {
      return FileTypeRegistry
          .__put(TextUtils.toUpperCase(use), dest, type);
    }
    return false;
  }

  /**
   * put an object into the map
   *
   * @param obj
   *          the object
   * @param dest
   *          the map
   * @param type
   *          the file type
   * @return {@code true} if the map changed
   */
  private static final boolean __put(final Object obj,
      final HashMap<Object, IFileType> dest, final IFileType type) {
    if (obj != null) {
      if (dest.get(obj) == null) {
        dest.put(obj, type);
        return true;
      }
    }
    return false;
  }

  /**
   * put the given type into the map
   *
   * @param dest
   *          the destination map
   * @param type
   *          the file type
   * @return {@code true} if the map changed
   */
  private static final boolean __put(
      final HashMap<Object, IFileType> dest, final IFileType type) {
    boolean b;
    IXMLFileType xft;

    if (type == null) {
      return false;
    }

    b = false;
    if (FileTypeRegistry.__put(dest, type.getDefaultSuffix(), type)) {
      b = true;
    }
    if (FileTypeRegistry.__put(dest, type.getMIMEType(), type)) {
      b = true;
    }
    if (type instanceof IXMLFileType) {
      xft = ((IXMLFileType) (type));
      if (FileTypeRegistry.__put(dest, xft.getNamespace(), type)) {
        b = true;
      }
      if (FileTypeRegistry.__put(xft.getNamespaceURI(), dest, type)) {
        b = true;
      }
    }
    return b;
  }

  /**
   * get the globally shared instance of the file type registry
   *
   * @return the globally shared instance of the file type registry
   */
  public static final FileTypeRegistry getInstance() {
    return __FileTypeRegistryHolder.INSTANCE;
  }

  /** the holder for the file type registry */
  private static final class __FileTypeRegistryHolder {
    /** the shared instance of the file type registry */
    static final FileTypeRegistry INSTANCE = new FileTypeRegistry();
  }
}