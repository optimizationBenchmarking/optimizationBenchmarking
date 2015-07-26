package examples.org.optimizationBenchmarking.utils.bibliography.data;

import java.text.Normalizer;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticleBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibBookBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInCollectionBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganizationBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReportBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibThesisBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibWebsiteBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;
import org.optimizationBenchmarking.utils.math.random.RandomUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A class to test generating a random bibliography.
 */
public final class RandomBibliography extends BibliographyExample {

  /** the randomizer */
  private final Random m_rand;

  /** the character ranges */
  private static final char[][] RANGES = { //
  { '\u03a0', '\u03f3' },//
      { '\u0410', '\u044f' },//
      { '\u0531', '\u0556' },//
      { '\u05d0', '\u05ea' },//
      { '\u0621', '\u064a' },//
      { '\u0905', '\u0939' },//
      { '\u0985', '\u09b9' },//
      { '\u0a05', '\u0a39' },//
      { '\u0a85', '\u0ab9' },//
      { '\u0b05', '\u0b39' },//
      { '\u0b85', '\u0bb9' },//
      { '\u0c05', '\u0c39' },//
      { '\u0c85', '\u0cb9' },//
      { '\u0d05', '\u0d39' },//
      { '\u0e01', '\u0e2e' },//
      { '\u0e81', '\u0eae' },//
      { '\u0f40', '\u0f69' },//
      { '\u10a0', '\u10f6' },//
      { '\uac00', '\ud768' },//
      { '\u3041', '\u3357' },//
      { '\u4e00', '\u9fa5' },//
  };

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  @Override
  public final Bibliography createBibliography() {
    int k;

    k = 0;
    try (final BibliographyBuilder bb = new BibliographyBuilder()) {

      do {

        switch (this.m_rand.nextInt(8)) {

          case 0: {
            try (final BibTechReportBuilder trb = bb.techReport()) {
              this.__randomTR(trb);
            }
            break;
          }

          case 1: {
            try (final BibInProceedingsBuilder trb = bb.inProceedings()) {
              this.__randomPaper(trb);
            }
            break;
          }

          case 2: {
            MyBibliography.__addRandomOfMine(bb, this.m_rand);
            break;
          }

          case 3: {
            try (final BibBookBuilder trb = bb.book()) {
              this.__randomBook(trb, this.m_rand.nextBoolean(),
                  Integer.MAX_VALUE);
            }
            break;
          }

          case 4: {
            try (final BibInCollectionBuilder trb = bb.inCollection()) {
              this.__randomInBook(trb);
            }
            break;
          }

          case 5: {
            try (final BibArticleBuilder trb = bb.article()) {
              this.__randomArticle(trb);
            }
            break;
          }

          case 6: {
            try (final BibThesisBuilder trb = bb.thesis()) {
              this.__randomThesis(trb);
            }
            break;
          }

          case 7: {
            try (final BibWebsiteBuilder trb = bb.website()) {
              this.__randomWeb(trb);
            }
            break;
          }

          default: {
            try (final BibProceedingsBuilder proc = bb.proceedings()) {
              this.__randomProc(proc);
            }
            break;
          }
        }
      } while (((k++) < 5) || (this.m_rand.nextInt(11) > 0));

      return bb.getResult();
    }
  }

  /**
   * Generate a random name
   *
   * @param first
   *          is it a fist name?
   * @return the random name
   */
  private final String __randomName(final boolean first) {
    final Random r;
    final StringBuilder sb;
    String s;
    int i, done;
    char ch;

    r = this.m_rand;
    sb = new StringBuilder();
    s = null;
    done = 0;

    do {
      sb.setLength(0);

      if (r.nextInt(3) > 0) {

        if (first) {
          switch (r.nextInt(23)) {
            case 0: {
              sb.append("Thomas");break;} //$NON-NLS-1$
            case 1: {
              sb.append("Alexandre");break;} //$NON-NLS-1$
            case 2: {
              sb.append("Ke");break;} //$NON-NLS-1$
            case 3: {
              sb.append("Jinlong");break;} //$NON-NLS-1$
            case 4: {
              sb.append("Raymond");break;} //$NON-NLS-1$
            case 5: {
              sb.append("J\u00f6rg");break;} //$NON-NLS-1$
            case 6: {
              sb.append("Xin");break;} //$NON-NLS-1$
            case 7: {
              sb.append("Kurt");break;} //$NON-NLS-1$
            case 8: {
              sb.append("Steffen");break;} //$NON-NLS-1$
            case 9: {
              sb.append("Sebastian");break;} //$NON-NLS-1$
            case 10: {
              sb.append("Roland");break;} //$NON-NLS-1$
            case 11: {
              sb.append("Michael");break;} //$NON-NLS-1$
            case 12: {
              sb.append("Frank");break;} //$NON-NLS-1$
            case 13: {
              sb.append("Claus");break;} //$NON-NLS-1$
            case 14: {
              sb.append("Eva");break;} //$NON-NLS-1$
            case 15: {
              sb.append("Maria");break;} //$NON-NLS-1$
            case 16: {
              sb.append("Ru");break;} //$NON-NLS-1$
            case 17: {
              sb.append("Diana");break;} //$NON-NLS-1$
            case 18: {
              sb.append("Anne");break;} //$NON-NLS-1$
            case 19: {
              sb.append("Sylvia");break;} //$NON-NLS-1$
            case 20: {
              sb.append("Nicole");break;} //$NON-NLS-1$
            case 21: {
              sb.append("Yan");break;} //$NON-NLS-1$
            default: {
              sb.append("Wei");break;} //$NON-NLS-1$
          }
        } else {
          switch (r.nextInt(25)) {
            case 0: {
              sb.append("Weise");break;} //$NON-NLS-1$
            case 1: {
              sb.append("Devert");break;} //$NON-NLS-1$
            case 2: {
              sb.append("Tang");break;} //$NON-NLS-1$
            case 3: {
              sb.append("Li");break;} //$NON-NLS-1$
            case 4: {
              sb.append("Chiong");break;} //$NON-NLS-1$
            case 5: {
              sb.append("L\u00e4ssig");break;} //$NON-NLS-1$
            case 6: {
              sb.append("Yao");break;} //$NON-NLS-1$
            case 7: {
              sb.append("Geihs");break;} //$NON-NLS-1$
            case 8: {
              sb.append("Bleul");break;} //$NON-NLS-1$
            case 9: {
              sb.append("Degenkold");break;} //$NON-NLS-1$
            case 10: {
              sb.append("Reichle");break;} //$NON-NLS-1$
            case 11: {
              sb.append("Zapf");break;} //$NON-NLS-1$
            case 12: {
              sb.append("Stolle");break;} //$NON-NLS-1$
            case 13: {
              sb.append("Wagner");break;} //$NON-NLS-1$
            case 14: {
              sb.append("Beyer");break;} //$NON-NLS-1$
            case 15: {
              sb.append("M\u00fcller");break;} //$NON-NLS-1$
            case 16: {
              sb.append("Fischer");break;} //$NON-NLS-1$
            case 17: {
              sb.append("Mayer");break;} //$NON-NLS-1$
            case 18: {
              sb.append("Schmidt");break;} //$NON-NLS-1$
            case 19: {
              sb.append("Sch\u00fcppel");break;} //$NON-NLS-1$
            case 20: {
              sb.append("Schneider");break;} //$NON-NLS-1$
            case 21: {
              sb.append("Sachse");break;} //$NON-NLS-1$
            case 22: {
              sb.append("Schulze");break;} //$NON-NLS-1$
            case 23: {
              sb.append("Wei\u00df");break;} //$NON-NLS-1$
            default: {
              sb.append("Wu");break;} //$NON-NLS-1$
          }
        }

      } else {

        sb.append((char) ('A' + r.nextInt(26)));
        do {
          sb.append((char) ('a' + r.nextInt(26)));
        } while (((r.nextInt(6) > 0) && (sb.length() < 10))
            || (sb.length() < 3));

        for (i = (sb.length() >> 2); (--i) >= 0;) {
          switch (r.nextInt(4)) {
            case 0: {
              ch = 'a';
              break;
            }
            case 1: {
              ch = 'e';
              break;
            }
            case 2: {
              ch = 'i';
              break;
            }
            default: {
              ch = 'o';
              break;
            }
          }
          sb.insert((1 + r.nextInt(sb.length())), ch);
        }
      }

      if (s != null) {
        sb.insert(0, (r.nextBoolean() ? '-' : ' '));
        sb.insert(0, s);
      }
      s = sb.toString();
    } while (((++done) < 3) && (r.nextInt(4) <= 0));

    if (first || (r.nextInt(5) > 0)) {
      return s;
    }
    switch (r.nextInt(9)) {
      case 0: {
        sb.insert(0, "d'");break;} //$NON-NLS-1$
      case 1: {
        sb.insert(0, "Von ");break;} //$NON-NLS-1$
      case 2: {
        sb.insert(0, "Von und Zu ");break;} //$NON-NLS-1$
      case 3: {
        sb.insert(0, "Graf von ");break;} //$NON-NLS-1$
      case 4: {
        sb.insert(0, "Van ");break;} //$NON-NLS-1$
      case 5: {
        sb.insert(0, "Van der ");break;} //$NON-NLS-1$
      case 6: {
        sb.insert(0, "De La ");break;} //$NON-NLS-1$
      case 7: {
        sb.insert(0, "Lord of ");break;} //$NON-NLS-1$
      default: {
        sb.insert(0, "Earl of ");break;} //$NON-NLS-1$
    }

    return sb.toString();
  }

  /**
   * can we use the given character?
   *
   * @param ch
   *          the character
   * @return the normalized character if yes, 0 if no
   */
  private static final char __canUseChar(final char ch) {
    String charString, plainString;
    char t;

    if ((!(Character.isDefined(ch))) || Character.isDigit(ch)
        || Character.isISOControl(ch) || Character.isSpaceChar(ch)
        || Character.isWhitespace(ch) || Character.isWhitespace(ch)
        || (!(Character.isValidCodePoint(ch)))
        || Character.isSurrogate(ch) || (!(Character.isBmpCodePoint(ch)))
        || Character.isHighSurrogate(ch) || Character.isLowSurrogate(ch)
        || Character.isTitleCase(ch)
        || Character.isIdentifierIgnorable(ch)
        || Character.isSupplementaryCodePoint(ch)) {
      return 0;
    }

    charString = String.valueOf(ch);
    plainString = Normalizer.normalize(charString,
        TextUtils.DEFAULT_NORMALIZER_FORM);
    if (plainString.length() != 1) {
      return 0;
    }
    try {
      Integer.parseInt(plainString);
      return 0;
    } catch (final Throwable tt) {
      //
    }

    t = plainString.charAt(0);
    if ((t > ' ') && //
        ((t < '0') || (t > '9')) && //
        ((t < 'a') || (t > 'z')) && //
        ((t < 'A') || (t > 'Z'))) {
      if ((t < '0') || //
          ((t > '9') && (t < 'A')) || //
          ((t > 'A') && (t < 'a')) || //
          ((t > 'z') && (t < 0x7e))) {
        return 0;
      }

      return t;
    }
    return 0;
  }

  /**
   * Generate a random original spelling
   *
   * @return the random spelling
   */
  private final String __randomOriginalSpelling() {
    final char[] range;
    final Random r;
    final StringBuilder sb;
    char ch, prev;

    r = this.m_rand;
    range = RandomBibliography.RANGES[r
        .nextInt(RandomBibliography.RANGES.length)];
    sb = new StringBuilder();

    prev = 0;
    main: do {
      do {
        ch = RandomBibliography.__canUseChar((char) (range[0] + (r
            .nextInt((range[1] - range[0]) + 1))));
      } while ((ch <= ' ') || (ch == prev));
      prev = ch;
      if (sb.length() <= 0) {
        ch = TextUtils.toUpperCase(ch);
        if (Character.isLowerCase(ch)) {
          continue main;
        }
      } else {
        ch = TextUtils.toLowerCase(ch);
        if (Character.isUpperCase(ch) || Character.isTitleCase(ch)) {
          continue main;
        }
      }
      sb.append(ch);
    } while (((sb.length() <= 1) || (r.nextInt(6) > 0))
        && (sb.length() < 8));

    return sb.toString();
  }

  /**
   * Generate a random title
   *
   * @return the random title
   */
  private final String __randomTitle() {
    StringBuilder sb;
    Random r;

    sb = new StringBuilder();
    r = this.m_rand;

    switch (r.nextInt(13)) {
      case 0: {
        sb.append("Applications of ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("The Use of ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Improvements of ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Utilization of ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Novel Approach to ");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Detection of Flaws in ");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Classification of ");break;} //$NON-NLS-1$
      case 7: {
        sb.append("New Ideas for ");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Recent Advances in ");break;} //$NON-NLS-1$
      case 9: {
        sb.append("History of ");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Variants of ");break;} //$NON-NLS-1$
      case 11: {
        sb.append("A Study of ");break;} //$NON-NLS-1$
      default: {
        sb.append("A Note on ");break;} //$NON-NLS-1$
    }

    inner: for (;;) {
      switch (r.nextInt(14)) {
        case 0: {
          sb.append("Supervised ");break;} //$NON-NLS-1$
        case 1: {
          sb.append("Unsupervised ");break;} //$NON-NLS-1$
        case 2: {
          sb.append("Semi-Supervised ");break;} //$NON-NLS-1$
        case 3: {
          sb.append("Multi-Objective ");break;} //$NON-NLS-1$
        case 4: {
          sb.append("Many-Objective ");break;} //$NON-NLS-1$
        case 5: {
          sb.append("Self-Adaptive ");break;} //$NON-NLS-1$
        case 6: {
          sb.append("Robust ");break;} //$NON-NLS-1$
        case 7: {
          sb.append("Parallel ");break;} //$NON-NLS-1$
        case 8: {
          sb.append("Distributed ");break;} //$NON-NLS-1$
        case 9: {
          sb.append("Orthogonalized ");break;} //$NON-NLS-1$
        case 10: {
          sb.append("Hybrid ");break;} //$NON-NLS-1$
        case 11: {
          sb.append("Intelligent ");break;} //$NON-NLS-1$
        case 12: {
          sb.append("Interactive ");break;} //$NON-NLS-1$
        default: {/* */
        }
      }

      switch (r.nextInt(14)) {
        case 0: {
          sb.append("Evolutionary Algorithms ");break;} //$NON-NLS-1$
        case 1: {
          sb.append("Neural Networks ");break;} //$NON-NLS-1$
        case 2: {
          sb.append("Machine Learning ");break;} //$NON-NLS-1$
        case 3: {
          sb.append("Evolution Strategies ");break;} //$NON-NLS-1$
        case 4: {
          sb.append("Evolutionary Programming ");break;} //$NON-NLS-1$
        case 5: {
          sb.append("Fitness Assignment ");break;} //$NON-NLS-1$
        case 6: {
          sb.append("Genetic Programming ");break;} //$NON-NLS-1$
        case 7: {
          sb.append("Genetic Algorithms ");break;} //$NON-NLS-1$
        case 8: {
          sb.append("Simulated Annealing ");break;} //$NON-NLS-1$
        case 9: {
          sb.append("Tabu Search ");break;} //$NON-NLS-1$
        case 10: {
          sb.append("Ant Colony Optimization ");break;} //$NON-NLS-1$
        case 11: {
          sb.append("Local Search ");break;} //$NON-NLS-1$
        case 12: {
          sb.append("Memetic Algorithms ");break;} //$NON-NLS-1$
        default: {
          sb.append("Particle Swarm Optimization ");break;} //$NON-NLS-1$
      }
      if (r.nextBoolean()) {
        break inner;
      }

      switch (r.nextInt(5)) {
        case 0: {
          sb.append("hybridized with ");break;} //$NON-NLS-1$
        case 1: {
          sb.append("complemented with ");break;} //$NON-NLS-1$
        case 2: {
          sb.append("embedded in ");break;} //$NON-NLS-1$
        default: {
          break inner;
        }
      }
    }

    if (r.nextBoolean()) {
      switch (r.nextInt(6)) {
        case 0: {
          sb.append("applied to ");break;} //$NON-NLS-1$
        case 1: {
          sb.append("used for ");break;} //$NON-NLS-1$
        case 2: {
          sb.append("in the Domain of ");break;} //$NON-NLS-1$
        case 3: {
          sb.append("for ");break;} //$NON-NLS-1$
        case 4: {
          sb.append("in the Field of ");break;} //$NON-NLS-1$
        case 5: {
          sb.append("with Applications in ");break;} //$NON-NLS-1$
        default: {/* */
        }
      }

      switch (r.nextInt(21)) {
        case 0: {
          sb.append("Big Data");break;} //$NON-NLS-1$
        case 1: {
          sb.append("Image Processing");break;} //$NON-NLS-1$
        case 2: {
          sb.append("Road Gridding");break;} //$NON-NLS-1$
        case 3: {
          sb.append("the Traveling Salesman Problem");break;} //$NON-NLS-1$
        case 4: {
          sb.append("Logistic Planning");break;} //$NON-NLS-1$
        case 5: {
          sb.append("Satisfiability Problems");break;} //$NON-NLS-1$
        case 6: {
          sb.append("Bin Packing");break;} //$NON-NLS-1$
        case 7: {
          sb.append("Clustering");break;} //$NON-NLS-1$
        case 8: {
          sb.append("Robust Optimization");break;} //$NON-NLS-1$
        case 9: {
          sb.append("the N-K Landscape");break;} //$NON-NLS-1$
        case 10: {
          sb.append("Multi-Level Bi-Partitioning");break;} //$NON-NLS-1$
        case 11: {
          sb.append("Airline Crew Assignment");break;} //$NON-NLS-1$
        case 12: {
          sb.append("Knapsack Problems");break;} //$NON-NLS-1$
        case 13: {
          sb.append("Artwork Synthesis");break;} //$NON-NLS-1$
        case 14: {
          sb.append("Organic Chemistry");break;} //$NON-NLS-1$
        case 15: {
          sb.append("Agriculture");break;} //$NON-NLS-1$
        case 16: {
          sb.append("Space Exploration");break;} //$NON-NLS-1$
        case 17: {
          sb.append("Engineering Design");break;} //$NON-NLS-1$
        case 18: {
          sb.append("User Preference Detection");break;} //$NON-NLS-1$
        case 19: {
          sb.append("Systems Security");break;} //$NON-NLS-1$
        default: {
          sb.append("Landscaping");break;} //$NON-NLS-1$
      }
    }
    return sb.toString().trim();
  }

  /**
   * Generate a random address
   *
   * @return the random address
   */
  private final String __randomAddress() {
    final Random r;
    final StringBuilder sb;
    String s;
    int i;
    char ch;

    r = this.m_rand;
    sb = new StringBuilder();
    s = null;

    if (r.nextBoolean()) {

      switch (r.nextInt(31)) {
        case 0: {
          return ("Hefei, Anui, China");} //$NON-NLS-1$
        case 1: {
          return ("Suzhou, Jiangsu, China");} //$NON-NLS-1$
        case 2: {
          return ("Shanghai, China");} //$NON-NLS-1$
        case 3: {
          return ("Berlin, Germany");} //$NON-NLS-1$
        case 4: {
          return ("Chemnitz, Sachsen, Germany");} //$NON-NLS-1$
        case 5: {
          return ("Kassel, Hessen, Germany");} //$NON-NLS-1$
        case 6: {
          return ("Dortmund, Germany");} //$NON-NLS-1$
        case 7: {
          return ("New York, USA");} //$NON-NLS-1$
        case 8: {
          return ("Los Angeles, California, USA");} //$NON-NLS-1$
        case 9: {
          return ("Portland, Oregon, USA");} //$NON-NLS-1$
        case 10: {
          return ("London, England, UK");} //$NON-NLS-1$
        case 11: {
          return ("Birmingham, England, UK");} //$NON-NLS-1$
        case 12: {
          return ("Paris, France");} //$NON-NLS-1$
        case 13: {
          return ("Madrid, Spain");} //$NON-NLS-1$
        case 14: {
          return ("Barcelona, Spain");} //$NON-NLS-1$
        case 15: {
          return ("Roma, Italy");} //$NON-NLS-1$
        case 16: {
          return ("Athens, Greece");} //$NON-NLS-1$
        case 17: {
          return ("Lisbon, Portugal");} //$NON-NLS-1$
        case 18: {
          return ("Amsterdam, The Netherlands");} //$NON-NLS-1$
        case 19: {
          return ("K\u00f8benhavn, Denmark");} //$NON-NLS-1$
        case 20: {
          return ("Warsaw, Poland");} //$NON-NLS-1$
        case 21: {
          return ("Krakow, Poland");} //$NON-NLS-1$
        case 22: {
          return ("Moscow, Russia");} //$NON-NLS-1$
        case 23: {
          return ("Tokio, Japan");} //$NON-NLS-1$
        case 24: {
          return ("Melburne, Australia");} //$NON-NLS-1$
        case 25: {
          return ("Sydney, Australia");} //$NON-NLS-1$
        case 26: {
          return ("Beijing, China");} //$NON-NLS-1$
        case 27: {
          return ("San Sebasti\u00e1n, Spain");} //$NON-NLS-1$
        case 28: {
          return ("Vienna, Austria");} //$NON-NLS-1$
        case 29: {
          return ("Krak\u00f3w, Poland");} //$NON-NLS-1$
        default: {
          return ("Prague, Czech Republic");} //$NON-NLS-1$
      }

    }

    do {
      sb.setLength(0);

      sb.append((char) ('A' + r.nextInt(26)));
      do {
        sb.append((char) ('a' + r.nextInt(26)));
      } while (((r.nextInt(6) > 0) && (sb.length() < 10))
          || (sb.length() < 3));

      for (i = (sb.length() >> 2); (--i) >= 0;) {
        switch (r.nextInt(4)) {
          case 0: {
            ch = 'a';
            break;
          }
          case 1: {
            ch = 'e';
            break;
          }
          case 2: {
            ch = 'i';
            break;
          }
          default: {
            ch = 'o';
            break;
          }
        }
        sb.insert((1 + r.nextInt(sb.length())), ch);
      }

      if (s != null) {
        sb.insert(0, (r.nextBoolean() ? "-" : ", ")); //$NON-NLS-2$//$NON-NLS-1$
        sb.insert(0, s);
      }
      s = sb.toString();
    } while (r.nextInt(2) <= 0);

    switch (r.nextInt(16)) {
      case 0: {
        sb.append(", China");break;} //$NON-NLS-1$
      case 1: {
        sb.append(", Germany");break;} //$NON-NLS-1$
      case 2: {
        sb.append(", Japan");break;} //$NON-NLS-1$
      case 3: {
        sb.append(", UK");break;} //$NON-NLS-1$
      case 4: {
        sb.append(", USA");break;} //$NON-NLS-1$
      case 5: {
        sb.append(", France");break;} //$NON-NLS-1$
      case 6: {
        sb.append(", Poland");break;} //$NON-NLS-1$
      case 7: {
        sb.append(", Austria");break;} //$NON-NLS-1$
      case 8: {
        sb.append(", Australia");break;} //$NON-NLS-1$
      case 9: {
        sb.append(", Sweden");break;} //$NON-NLS-1$
      case 10: {
        sb.append(", Czech Republic");break;} //$NON-NLS-1$
      case 11: {
        sb.append(", Vietnam");break;} //$NON-NLS-1$
      case 12: {
        sb.append(", Australia");break;} //$NON-NLS-1$
      case 13: {
        sb.append(", Spain");break;} //$NON-NLS-1$
      case 14: {
        sb.append(", Switzerland");break;} //$NON-NLS-1$
      default: {
        sb.append(", South Africa");break;} //$NON-NLS-1$

    }

    return sb.toString();
  }

  /**
   * make a random organization
   *
   * @param needsInstitute
   *          do we need an institute
   * @param o
   *          the builder
   */
  private final void __randomOrganization(final BibOrganizationBuilder o,
      final boolean needsInstitute) {
    o.setAddress(this.__randomAddress());
    if (needsInstitute || this.m_rand.nextBoolean()) {
      o.setName(this.__randomInstituteName());
    }
    if (this.m_rand.nextInt(4) <= 0) {
      o.setOriginalSpelling(this.__randomOriginalSpelling());
    }
  }

  /**
   * Generate a random institute name
   *
   * @return the random institute name
   */
  private final String __randomInstituteName() {
    final Random r;
    final StringBuilder sb;
    String s;
    int i;
    char ch;

    r = this.m_rand;
    sb = new StringBuilder();
    s = null;

    switch (r.nextInt(3)) {
      case 0: {
        sb.append(this.__randomName(false));
        break;
      }
      case 1: {
        s = this.__randomAddress();
        i = s.indexOf(' ');
        if (i > 0) {
          s = s.substring(0, i);
        }
        i = s.indexOf(',');
        if (i > 0) {
          s = s.substring(0, i);
        }
        sb.append(s.trim());
        break;
      }

      default: {

        do {
          sb.setLength(0);
          sb.append((char) ('A' + r.nextInt(26)));
          do {
            sb.append((char) ('a' + r.nextInt(26)));
          } while (((r.nextInt(6) > 0) && (sb.length() < 10))
              || (sb.length() < 3));

          for (i = (sb.length() >> 2); (--i) >= 0;) {
            switch (r.nextInt(4)) {
              case 0: {
                ch = 'a';
                break;
              }
              case 1: {
                ch = 'e';
                break;
              }
              case 2: {
                ch = 'i';
                break;
              }
              default: {
                ch = 'o';
                break;
              }
            }
            sb.insert((1 + r.nextInt(sb.length())), ch);
          }

          if (s != null) {
            sb.insert(0, (r.nextBoolean() ? '-' : ' '));
            sb.insert(0, s);
          }
          s = sb.toString();
        } while (r.nextInt(4) <= 0);
      }
    }

    switch (r.nextInt(11)) {
      case 0: {
        sb.append(" Publishers");break;} //$NON-NLS-1$
      case 1: {
        sb.insert(0, "University of ");break;} //$NON-NLS-1$
      case 2: {
        sb.append(" University");break;} //$NON-NLS-1$
      case 3: {
        sb.append(" School");break;} //$NON-NLS-1$
      case 4: {
        sb.append(" Hochschule");break;} //$NON-NLS-1$
      case 5: {
        sb.append(" GmbH");break;} //$NON-NLS-1$
      case 6: {
        sb.append(" Co.");break;} //$NON-NLS-1$
      case 7: {
        sb.append(" Ltd.");break;} //$NON-NLS-1$
      case 8: {
        sb.append(" Verlag");break;} //$NON-NLS-1$
      case 9: {
        sb.insert(0, "Technische Universit\u00e4t ");break;} //$NON-NLS-1$
      default: {
        sb.append(" Institute");break;} //$NON-NLS-1$
    }

    return sb.toString();
  }

  /**
   * Generate a random date
   *
   * @return the date
   */
  private final long __randomDate() {
    long l;

    l = (1000L * 60L * 60L * 24L * 365L * 30L);

    return (System.currentTimeMillis() - Math.abs(this.m_rand.nextLong()
        % l));
  }

  /**
   * Generate a random url
   *
   * @return the url
   */
  private final String __randomURL() {
    final StringBuilder sb;
    final Random r;

    r = this.m_rand;
    sb = new StringBuilder();

    switch (r.nextInt(4)) {
      case 0: {
        sb.append("http://www");break;} //$NON-NLS-1$
      case 1: {
        sb.append("ftp://ftp");break;} //$NON-NLS-1$
      case 2: {
        sb.append("http://blog");break;} //$NON-NLS-1$
      default: {
        sb.append("http://web");break;} //$NON-NLS-1$
    }

    do {
      sb.append('.');
      sb.append(RandomUtils.longToString(null, r.nextLong()));
    } while (r.nextBoolean());

    switch (r.nextInt(10)) {
      case 0: {
        sb.append(".de");break;} //$NON-NLS-1$
      case 1: {
        sb.append(".com");break;} //$NON-NLS-1$
      case 2: {
        sb.append(".org");break;} //$NON-NLS-1$
      case 3: {
        sb.append(".cn");break;} //$NON-NLS-1$
      case 4: {
        sb.append(".edu");break;} //$NON-NLS-1$
      case 5: {
        sb.append(".edu.cn");break;} //$NON-NLS-1$
      case 6: {
        sb.append(".co.uk");break;} //$NON-NLS-1$
      case 7: {
        sb.append(".ac.uk");break;} //$NON-NLS-1$
      case 8: {
        sb.append(".au");break;} //$NON-NLS-1$
      default: {
        sb.append(".net");break;} //$NON-NLS-1$
    }
    do {
      sb.append('/');
      sb.append(RandomUtils.longToString(null, r.nextLong()));
    } while (r.nextBoolean());

    switch (r.nextInt(8)) {
      case 0: {
        sb.append(".pdf");break;} //$NON-NLS-1$
      case 1: {
        sb.append(".html");break;} //$NON-NLS-1$
      case 2: {
        sb.append(".ppt");break;} //$NON-NLS-1$
      case 3: {
        sb.append(".txt");break;} //$NON-NLS-1$
      case 4: {
        sb.append(".doc");break;} //$NON-NLS-1$
      case 5: {
        sb.append(".ps");break;} //$NON-NLS-1$
      case 6: {
        sb.append(".tex");break;} //$NON-NLS-1$
      default: {
        sb.append(".htm");break;} //$NON-NLS-1$
    }

    return sb.toString();
  }

  /**
   * Generate a random isbn
   *
   * @return the random isbn
   */
  private final String __randomISBN() {
    final Random r;
    final StringBuilder sb;

    sb = new StringBuilder();
    r = this.m_rand;

    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append('-');
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append('-');
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append('-');
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append('-');
    sb.append((char) ('0' + r.nextInt(10)));

    return sb.toString();
  }

  /**
   * Generate a random issn
   *
   * @return the random issn
   */
  private final String __randomISSN() {
    final Random r;
    final StringBuilder sb;

    sb = new StringBuilder();
    r = this.m_rand;

    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append('-');
    sb.append('-');
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));
    sb.append((char) ('0' + r.nextInt(10)));

    return sb.toString();
  }

  /**
   * Generate a random doi
   *
   * @return the random doi
   */
  private final String __randomDoi() {
    final Random r;
    final StringBuilder sb;
    int i, j;

    sb = new StringBuilder();
    r = this.m_rand;

    sb.append("10"); //$NON-NLS-1$
    i = 0;
    do {
      do {
        i++;
        switch (r.nextInt(3)) {
          case 0: {
            sb.append('.');
            break;
          }
          case 1: {
            sb.append('_');
            break;
          }
          default: {
            sb.append('/');
            break;
          }
        }
        j = 0;
        do {
          sb.append((char) ('0' + r.nextInt(10)));
        } while (((++j) < 7) && (r.nextInt(5) > 0));
      } while (r.nextBoolean());
      if (r.nextBoolean()) {
        sb.append(this.__randomISBN());
      }
    } while ((i < 3) || (r.nextBoolean()));

    return sb.toString();
  }

  /**
   * Generate a random proceedings name
   *
   * @return the random proceedings name
   */
  private final String __randomProceedingsName() {
    final Random r;
    final StringBuilder sb;
    int x;

    sb = new StringBuilder();
    r = this.m_rand;

    switch (r.nextInt(3)) {
      case 0: {
        sb.append("Proceedings of the ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Revised and Selected Papers of the ");break;} //$NON-NLS-1$
      default: {
        sb.append("Selected Papers from the ");break;} //$NON-NLS-1$
    }

    switch (r.nextInt(6)) {
      case 0: {
        sb.append("First ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Second ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Third ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Fourth ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Fifth ");break;} //$NON-NLS-1$
      default: {
        x = 6 + r.nextInt(20);
        sb.append(x);
        switch (x % 10) {
          case 1: {
            sb.append("st ");break;}//$NON-NLS-1$
          case 2: {
            sb.append("nd ");break;}//$NON-NLS-1$
          case 3: {
            sb.append("rd ");break;}//$NON-NLS-1$
          default: {
            sb.append("th ");break;}//$NON-NLS-1$
        }
        break;
      }
    }

    switch (r.nextInt(9)) {
      case 0: {
        sb.append("International ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("German ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("French ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("National ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Asian ");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Australian ");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Japanese ");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Joint ");break;} //$NON-NLS-1$
      default: {
        sb.append("European ");break;} //$NON-NLS-1$
    }

    switch (r.nextInt(5)) {
      case 0: {
        sb.append("Workshop on ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Conference on ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Meeting on ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Symposium on ");break;} //$NON-NLS-1$
      default: {
        sb.append("Multi-Conference on ");break;} //$NON-NLS-1$
    }
    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Supervised ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Unsupervised ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Semi-Supervised ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Multi-Objective ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Many-Objective ");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Self-Adaptive ");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Robust ");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Parallel ");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Distributed ");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Orthogonalized ");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Hybrid ");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Intelligent ");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Interactive ");break;} //$NON-NLS-1$
      default: {/* */
      }
    }

    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Evolutionary Algorithms");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Neural Networks");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Machine Learning");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Evolution Strategies");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Evolutionary Programming");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Fitness Assignment");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Genetic Programming");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Genetic Algorithms");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Simulated Annealing");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Tabu Search");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Ant Colony Optimization");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Local Search");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Memetic Algorithms");break;} //$NON-NLS-1$
      default: {
        sb.append("Particle Swarm Optimization");break;} //$NON-NLS-1$
    }

    return sb.toString();
  }

  /**
   * Generate a random book name
   *
   * @return the random book name
   */
  private final String __randomBookName() {
    final Random r;
    final StringBuilder sb;

    sb = new StringBuilder();
    r = this.m_rand;

    switch (r.nextInt(5)) {
      case 0: {
        sb.append("Advances in ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("New Ideas in ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Novel Approaches to ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Collected Thoughts on ");break;} //$NON-NLS-1$
      default: {
        //
      }
    }
    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Supervised ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Unsupervised ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Semi-Supervised ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Multi-Objective ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Many-Objective ");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Self-Adaptive ");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Robust ");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Parallel ");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Distributed ");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Orthogonalized ");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Hybrid ");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Intelligent ");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Interactive ");break;} //$NON-NLS-1$
      default: {/* */
      }
    }

    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Evolutionary Algorithms");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Neural Networks");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Machine Learning");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Evolution Strategies");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Evolutionary Programming");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Fitness Assignment");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Genetic Programming");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Genetic Algorithms");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Simulated Annealing");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Tabu Search");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Ant Colony Optimization");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Local Search");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Memetic Algorithms");break;} //$NON-NLS-1$
      default: {
        sb.append("Particle Swarm Optimization");break;} //$NON-NLS-1$
    }

    return sb.toString();
  }

  /**
   * Generate a random journal name
   *
   * @return the random journal name
   */
  private final String __randomJournalName() {
    final Random r;
    final StringBuilder sb;
    boolean needs;

    sb = new StringBuilder();
    r = this.m_rand;

    needs = false;
    switch (r.nextInt(4)) {
      case 0: {
        sb.append("Advances in ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("International Journal of ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Transactions on ");break;} //$NON-NLS-1$
      default: {
        needs = true;
      }
    }
    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Supervised ");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Unsupervised ");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Semi-Supervised ");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Multi-Objective ");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Many-Objective ");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Self-Adaptive ");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Robust ");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Parallel ");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Distributed ");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Orthogonalized ");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Hybrid ");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Intelligent ");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Interactive ");break;} //$NON-NLS-1$
      default: {/* */
      }
    }

    switch (r.nextInt(14)) {
      case 0: {
        sb.append("Evolutionary Algorithms");break;} //$NON-NLS-1$
      case 1: {
        sb.append("Neural Networks");break;} //$NON-NLS-1$
      case 2: {
        sb.append("Machine Learning");break;} //$NON-NLS-1$
      case 3: {
        sb.append("Evolution Strategies");break;} //$NON-NLS-1$
      case 4: {
        sb.append("Evolutionary Programming");break;} //$NON-NLS-1$
      case 5: {
        sb.append("Fitness Assignment");break;} //$NON-NLS-1$
      case 6: {
        sb.append("Genetic Programming");break;} //$NON-NLS-1$
      case 7: {
        sb.append("Genetic Algorithms");break;} //$NON-NLS-1$
      case 8: {
        sb.append("Simulated Annealing");break;} //$NON-NLS-1$
      case 9: {
        sb.append("Tabu Search");break;} //$NON-NLS-1$
      case 10: {
        sb.append("Ant Colony Optimization");break;} //$NON-NLS-1$
      case 11: {
        sb.append("Local Search");break;} //$NON-NLS-1$
      case 12: {
        sb.append("Memetic Algorithms");break;} //$NON-NLS-1$
      default: {
        sb.append("Particle Swarm Optimization");break;} //$NON-NLS-1$
    }

    if (needs) {
      switch (r.nextInt(4)) {
        case 0: {
          sb.append(" Letters");break;} //$NON-NLS-1$
        case 1: {
          sb.append(" Magazine");break;} //$NON-NLS-1$
        case 2: {
          sb.append(" News");break;} //$NON-NLS-1$
        default: {
          sb.append(" Periodical");break;} //$NON-NLS-1$
      }
    }

    return sb.toString();
  }

  /**
   * random authors
   *
   * @param abs
   *          the authors builder
   * @param max
   *          the maximum number authors
   */
  private final void __randomAuthors(final BibAuthorsBuilder abs,
      final int max) {
    int i;
    i = 0;
    do {
      try (final BibAuthorBuilder ab = abs.author()) {
        ab.setFamilyName(this.__randomName(false));
        ab.setPersonalName(this.__randomName(true));
        if (this.m_rand.nextInt(4) <= 0) {
          ab.setOriginalSpelling(this.__randomOriginalSpelling());
        }
      } catch (final Throwable tt) {
        // ignore
      }
      i++;
    } while ((i < max) && (this.m_rand.nextInt(3) > 0));
  }

  /**
   * Generate a random series
   *
   * @return the random series
   */
  private final String __randomSeries() {
    switch (this.m_rand.nextInt(7)) {
      case 0: {
        return "Lecture Notes in Evolutionary Algorithms";} //$NON-NLS-1$
      case 1: {
        return "Lecture Notes in Computer Sciences";} //$NON-NLS-1$
      case 2: {
        return "Advanced Studes in Machine Learning";} //$NON-NLS-1$
      case 3: {
        return "Genetic Programming Studies";} //$NON-NLS-1$
      case 4: {
        return "Machine Discussions";} //$NON-NLS-1$
      case 5: {
        return "EC for Dummies";} //$NON-NLS-1$
      default: {
        return "Novel Classification Series";} //$NON-NLS-1$
    }
  }

  /**
   * make tech report
   *
   * @param trb
   *          the builder
   */
  private final void __randomTR(final BibTechReportBuilder trb) {
    trb.setTitle(this.__randomTitle());

    try (final BibAuthorsBuilder abs = trb.setAuthors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    try (final BibOrganizationBuilder ob = trb.publisher()) {
      this.__randomOrganization(ob, true);
    }
    try (final BibDateBuilder db = trb.date()) {
      db.fromTime(this.__randomDate());
    }

    if (this.m_rand.nextBoolean()) {
      trb.setURL(this.__randomURL());
    }
    if (this.m_rand.nextBoolean()) {
      trb.setDOI(this.__randomDoi());
    }
  }

  /**
   * make website
   *
   * @param trb
   *          the builder
   */
  private final void __randomWeb(final BibWebsiteBuilder trb) {
    trb.setTitle(this.__randomTitle());

    try (final BibAuthorsBuilder abs = trb.setAuthors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    try (final BibOrganizationBuilder ob = trb.publisher()) {
      this.__randomOrganization(ob, true);
    }
    try (final BibDateBuilder db = trb.date()) {
      db.fromTime(this.__randomDate());
    }

    trb.setURL(this.__randomURL());
  }

  /**
   * make conference paper
   *
   * @param ip
   *          the builder
   */
  private final void __randomPaper(final BibInProceedingsBuilder ip) {
    int p;

    ip.setTitle(this.__randomTitle());

    try (final BibAuthorsBuilder abs = ip.setAuthors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    try (final BibProceedingsBuilder bp = ip.proceedings()) {
      this.__randomProc(bp);
    }
    if (this.m_rand.nextBoolean()) {
      ip.setURL(this.__randomURL());
    }
    if (this.m_rand.nextBoolean()) {
      ip.setDOI(this.__randomDoi());
    }

    p = this.m_rand.nextInt(3000) + 1;
    ip.setStartPage(String.valueOf(p));
    p += this.m_rand.nextInt(20);
    ip.setEndPage(String.valueOf(p));

    if (this.m_rand.nextBoolean()) {
      ip.setChapter(String.valueOf(1 + this.m_rand.nextInt(100)));
    }
  }

  /**
   * make an article
   *
   * @param ip
   *          the builder
   */
  private final void __randomArticle(final BibArticleBuilder ip) {
    int p;

    ip.setTitle(this.__randomTitle());

    try (final BibAuthorsBuilder abs = ip.setAuthors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    if (this.m_rand.nextBoolean()) {
      ip.setURL(this.__randomURL());
    }
    if (this.m_rand.nextBoolean()) {
      ip.setDOI(this.__randomDoi());
    }

    ip.setJournal(this.__randomJournalName());

    p = this.m_rand.nextInt(10000) + 1;
    ip.setStartPage(String.valueOf(p));
    p += this.m_rand.nextInt(20);
    ip.setEndPage(String.valueOf(p));

    ip.setVolume(String.valueOf(this.m_rand.nextInt(200) + 1));
    ip.setNumber(String.valueOf(this.m_rand.nextInt(12) + 1));

    if (this.m_rand.nextBoolean()) {
      ip.setISSN(this.__randomISSN());
    }

    try (final BibDateBuilder db = ip.date()) {
      db.fromTime(this.__randomDate());
    }

    try (final BibOrganizationBuilder ob = ip.publisher()) {
      this.__randomOrganization(ob, true);
    }
  }

  /**
   * make a book chapter
   *
   * @param col
   *          the builder
   */
  private final void __randomInBook(final BibInCollectionBuilder col) {
    int p;

    col.setTitle(this.__randomTitle());

    try (final BibAuthorsBuilder abs = col.setAuthors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    try (final BibBookBuilder bp = col.book()) {
      this.__randomBook(bp, false, Integer.MAX_VALUE);
    }
    if (this.m_rand.nextBoolean()) {
      col.setURL(this.__randomURL());
    }
    if (this.m_rand.nextBoolean()) {
      col.setDOI(this.__randomDoi());
    }

    p = this.m_rand.nextInt(3000) + 1;
    col.setStartPage(String.valueOf(p));
    p += this.m_rand.nextInt(20);
    col.setEndPage(String.valueOf(p));

    if (this.m_rand.nextBoolean()) {
      col.setChapter(String.valueOf(1 + this.m_rand.nextInt(100)));
    }
  }

  /**
   * make proceedings report
   *
   * @param proc
   *          the builder
   */
  private final void __randomProc(final BibProceedingsBuilder proc) {
    long start, end;

    proc.setTitle(this.__randomProceedingsName());

    try (final BibAuthorsBuilder abs = proc.setEditors()) {
      this.__randomAuthors(abs, Integer.MAX_VALUE);
    }
    try (final BibOrganizationBuilder ob = proc.publisher()) {
      this.__randomOrganization(ob, true);
    }

    start = this.__randomDate();
    try (final BibDateBuilder db = proc.startDate()) {
      db.fromTime(start);
    }
    try (final BibDateBuilder db = proc.endDate()) {
      end = (start + (TimeUnit.MILLISECONDS.convert(
          this.m_rand.nextInt(14), TimeUnit.DAYS)));
      db.fromTime(end);
    }

    if (this.m_rand.nextBoolean()) {
      proc.setURL(this.__randomURL());
    }

    try (final BibOrganizationBuilder bo = proc.location()) {
      this.__randomOrganization(bo, false);
    }

    if (this.m_rand.nextBoolean()) {
      proc.setSeries(this.__randomSeries());
      proc.setVolume(String.valueOf(this.m_rand.nextInt(1000) + 1));
      if (this.m_rand.nextBoolean()) {
        proc.setISSN(this.__randomISSN());
      }
    }

    if (this.m_rand.nextBoolean()) {
      proc.setDOI(this.__randomDoi());
    }
    if (this.m_rand.nextBoolean()) {
      proc.setISBN(this.__randomISBN());
    }
  }

  /**
   * make thesis
   *
   * @param thesis
   *          the builder
   */
  private final void __randomThesis(final BibThesisBuilder thesis) {
    EThesisType[] e;

    this.__randomBook(thesis, true, 1);

    try (final BibOrganizationBuilder ob = thesis.school()) {
      this.__randomOrganization(ob, true);
    }

    e = EThesisType.values();
    thesis.setType(e[this.m_rand.nextInt(e.length)]);
  }

  /**
   * make book report
   *
   * @param book
   *          the builder
   * @param authors
   *          the authors
   * @param max
   *          the maximum number authors
   */
  private final void __randomBook(final BibBookBuilder book,
      final boolean authors, final int max) {

    book.setTitle(this.__randomBookName());

    if (authors) {
      try (final BibAuthorsBuilder abs = book.setAuthors()) {
        this.__randomAuthors(abs, max);
      }
    } else {
      try (final BibAuthorsBuilder abs = book.setEditors()) {
        this.__randomAuthors(abs, max);
      }
    }

    try (final BibOrganizationBuilder ob = book.publisher()) {
      this.__randomOrganization(ob, true);
    }

    try (final BibDateBuilder db = book.date()) {
      db.fromTime(this.__randomDate());
    }

    if (this.m_rand.nextBoolean()) {
      book.setURL(this.__randomURL());
    }

    if (this.m_rand.nextBoolean()) {
      book.setSeries(this.__randomSeries());
      book.setVolume(String.valueOf(this.m_rand.nextInt(1000) + 1));
      if (this.m_rand.nextBoolean()) {
        book.setISSN(this.__randomISSN());
      }
    }

    if (this.m_rand.nextBoolean()) {
      book.setDOI(this.__randomDoi());
    }
    if (this.m_rand.nextBoolean()) {
      book.setISBN(this.__randomISBN());
    }
  }

  /** the constructor */
  public RandomBibliography() {
    this(null);
  }

  /**
   * the constructor
   *
   * @param rand
   *          the randomizer
   */
  public RandomBibliography(final Random rand) {
    super();
    this.m_rand = ((rand != null) ? rand : new Random());
  }

  /**
   * The main routine, printing my bibliography
   *
   * @param args
   *          the command line arguments: ignored
   */
  public static final void main(final String[] args) {
    new RandomBibliography().run();
  }
}
