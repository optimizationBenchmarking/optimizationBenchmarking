package examples.org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganizationBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedingsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;

/**
 * A class to test generating a paper for which the date range spans over
 * two years.
 */
public final class YearSpanningTestBibliography extends
    BibliographyExample {

  /**
   * create my bibliography
   *
   * @return the bibliography
   */
  @Override
  public final Bibliography createBibliography() {

    try (final BibliographyBuilder bb = new BibliographyBuilder()) {

      YearSpanningTestBibliography.__YEAR(bb);

      return bb.getResult();
    }
  }

  /**
   * make paper YEAR
   *
   * @param bb
   *          the builder
   */
  private static final void __YEAR(final BibliographyBuilder bb) {

    try (final BibInProceedingsBuilder bab = bb.inProceedings()) {
      bab.setTitle("Paper Title"); //$NON-NLS-1$
      try (final BibAuthorsBuilder abs = bab.setAuthors()) {
        try (final BibAuthorBuilder ab = abs.author()) {
          ab.setFamilyName("AuthorFamily"); //$NON-NLS-1$
          ab.setPersonalName("AuthorPersonal"); //$NON-NLS-1$
        }
      }

      try (final BibProceedingsBuilder pb = bab.proceedings()) {
        pb.setTitle("Proceedings"); //$NON-NLS-1$
        try (final BibAuthorsBuilder abs = pb.setEditors()) {
          try (final BibAuthorBuilder ab = abs.author()) {
            ab.setFamilyName("EditorFamily"); //$NON-NLS-1$
            ab.setPersonalName("EditorPersonal"); //$NON-NLS-1$
          }
        }
        try (final BibOrganizationBuilder bo = pb.publisher()) {
          bo.setName("Publisher"); //$NON-NLS-1$
          bo.setAddress("PublisherAddress"); //$NON-NLS-1$
        }
        try (final BibOrganizationBuilder bo = pb.location()) {
          bo.setName("Location"); //$NON-NLS-1$
          bo.setAddress("LocationAddress"); //$NON-NLS-1$
        }
        try (final BibDateBuilder d = pb.startDate()) {
          d.setYear(2007);
          d.setMonth(EBibMonth.DECEMBER);
          d.setDay(20);
        }
        try (final BibDateBuilder d = pb.endDate()) {
          d.setYear(2008);
          d.setMonth(EBibMonth.JANUARY);
          d.setDay(10);

        }
      }

      bab.setStartPage("100"); //$NON-NLS-1$
      bab.setEndPage("101"); //$NON-NLS-1$
    }
  }

  /** the constructor */
  public YearSpanningTestBibliography() {
    super();
  }

  /**
   * The main routine, printing the bibliography
   *
   * @param args
   *          the command line arguments: ignored
   */
  public static final void main(final String[] args) {
    new YearSpanningTestBibliography().run();
  }
}
