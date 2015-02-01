package org.optimizationBenchmarking.experimentation.evaluation.system.spec;

/**
 * This enum allows us to represent the relationship of modules.
 */
public enum EModuleRelationship {

  /**
   * The modules are unrelated: It does not matter in which order they are
   * executed. They are also not nested.
   */
  NONE,

  /** The module should be executed before the other module. */
  EXECUTE_BEFORE,

  /** The module should be executed after the other module. */
  EXECUTE_AFTER,

  /**
   * The module should be executed as child module inside the other module,
   * if possible.
   */
  CONTAINED_IN,

  /**
   * The module should execute the specified module as child module, if
   * possible.
   */
  CONTAINS;
}
