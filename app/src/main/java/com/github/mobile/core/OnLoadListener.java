
package com.github.mobile.core;

/**
 * Load listener callback
 *
 * @param <V>
 */
public interface OnLoadListener<V> {

  /**
   * Loaded callback
   *
   * @param data
   */
  void loaded(V data);
}
