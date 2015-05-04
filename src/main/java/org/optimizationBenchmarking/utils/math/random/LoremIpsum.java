package org.optimizationBenchmarking.utils.math.random;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A class which can generate random text in the style of <a
 * href="http://en.wikipedia.org/wiki/Lorem_ipsum">Lorem Ipsum</a>. Such
 * text can be used as place-holder in examples or tests.
 */
public final class LoremIpsum {

  /** the internal text */
  private static final String[] TEXT = { //
    "a", //$NON-NLS-1$
    "ac", //$NON-NLS-1$
    "ad", //$NON-NLS-1$
    "at", //$NON-NLS-1$
    "ea", //$NON-NLS-1$
    "et", //$NON-NLS-1$
    "eu", //$NON-NLS-1$
    "ex", //$NON-NLS-1$
    "id", //$NON-NLS-1$
    "in", //$NON-NLS-1$
    "mi", //$NON-NLS-1$
    "no", //$NON-NLS-1$
    "te", //$NON-NLS-1$
    "ut", //$NON-NLS-1$
    "cum", //$NON-NLS-1$
    "dis", //$NON-NLS-1$
    "dui", //$NON-NLS-1$
    "duo", //$NON-NLS-1$
    "eos", //$NON-NLS-1$
    "est", //$NON-NLS-1$
    "eum", //$NON-NLS-1$
    "hac", //$NON-NLS-1$
    "leo", //$NON-NLS-1$
    "mus", //$NON-NLS-1$
    "nam", //$NON-NLS-1$
    "nec", //$NON-NLS-1$
    "non", //$NON-NLS-1$
    "per", //$NON-NLS-1$
    "qui", //$NON-NLS-1$
    "sea", //$NON-NLS-1$
    "sed", //$NON-NLS-1$
    "sem", //$NON-NLS-1$
    "sit", //$NON-NLS-1$
    "vel", //$NON-NLS-1$
    "amet", //$NON-NLS-1$
    "anim", //$NON-NLS-1$
    "ante", //$NON-NLS-1$
    "arcu", //$NON-NLS-1$
    "aute", //$NON-NLS-1$
    "cras", //$NON-NLS-1$
    "diam", //$NON-NLS-1$
    "duis", //$NON-NLS-1$
    "eget", //$NON-NLS-1$
    "elit", //$NON-NLS-1$
    "enim", //$NON-NLS-1$
    "erat", //$NON-NLS-1$
    "eros", //$NON-NLS-1$
    "esse", //$NON-NLS-1$
    "iure", //$NON-NLS-1$
    "kasd", //$NON-NLS-1$
    "nibh", //$NON-NLS-1$
    "nisi", //$NON-NLS-1$
    "nisl", //$NON-NLS-1$
    "nunc", //$NON-NLS-1$
    "odio", //$NON-NLS-1$
    "orci", //$NON-NLS-1$
    "quam", //$NON-NLS-1$
    "quis", //$NON-NLS-1$
    "quod", //$NON-NLS-1$
    "sint", //$NON-NLS-1$
    "stet", //$NON-NLS-1$
    "sunt", //$NON-NLS-1$
    "urna", //$NON-NLS-1$
    "vero", //$NON-NLS-1$
    "wisi", //$NON-NLS-1$
    "assum", //$NON-NLS-1$
    "augue", //$NON-NLS-1$
    "autem", //$NON-NLS-1$
    "class", //$NON-NLS-1$
    "clita", //$NON-NLS-1$
    "culpa", //$NON-NLS-1$
    "dolor", //$NON-NLS-1$
    "donec", //$NON-NLS-1$
    "elitr", //$NON-NLS-1$
    "etiam", //$NON-NLS-1$
    "facer", //$NON-NLS-1$
    "fames", //$NON-NLS-1$
    "felis", //$NON-NLS-1$
    "fusce", //$NON-NLS-1$
    "illum", //$NON-NLS-1$
    "ipsum", //$NON-NLS-1$
    "iusto", //$NON-NLS-1$
    "justo", //$NON-NLS-1$
    "lacus", //$NON-NLS-1$
    "liber", //$NON-NLS-1$
    "lorem", //$NON-NLS-1$
    "magna", //$NON-NLS-1$
    "massa", //$NON-NLS-1$
    "mazim", //$NON-NLS-1$
    "metus", //$NON-NLS-1$
    "minim", //$NON-NLS-1$
    "morbi", //$NON-NLS-1$
    "neque", //$NON-NLS-1$
    "netus", //$NON-NLS-1$
    "nihil", //$NON-NLS-1$
    "nobis", //$NON-NLS-1$
    "nulla", //$NON-NLS-1$
    "porta", //$NON-NLS-1$
    "proin", //$NON-NLS-1$
    "purus", //$NON-NLS-1$
    "rebum", //$NON-NLS-1$
    "risus", //$NON-NLS-1$
    "velit", //$NON-NLS-1$
    "vitae", //$NON-NLS-1$
    "zzril", //$NON-NLS-1$
    "aenean", //$NON-NLS-1$
    "aliqua", //$NON-NLS-1$
    "aptent", //$NON-NLS-1$
    "auctor", //$NON-NLS-1$
    "cillum", //$NON-NLS-1$
    "congue", //$NON-NLS-1$
    "curae;", //$NON-NLS-1$
    "cursus", //$NON-NLS-1$
    "dictum", //$NON-NLS-1$
    "dolore", //$NON-NLS-1$
    "doming", //$NON-NLS-1$
    "eirmod", //$NON-NLS-1$
    "exerci", //$NON-NLS-1$
    "fugiat", //$NON-NLS-1$
    "iriure", //$NON-NLS-1$
    "labore", //$NON-NLS-1$
    "lectus", //$NON-NLS-1$
    "libero", //$NON-NLS-1$
    "ligula", //$NON-NLS-1$
    "litora", //$NON-NLS-1$
    "luctus", //$NON-NLS-1$
    "magnis", //$NON-NLS-1$
    "mattis", //$NON-NLS-1$
    "mauris", //$NON-NLS-1$
    "mollis", //$NON-NLS-1$
    "mollit", //$NON-NLS-1$
    "montes", //$NON-NLS-1$
    "nonumy", //$NON-NLS-1$
    "nostra", //$NON-NLS-1$
    "nullam", //$NON-NLS-1$
    "option", //$NON-NLS-1$
    "ornare", //$NON-NLS-1$
    "platea", //$NON-NLS-1$
    "possim", //$NON-NLS-1$
    "primis", //$NON-NLS-1$
    "rutrum", //$NON-NLS-1$
    "sapien", //$NON-NLS-1$
    "semper", //$NON-NLS-1$
    "sociis", //$NON-NLS-1$
    "soluta", //$NON-NLS-1$
    "taciti", //$NON-NLS-1$
    "tation", //$NON-NLS-1$
    "tellus", //$NON-NLS-1$
    "tempor", //$NON-NLS-1$
    "tempus", //$NON-NLS-1$
    "tortor", //$NON-NLS-1$
    "turpis", //$NON-NLS-1$
    "varius", //$NON-NLS-1$
    "veniam", //$NON-NLS-1$
    "accusam", //$NON-NLS-1$
    "aliquam", //$NON-NLS-1$
    "aliquet", //$NON-NLS-1$
    "aliquid", //$NON-NLS-1$
    "aliquip", //$NON-NLS-1$
    "blandit", //$NON-NLS-1$
    "commodi", //$NON-NLS-1$
    "commodo", //$NON-NLS-1$
    "conubia", //$NON-NLS-1$
    "cubilia", //$NON-NLS-1$
    "dapibus", //$NON-NLS-1$
    "delenit", //$NON-NLS-1$
    "dolores", //$NON-NLS-1$
    "egestas", //$NON-NLS-1$
    "eiusmod", //$NON-NLS-1$
    "euismod", //$NON-NLS-1$
    "feugait", //$NON-NLS-1$
    "feugiat", //$NON-NLS-1$
    "finibus", //$NON-NLS-1$
    "gravida", //$NON-NLS-1$
    "iaculis", //$NON-NLS-1$
    "integer", //$NON-NLS-1$
    "laboris", //$NON-NLS-1$
    "laborum", //$NON-NLS-1$
    "lacinia", //$NON-NLS-1$
    "laoreet", //$NON-NLS-1$
    "maximus", //$NON-NLS-1$
    "natoque", //$NON-NLS-1$
    "nonummy", //$NON-NLS-1$
    "nostrud", //$NON-NLS-1$
    "officia", //$NON-NLS-1$
    "posuere", //$NON-NLS-1$
    "potenti", //$NON-NLS-1$
    "pretium", //$NON-NLS-1$
    "quisque", //$NON-NLS-1$
    "rhoncus", //$NON-NLS-1$
    "sanctus", //$NON-NLS-1$
    "sodales", //$NON-NLS-1$
    "ullamco", //$NON-NLS-1$
    "vivamus", //$NON-NLS-1$
    "viverra", //$NON-NLS-1$
    "accumsan", //$NON-NLS-1$
    "aliquyam", //$NON-NLS-1$
    "bibendum", //$NON-NLS-1$
    "deserunt", //$NON-NLS-1$
    "dictumst", //$NON-NLS-1$
    "eleifend", //$NON-NLS-1$
    "facilisi", //$NON-NLS-1$
    "faucibus", //$NON-NLS-1$
    "habitant", //$NON-NLS-1$
    "inceptos", //$NON-NLS-1$
    "incidunt", //$NON-NLS-1$
    "interdum", //$NON-NLS-1$
    "invidunt", //$NON-NLS-1$
    "lobortis", //$NON-NLS-1$
    "luptatum", //$NON-NLS-1$
    "maecenas", //$NON-NLS-1$
    "molestie", //$NON-NLS-1$
    "nascetur", //$NON-NLS-1$
    "obcaecat", //$NON-NLS-1$
    "pariatur", //$NON-NLS-1$
    "pharetra", //$NON-NLS-1$
    "placerat", //$NON-NLS-1$
    "praesent", //$NON-NLS-1$
    "proident", //$NON-NLS-1$
    "pulvinar", //$NON-NLS-1$
    "sagittis", //$NON-NLS-1$
    "senectus", //$NON-NLS-1$
    "sociosqu", //$NON-NLS-1$
    "suscipit", //$NON-NLS-1$
    "takimata", //$NON-NLS-1$
    "torquent", //$NON-NLS-1$
    "ultrices", //$NON-NLS-1$
    "vehicula", //$NON-NLS-1$
    "voluptua", //$NON-NLS-1$
    "volutpat", //$NON-NLS-1$
    "adipisici", //$NON-NLS-1$
    "consequat", //$NON-NLS-1$
    "convallis", //$NON-NLS-1$
    "cupiditat", //$NON-NLS-1$
    "curabitur", //$NON-NLS-1$
    "dignissim", //$NON-NLS-1$
    "efficitur", //$NON-NLS-1$
    "elementum", //$NON-NLS-1$
    "excepteur", //$NON-NLS-1$
    "facilisis", //$NON-NLS-1$
    "fermentum", //$NON-NLS-1$
    "fringilla", //$NON-NLS-1$
    "gubergren", //$NON-NLS-1$
    "habitasse", //$NON-NLS-1$
    "hendrerit", //$NON-NLS-1$
    "himenaeos", //$NON-NLS-1$
    "imperdiet", //$NON-NLS-1$
    "malesuada", //$NON-NLS-1$
    "penatibus", //$NON-NLS-1$
    "phasellus", //$NON-NLS-1$
    "porttitor", //$NON-NLS-1$
    "ridiculus", //$NON-NLS-1$
    "tincidunt", //$NON-NLS-1$
    "tristique", //$NON-NLS-1$
    "ultricies", //$NON-NLS-1$
    "venenatis", //$NON-NLS-1$
    "voluptate", //$NON-NLS-1$
    "vulputate", //$NON-NLS-1$
    "adipiscing", //$NON-NLS-1$
    "consetetur", //$NON-NLS-1$
    "parturient", //$NON-NLS-1$
    "sadipscing", //$NON-NLS-1$
    "vestibulum", //$NON-NLS-1$
    "condimentum", //$NON-NLS-1$
    "consectetur", //$NON-NLS-1$
    "scelerisque", //$NON-NLS-1$
    "suspendisse", //$NON-NLS-1$
    "ullamcorper", //$NON-NLS-1$
    "consectetuer", //$NON-NLS-1$
    "exercitation", //$NON-NLS-1$
    "pellentesque", //$NON-NLS-1$
    "sollicitudin", //$NON-NLS-1$
    "reprehenderit", //$NON-NLS-1$
  };

  /** the maximum word length + 1 */
  private static final int MAX_WORD_LENGTH_P1 = (LoremIpsum.TEXT[LoremIpsum.TEXT.length - 1]
      .length() + 1);

  /**
   * Append some random <a
   * href="http://en.wikipedia.org/wiki/Lorem_ipsum">Lorem Ipsum</a>, i.e.,
   * a randomized filler text.
   *
   * @param out
   *          the output
   * @param rand
   *          the randomizer
   */
  public static final void appendLoremIpsum(final ITextOutput out,
      final Random rand) {
    LoremIpsum.appendLoremIpsum(out, rand, Integer.MAX_VALUE);
  }

  /**
   * Append some random <a
   * href="http://en.wikipedia.org/wiki/Lorem_ipsum">Lorem Ipsum</a>, i.e.,
   * a randomized filler text.
   *
   * @param out
   *          the output
   * @param rand
   *          the randomizer
   * @param maxLength
   *          the maximum length of the lorem ipsum
   */
  @SuppressWarnings("fallthrough")
  public static final void appendLoremIpsum(final ITextOutput out,
      final Random rand, final int maxLength) {
    boolean firstText, newSentence;
    char lastSentenceEnd;
    int count, lastLength, curLength, sentencePartLength, sentencePartCount;
    String string, lastString;

    if (maxLength <= 0) {
      return;
    }

    firstText = true;
    sentencePartCount = sentencePartLength = 0;
    count = 0;
    lastLength = (-1);
    lastString = null;
    lastSentenceEnd = 0;

    do {
      sentencePartLength = sentencePartCount = 0;
      newSentence = true;

      if (firstText) {
        firstText = false;
      } else {
        out.append(' ');
      }

      do {

        // pick a word: different from last word
        do {
          string = LoremIpsum.TEXT[rand.nextInt(LoremIpsum.TEXT.length)];
          curLength = string.length();
        } while ((string == lastString) || //
            ((lastLength <= 2) && (curLength <= 2)) || //
            ((sentencePartLength <= 0) && (curLength == 2)));
        lastLength = curLength;
        lastString = string;

        if (newSentence) {
          // start sentence with capital letter
          out.append(Character.toUpperCase(string.charAt(0)));
          out.append(string, 1, curLength);
          newSentence = false;
        } else {

          // should we split sentence into sub-sentences?
          if ((sentencePartLength > 3) && (sentencePartCount < 3)) {
            split: {
            switcher: switch (rand.nextInt(90)) {
              case 0:
              case 1:
              case 2:
              case 3: {
                out.append(',');
                break switcher;
              }
              case 4: {
                out.append(';');
                break switcher;
              }
              case 5: {
                out.append(':');
                break switcher;
              }
              case 6: {
                out.append(' ');
                out.append('\u2012');
                break switcher;
              }
              default: {
                break split;
              }
            }

          // ok, sentence was split
          sentencePartCount++;
          sentencePartLength = 0;
          }
          }

          // append the word inside the sentence
          out.append(' ');
          out.append(string);
        }
        sentencePartLength++;

      } while (((++count) < maxLength) && //
          (sentencePartLength < 8) && //
          ((sentencePartLength <= 3) || (rand.nextInt(12) > 0)));

      // end the sentence
      switch (rand.nextInt(12)) {
        case 0: {
          if (lastSentenceEnd != '!') {
            out.append('!');
            lastSentenceEnd = '!';
            break;
          }
        }
        case 1: {
          if (lastSentenceEnd != '?') {
            out.append('?');
            lastSentenceEnd = '?';
            break;
          }
        }
        case 2: {
          if (lastSentenceEnd != '\u2026') {
            out.append('\u2026');
            lastSentenceEnd = '\u2026';
            break;
          }
        }
        default: {
          out.append('.');
          lastSentenceEnd = '.';
        }
      }
    } while ((count < maxLength) && (rand.nextInt(3) > 0));
  }

  /**
   * Get some random <a
   * href="http://en.wikipedia.org/wiki/Lorem_ipsum">Lorem Ipsum</a>, i.e.,
   * a randomized filler text.
   *
   * @param rand
   *          the randomizer
   * @param maxLength
   *          the maximum length of the lorem ipsum
   * @return the lorem ipsum
   */
  public static final String loremIpsum(final Random rand,
      final int maxLength) {
    final MemoryTextOutput out;

    if ((maxLength > 0) && (maxLength < 1000000)) {
      out = new MemoryTextOutput(maxLength * LoremIpsum.MAX_WORD_LENGTH_P1);
    } else {
      out = new MemoryTextOutput();
    }
    LoremIpsum.appendLoremIpsum(out, rand, maxLength);
    return out.toString();
  }

  /**
   * Print a lorem ipsum to {@code System.out}.
   *
   * @param args
   *          ignored
   * @throws IOException
   *           this should not happen
   */
  public static final void main(final String args[]) throws IOException {
    final AbstractTextOutput dest;
    final Random random;
    int i;

    // the writer is needed, because sometimes System.out does not support
    // unicode (seemingly)??
    try (OutputStreamWriter osw = new OutputStreamWriter(System.out)) {
      dest = AbstractTextOutput.wrap(osw);
      random = new Random();

      for (i = 0; i < 40; i++) {
        if (i > 0) {
          dest.appendLineBreak();
          dest.appendLineBreak();
        }
        LoremIpsum.appendLoremIpsum(dest, random);
      }
    }
    System.out.flush();
  }

  /** the forbidden constructor */
  private LoremIpsum() {
    ErrorUtils.doNotCall();
  }
}
