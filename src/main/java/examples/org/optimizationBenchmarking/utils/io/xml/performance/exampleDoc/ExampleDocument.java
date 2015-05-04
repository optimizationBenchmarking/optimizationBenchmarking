package examples.org.optimizationBenchmarking.utils.io.xml.performance.exampleDoc;

import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

import org.optimizationBenchmarking.utils.math.random.LoremIpsum;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.SerializationMethod;
import examples.org.optimizationBenchmarking.utils.io.xml.performance.serializers.XMLStreamWriterSerialization;

/**
 * An &quot;example document&quot; is an example for what a potential
 * XMLFileType output could be. Example documents are randomly generated
 * and contain a given amount of hierarchically nested nodes. Each node may
 * have some attributes and contain a text. The attribute text and the
 * element text is represented by an object and obtained by the
 * {@link java.lang.Object#toString()} method. This method may have some
 * delay, as it would also be the case when generating and serializing
 * data.
 */
public class ExampleDocument {

  /** the empty objects */
  private static final Object[] EMPTY_OBJECTS = new Object[0];
  /** the empty attributes */
  private static final ExampleAttribute[] EMPTY_ATTRIBUTES = new ExampleAttribute[0];

  /** the namespaces */
  public final ExampleNamespace[] namespaces;

  /** the root element */
  public final ExampleElement root;

  /**
   * create an example document
   *
   * @param _namespaces
   *          the namespaces used in the document
   * @param _root
   *          the root element
   */
  ExampleDocument(final ExampleNamespace[] _namespaces,
      final ExampleElement _root) {
    super();
    this.namespaces = _namespaces;
    this.root = _root;
  }

  /**
   * Create an example document
   *
   * @param rand
   *          the randomizer
   * @return the document
   */
  public static final ExampleDocument createExampleDocument(
      final Random rand) {
    return ExampleDocument.createExampleDocument(rand,
        (1 + rand.nextInt(1000)), (1 + rand.nextInt(100)));
  }

  /**
   * Create an example document
   *
   * @param rand
   *          the randomizer
   * @param nodes
   *          the number of nodes to create
   * @param delay
   *          the delay of strings
   * @return the document
   */
  public static final ExampleDocument createExampleDocument(
      final Random rand, final int nodes, final int delay) {
    final ExampleNamespace[] ns;

    ns = ExampleDocument.__createExampleNamespaces(rand);
    return new ExampleDocument(ns, ExampleDocument.__createExampleElement(
        ns, rand, new int[] { Math.max(0, (nodes - 1)) }, true, 0, delay));
  }

  /**
   * create some example namespaces
   *
   * @param rand
   *          the randomizer
   * @return the namespaces
   */
  private static final ExampleNamespace[] __createExampleNamespaces(
      final Random rand) {
    final ArrayList<ExampleNamespace> list;
    ExampleNamespace n, o;
    int i;

    list = new ArrayList<>();
    do {
      n = ExampleDocument.__createExampleNamespace(rand);
      for (i = list.size(); (--i) >= 0;) {
        o = list.get(i);
        if ((o.prefix.equals(n.prefix)) || (o.uri.equals(n.uri))
            || (o.uriString.equals(n.uriString))) {
          continue;
        }
      }
      list.add(n);
    } while (rand.nextInt(4) > 0);

    return list.toArray(new ExampleNamespace[list.size()]);
  }

  /**
   * create an example namespace
   *
   * @param rand
   *          the randomizer
   * @return the namespaces
   */
  private static final ExampleNamespace __createExampleNamespace(
      final Random rand) {
    String scheme, userInfo, host, path, query, fragment, a;
    URI uri;
    long l;

    l = rand.nextLong();

    loop: for (;;) {
      scheme = "http"; //$NON-NLS-1$
      // RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET, l++);

      userInfo = null;

      host = RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET, l++);
      while (rand.nextBoolean()) {
        host += ('.' + RandomUtils.longToString(
            RandomUtils.DEFAULT_CHARSET, l++));
      }

      switch (rand.nextInt(7)) {
        case 0: {
          host += ".de";break;} //$NON-NLS-1$
        case 1: {
          host += ".com";break;} //$NON-NLS-1$
        case 2: {
          host += ".org";break;} //$NON-NLS-1$
        case 3: {
          host += ".cn";break;} //$NON-NLS-1$
        case 4: {
          host += ".au";break;} //$NON-NLS-1$
        case 5: {
          host += ".fr";break;} //$NON-NLS-1$
        default: {
          host += ".net";break;} //$NON-NLS-1$
      }

      path = '/' + RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
          l++);
      do {
        path += ('/' + RandomUtils.longToString(
            RandomUtils.DEFAULT_CHARSET, l++));
      } while (rand.nextBoolean());

      query = null;
      fragment = null;

      try {
        uri = new URI(scheme, userInfo, host, -1, path, query, fragment);
        uri = uri.normalize();
        uri = uri.toURL().toURI();
        uri = uri.normalize();
        a = uri.toASCIIString();
        if (!(a.equals(uri.toString()))) {
          continue loop;
        }
        if (!(a.equals(TextUtils.normalize(a)))) {
          continue loop;
        }
      } catch (final Throwable t) {
        continue loop;
      }

      return new ExampleNamespace(uri, RandomUtils.longToString(
          RandomUtils.DEFAULT_CHARSET, rand.nextLong()));
    }
  }

  /**
   * create some example element
   *
   * @param rand
   *          the randomizer
   * @param namespaces
   *          the namespaces
   * @param nodes
   *          the (maximum) number of to create
   * @param force
   *          create that number of nodes
   * @param depth
   *          the current depth
   * @param delay
   *          the node string access delay
   * @return the element
   */
  private static final ExampleElement __createExampleElement(
      final ExampleNamespace[] namespaces, final Random rand,
      final int[] nodes, final boolean force, final int depth,
      final int delay) {
    final ArrayList<ExampleAttribute> attrs;
    final ArrayList<Object> sub;
    final MemoryTextOutput sb;
    long attr;
    int i;
    boolean children;

    sb = new MemoryTextOutput();
    attrs = new ArrayList<>();
    attr = rand.nextLong();
    i = (rand.nextBoolean() ? 1 : 2);
    while (rand.nextInt(i++) <= 0) {
      attrs.add(new ExampleAttribute(//
          namespaces[rand.nextInt(namespaces.length)],//
          RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET, attr++),//
          ExampleDocument.__makeString(rand, false, sb, delay)));
    }

    children = ((depth < 100) && (nodes[0] > 0) && (force || (rand
        .nextInt(4) > 0)));

    sub = new ArrayList<>();
    if (rand.nextInt(children ? 30 : ((attrs.size() <= 0) ? 1 : 4)) <= 0) {
      sub.add(ExampleDocument.__makeString(rand, true, sb, delay));
    }

    while (children) {
      nodes[0]--;

      sub.add(ExampleDocument.__createExampleElement(namespaces, rand,
          nodes, false, (depth + 1), delay));

      children = ((depth < 100) && (nodes[0] > 0) && (force || (rand
          .nextInt(4) > 0)));

      if (rand.nextInt(30) <= 0) {
        sub.add(ExampleDocument.__makeString(rand, true, sb, delay));
      }
    }

    return new ExampleElement(namespaces[rand.nextInt(namespaces.length)],//
        RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
            rand.nextLong()),//
            ((attrs.size() > 0) ? attrs.toArray(new ExampleAttribute[attrs
                                                                     .size()]) : ExampleDocument.EMPTY_ATTRIBUTES),//
                                                                     ((sub.size() > 0) ? sub.toArray(new Object[sub.size()])
                                                                         : ExampleDocument.EMPTY_OBJECTS));
  }

  /**
   * Make an object that can be converted to a string.
   *
   * @param rand
   *          the randomizer
   * @param canLorem
   *          can a lorem be the result?
   * @param sb
   *          the string builder
   * @param delay
   *          the delay
   * @return the object
   */
  private static final Object __makeString(final Random rand,
      final boolean canLorem, final MemoryTextOutput sb, final int delay) {
    Object s;

    if (canLorem && (rand.nextInt(4) <= 0)) {
      sb.clear();
      LoremIpsum.appendLoremIpsum(sb, rand);
      s = sb.toString();
      sb.clear();
    } else {
      do {
        s = RandomUtils.longToObject(rand.nextLong(), false);
      } while ((s instanceof Character) || (s instanceof Boolean));
    }

    s = ((delay > 0) ? new _DelayedToString(s, delay) : s);
    s.toString();
    return s;
  }

  /**
   * The main method: test if document generation is correct
   *
   * @param args
   *          the arguments, ignored
   * @throws Throwable
   *           if something happens
   */
  public static final void main(final String[] args) throws Throwable {
    final SerializationMethod ser;
    String a, b;
    Random rand;
    long seed;
    int i;

    ser = new XMLStreamWriterSerialization();

    rand = new Random();
    seed = rand.nextLong();
    for (i = 0;; i++) {
      seed++;

      System.out.println("Test " + i); //$NON-NLS-1$

      rand.setSeed(seed);
      try (final StringWriter sw = new StringWriter()) {
        ser.store(ExampleDocument.createExampleDocument(rand,
            rand.nextInt(1000), rand.nextInt(200)), sw);
        sw.flush();
        a = sw.toString();
      }

      rand.setSeed(seed);
      try (final StringWriter sw = new StringWriter()) {
        ser.store(ExampleDocument.createExampleDocument(rand,
            rand.nextInt(1000), rand.nextInt(200)), sw);
        sw.flush();
        b = sw.toString();
      }

      if (!(a.equals(b))) {
        System.out.println("error"); //$NON-NLS-1$
        System.out.flush();
        System.exit(-1);
      }
    }

  }
}
