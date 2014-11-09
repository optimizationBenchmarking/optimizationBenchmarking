/**
 * A specification of the
 * {@link org.optimizationBenchmarking.utils.document document API}. This
 * API is a versatile way to produce report- or article-like documents in
 * various formats, with figures, charts, lists, tables, etc. The API is
 * also parallel: Each implementation of it <em>must</em> support parallel
 * invocations of
 * {@link org.optimizationBenchmarking.utils.document.spec.ISectionContainer#section(ILabel)}
 * , i.e., the parallel creation of document sections. The entry point of
 * an implementation of the document API is the corresponding
 * {@link org.optimizationBenchmarking.utils.document.spec.IDocumentDriver
 * document driver} instance.
 */
package org.optimizationBenchmarking.utils.document.spec;