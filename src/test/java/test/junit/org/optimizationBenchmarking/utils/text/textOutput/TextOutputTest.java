package test.junit.org.optimizationBenchmarking.utils.text.textOutput;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.utils.RandomUtils;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

import test.junit.TestBase;
import examples.org.optimizationBenchmarking.LoremIpsum;

/**
 * A base class to test implementations of
 * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput}
 * 
 * @param <R>
 *          the root object type
 */
@Ignore
public abstract class TextOutputTest<R> extends TestBase {

  /** create */
  public TextOutputTest() {
    super();
  }

  /**
   * create a root object
   * 
   * @return the root object
   */
  protected abstract R createRootObject();

  /**
   * Get the string from a root object
   * 
   * @param root
   *          the root object
   * @return the returned object
   */
  protected String getString(final R root) {
    return root.toString();
  }

  /**
   * Wrap a text output around a root object
   * 
   * @param root
   *          the root object
   * @return the wrapped text output
   */
  protected abstract ITextOutput wrap(final R root);

  /**
   * Perform one random test run.
   * 
   * @param r
   *          the randomizer
   */
  private final void __testRun(final Random r) {
    final R root;
    final ITextOutput t;
    final StringBuilder sb;
    int i;

    root = this.createRootObject();
    t = this.wrap(root);
    sb = new StringBuilder();

    for (i = 10000; i >= 0; i--) {
      TextOutputTest.__appendRandomObject(r, sb, t);
      t.flush();
      Assert.assertEquals(this.getString(root), sb.toString());
    }
  }

  /**
   * Append a random object
   * 
   * @param r
   *          the random number generator
   * @param sb
   *          the string builder
   * @param t
   *          the text output
   */
  private static final void __appendRandomObject(final Random r,
      final StringBuilder sb, final ITextOutput t) {
    String st;
    boolean bo;
    byte by;
    short sh;
    int in;
    long lo;
    float fl;
    double dou;
    char ch;
    char[] chs;
    Object ob;
    int i, j, l;

    switch (r.nextInt(13)) {
      case 0: {
        bo = r.nextBoolean();
        sb.append(bo);
        t.append(bo);
        return;
      }

      case 1: {
        by = (byte) (r.nextInt(256) - 128);
        sb.append(by);
        t.append(by);
        return;
      }

      case 2: {
        sh = (short) (r.nextInt(65536) - 32768);
        sb.append(sh);
        t.append(sh);
        return;
      }

      case 3: {
        in = r.nextInt();
        sb.append(in);
        t.append(in);
        return;
      }
      case 4: {
        lo = r.nextLong();
        sb.append(lo);
        t.append(lo);
        return;
      }
      case 5: {
        do {
          ch = ((char) (r.nextInt(65536)));
        } while ((!(Character.isDefined(ch) && Character
            .isBmpCodePoint(ch)))
            || Character.isSupplementaryCodePoint(ch)
            || Character.isSurrogate(ch));
        sb.append(ch);
        t.append(ch);
        return;
      }
      case 6: {
        in = r.nextInt();
        fl = Float.intBitsToFloat(in);
        if (fl <= Float.NEGATIVE_INFINITY) {
          fl = Float.NEGATIVE_INFINITY;
        }
        if (fl >= Float.POSITIVE_INFINITY) {
          fl = Float.POSITIVE_INFINITY;
        }
        if (fl != fl) {
          fl = Float.NaN;
        }
        sb.append(fl);
        t.append(fl);
        return;
      }
      case 7: {
        lo = r.nextLong();
        dou = Double.longBitsToDouble(lo);
        if (dou <= Double.NEGATIVE_INFINITY) {
          dou = Double.NEGATIVE_INFINITY;
        }
        if (dou >= Double.POSITIVE_INFINITY) {
          dou = Double.POSITIVE_INFINITY;
        }
        if (dou != dou) {
          dou = Double.NaN;
        }
        sb.append(dou);
        t.append(dou);
        return;
      }
      case 8: {
        ob = RandomUtils.longToObject(r.nextLong(), r.nextBoolean());
        sb.append(ob);
        t.append(ob);
        return;
      }

      case 9: {
        st = TextOutputTest.__string(r);
        l = st.length();
        i = Math.min(l, Math.max(0, r.nextInt(l + 1)));
        j = Math.min(i, (i + r.nextInt((l - i) + 1)));

        sb.append(st, i, j);
        if (r.nextBoolean()) {
          t.append(st, i, j);
        } else {
          t.append(((CharSequence) st), i, j);
        }
        return;
      }

      case 10: {
        chs = TextOutputTest.__string(r).toCharArray();
        sb.append(chs);
        t.append(chs);
        return;
      }

      case 11: {
        chs = TextOutputTest.__string(r).toCharArray();
        l = chs.length;
        i = Math.min(l, Math.max(0, r.nextInt(l + 1)));
        j = Math.min(i, (i + r.nextInt((l - i) + 1)));

        sb.append(chs, i, (j - i));
        t.append(chs, i, j);
        return;
      }
      default: {
        st = TextOutputTest.__string(r);
        sb.append(st);
        t.append(st);
      }
    }
  }

  /** test if random output is written correctly */
  @Test(timeout = 3600000)
  public void testRandomOutputCorrect() {
    final Random r;
    int i;

    r = new Random();
    for (i = 100; (--i) >= 0;) {
      this.__testRun(r);
    }
  }

  /** test if sequences are written correctly */
  @Test(timeout = 3600000)
  public void testSequenceOutput() {
    final ArrayList<String> list;
    R root;
    ITextOutput t;

    list = new ArrayList<>();

    root = this.createRootObject();
    t = this.wrap(root);
    ESequenceMode.AND.appendSequence(ETextCase.AT_SENTENCE_START, list,
        true, t);
    t.flush();
    Assert.assertEquals("", this.getString(root)); //$NON-NLS-1$
    ESequenceMode.OR.appendSequence(ETextCase.IN_SENTENCE, list, false, t);
    t.flush();
    Assert.assertEquals("", this.getString(root)); //$NON-NLS-1$
    ESequenceMode.NEITHER_NOR.appendSequence(ETextCase.IN_TITLE, list,
        true, t);
    t.flush();
    Assert.assertEquals("", this.getString(root)); //$NON-NLS-1$

    list.add("abc"); //$NON-NLS-1$
    ESequenceMode.AND.appendSequence(ETextCase.AT_SENTENCE_START, list,
        true, t);
    t.flush();
    Assert.assertEquals("Abc", this.getString(root)); //$NON-NLS-1$
    ESequenceMode.OR.appendSequence(ETextCase.IN_SENTENCE, list, false, t);
    t.flush();
    Assert.assertEquals("Abcabc", this.getString(root)); //$NON-NLS-1$
    ESequenceMode.NEITHER_NOR.appendSequence(ETextCase.IN_TITLE, list,
        true, t);
    t.flush();
    Assert.assertEquals("AbcabcAbc", this.getString(root)); //$NON-NLS-1$

    list.add("def xyz"); //$NON-NLS-1$
    ESequenceMode.AND.appendSequence(ETextCase.AT_SENTENCE_START, list,
        true, t);
    t.flush();
    Assert.assertEquals(
        "AbcabcAbcAbc and\u2007def xyz", this.getString(root)); //$NON-NLS-1$

    ESequenceMode.NEITHER_NOR.appendSequence(ETextCase.IN_TITLE, list,
        false, t);
    t.flush();
    Assert.assertEquals(
        "AbcabcAbcAbc and\u2007def xyzneither Abc nor Def Xyz",//$NON-NLS-1$
        this.getString(root));

    root = this.createRootObject();
    t = this.wrap(root);
    list.add("Thomas Weise"); //$NON-NLS-1$
    ESequenceMode.EITHER_OR.appendSequence(ETextCase.AT_TITLE_START, list,
        false, t);
    t.flush();
    Assert.assertEquals("Either Abc, Def Xyz, or Thomas Weise",//$NON-NLS-1$
        this.getString(root));

    list.add("Fritz Walter"); //$NON-NLS-1$
    ESequenceMode.ET_AL.appendSequence(ETextCase.AT_TITLE_START, list,
        false, t);
    t.flush();
    Assert.assertEquals("Either Abc, Def Xyz, or Thomas WeiseAbc et al.",//$NON-NLS-1$
        this.getString(root));
  }

  /**
   * Create a random string
   * 
   * @param r
   *          the randomizer
   * @return the string
   */
  private static final String __string(final Random r) {
    String s;
    MemoryTextOutput msb;

    switch (r.nextInt(4)) {

      case 0: {
        s = ""; //$NON-NLS-1$
        do {
          s += RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
              r.nextLong());
        } while (r.nextBoolean());
        return s;
      }

      case 1: {
        msb = new MemoryTextOutput();
        LoremIpsum.appendLoremIpsum(msb, r, null);
        return msb.toString();
      }

      case 2: {
        return String.valueOf(RandomUtils.longToObject(r.nextLong(),
            r.nextBoolean()));
      }

      default: {
        return RandomUtils.longToString(RandomUtils.DEFAULT_CHARSET,
            r.nextLong());
      }
    }
  }

  /** test if strings are written correctly */
  @SuppressWarnings("incomplete-switch")
  @Test(timeout = 10000000)
  public void testGrow() {
    final Random rand;
    R root;
    ITextOutput textOut;
    StringBuilder sb;
    int size, i, j, len;
    char[] add;
    String s;

    rand = new Random();

    for (boolean useFullLength : new boolean[] { true, false }) {
      for (int appendType = 4; appendType >= 0; appendType--) {

        for (size = 1; size < 384; size++) {

          root = this.createRootObject();
          textOut = this.wrap(root);
          sb = new StringBuilder();

          add = new char[size];
          for (i = 1; i < 2048; i++) {

            len = (useFullLength ? size : (1 + rand.nextInt(size)));

            for (j = len; (--j) >= 0;) {
              add[j] = ((char) (rand.nextInt(26) + 'a'));
            }

            switch ((appendType == 4) ? (rand.nextInt(appendType))
                : appendType) {
              case 0: {
                if (len >= size) {
                  textOut.append(add);
                  sb.append(add);
                  break;
                }
              }
              case 1: {
                textOut.append(add, 0, len);
                sb.append(add, 0, len);
                break;
              }
              case 2: {
                if (len >= size) {
                  s = String.valueOf(add);
                  textOut.append(s);
                  sb.append(s);
                  break;
                }
              }
              case 3: {
                s = String.valueOf(add);
                textOut.append(s, 0, len);
                sb.append(s, 0, len);
                break;
              }
            }
            Assert.assertEquals(sb.toString(), this.getString(root));
          }

        }
      }
    }
  }
}
