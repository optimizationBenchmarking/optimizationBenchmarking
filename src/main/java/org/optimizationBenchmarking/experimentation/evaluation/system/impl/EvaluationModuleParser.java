package org.optimizationBenchmarking.experimentation.evaluation.system.impl;

import java.util.LinkedHashSet;

import org.optimizationBenchmarking.experimentation.evaluation.system.impl.all.ExperimentSetModules;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.appendix.AppendixModules;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.description.DescriptionModules;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.single.ExperimentModules;
import org.optimizationBenchmarking.experimentation.evaluation.system.spec.IEvaluationModule;
import org.optimizationBenchmarking.utils.parsers.InstanceParser;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** The parser for evaluation modules */
public class EvaluationModuleParser extends
    InstanceParser<IEvaluationModule> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  EvaluationModuleParser() {
    super(IEvaluationModule.class, EvaluationModuleParser.__prefixes());
  }

  /**
   * get the prefixes
   * 
   * @return the path prefixes
   */
  private static final String[] __prefixes() {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();

    ReflectionUtils.addPackageOfClassToPrefixList(
        EvaluationModuleParser.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(ExperimentModules.class,
        paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        ExperimentSetModules.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(
        DescriptionModules.class, paths);
    ReflectionUtils.addPackageOfClassToPrefixList(AppendixModules.class,
        paths);

    return paths.toArray(new String[paths.size()]);
  }

  /**
   * Get the singleton instance of this parser
   * 
   * @return the document driver parser
   */
  public static final EvaluationModuleParser getInstance() {
    return __EvaluationModuleParserLoader.INSTANCE;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} for serialization,
   * i.e., when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   * 
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object writeReplace() {
    return EvaluationModuleParser.getInstance();
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #getInstance()} after
   * serialization, i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   * 
   * @return the replacement instance (always {@link #getInstance()})
   */
  private final Object readResolve() {
    return EvaluationModuleParser.getInstance();
  }

  /** the instance loader */
  private static final class __EvaluationModuleParserLoader {
    /** the instance */
    static final EvaluationModuleParser INSTANCE = new EvaluationModuleParser();
  }
}