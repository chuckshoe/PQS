package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects.
 * It maintains references to all created Stopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  private static final Map<String, Stopwatch> stopwatches =
      new ConcurrentHashMap<String, Stopwatch>();
    
  /**
   * Creates and returns a new Stopwatch object
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *     already taken.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("ID can't be null or empty");
    }
    synchronized (stopwatches) {
      if (stopwatches.containsKey(id)) {
        throw new IllegalArgumentException("The given ID is already taken");
      }
      Stopwatch newStopwatch = new StopwatchImplementation(id);
      stopwatches.put(id, newStopwatch);
      return newStopwatch;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of al creates Stopwatch objects.  Returns an empty
   * list if no Stopwatches have been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopwatches) {
      return new ArrayList<Stopwatch>(stopwatches.values());
    }
  }
}
