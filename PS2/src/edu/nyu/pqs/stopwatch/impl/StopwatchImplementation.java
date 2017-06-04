package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * This class is a thread-safe implementation of<code>Stopwatch</code> interface.
 * It supports the following operations:
 * <ul>
 * <li>Start</li>
 * <li>Stop</li>
 * <li>Record Lap</li>
 * <li>Reset</li>
 * <li>Get the recorded lap times</li>
 * </ul>
 * @author Chakshu Sardana
 */
public class StopwatchImplementation implements Stopwatch {
  private enum State {RUNNING, PAUSED, RESET};
  private final String id;
  private long startTime;
  private int currentLap;
  private long currentLapTotalTime;
  State currentState;
  private final Map<Integer, Long> laps;
  private Object lock;
  
  /**
   * Creates a new <code>StopwatchImplementation</code> object.
   * 
   * @param id This is the required parameter that is unique for every object of this class.
   */
  public StopwatchImplementation (String id) {
    this.id = id;
    currentLap = 1;
    startTime = 0;
    currentState = State.RESET;
    currentLapTotalTime = 0;
    laps = new ConcurrentHashMap<Integer, Long>();
    lock = new Object();
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void start() {
    synchronized (lock) {
      if (currentState.equals(State.PAUSED)) {
        laps.remove(currentLap);
      } else if (currentState.equals(State.RUNNING)) {
        throw new IllegalStateException ("Stopwatch is already running");  
      }
      startTime = System.currentTimeMillis();
      currentState = State.RUNNING;
    }
  }

  @Override
  public void lap() {
    if (currentState.equals(State.RUNNING)) {
      synchronized (lock) {
        laps.put(currentLap, currentLapTotalTime + System.currentTimeMillis() - startTime);
        currentLap ++;
        startTime = System.currentTimeMillis();
        currentLapTotalTime = 0;
      }
    } else {
      throw new IllegalStateException ("Stopwatch is not running");
    }
  }

  @Override
  public void stop() {
    if (currentState.equals(State.RUNNING)) {
      synchronized (lock) {
        currentState = State.PAUSED;
        currentLapTotalTime += System.currentTimeMillis() - startTime;
        laps.put(currentLap, currentLapTotalTime);
      }
    } else {
      throw new IllegalStateException ("Stopwatch is not running");
    }
  }

  @Override
  public void reset() {
    synchronized (lock) {
      currentLap = 1;
      startTime = 0;
      currentState = State.RESET;
      currentLapTotalTime = 0;
      laps.clear();
    }
  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return new ArrayList<>(laps.values());
    }
  }

  @Override
  public String toString() {
    synchronized (lock) {
      StringBuilder result = new StringBuilder(
          "Stopwatch ID: " + id + "\t State:" + currentState + "\n");
      if (laps.size() > 0) {
        result.append("Current laps in this stopwatch are: \n");
        for (int lap : laps.keySet()) {
          result.append("Lap " + lap + ": " + laps.get(lap) + " milliSeconds\n");
        }
      }
      return result.toString();
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + id.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (!(obj instanceof StopwatchImplementation)) {
      return false;
    }
    StopwatchImplementation other = (StopwatchImplementation) obj;
    if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }
}
