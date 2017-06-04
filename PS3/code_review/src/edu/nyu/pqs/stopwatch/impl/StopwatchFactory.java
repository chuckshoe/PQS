package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects.
 * It maintains references to all created Stopwatch objects and provides a
 * convenient method for getting a list of those objects.
 * @author Sydney Schweber
 */
public class StopwatchFactory {

  private static List<Stopwatch> stopwatches = 
          Collections.synchronizedList(new ArrayList<Stopwatch>());

  /**
   * Creates and returns a new Stopwatch object
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *     already taken.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null || id.equals("")) {
      throw new IllegalArgumentException("Cannot have empty or null ID");
    }

    synchronized (stopwatches) {
      if (stopwatches.contains(id)) {
        throw new IllegalArgumentException("ID: " + id + " is already taken");
      }
      
      Timer timer = new Timer(id);
      stopwatches.add(timer);

      return timer;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of all created Stopwatch objects.  Returns an empty
   * list if no Stopwatches have been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopwatches) {
      return stopwatches;
    }
  }
}
