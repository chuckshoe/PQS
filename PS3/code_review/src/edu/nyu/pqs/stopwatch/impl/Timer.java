package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * A Timer class to implement the Stopwatch interface
 * @author Sydney Schweber
 *
 */
public class Timer implements Stopwatch {
  
  private final String id;
  private List<Long> lapTimes;
  private boolean running; 
  private long startTime;
  
  private Object lock;
  
  /**
   * Timer constructor that takes ID as a parameter
   * ID cannot be null
   * @param id - the id to be used
   */
  public Timer(String id) {
    if (id.equals("") || id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    this.id = id;
    lapTimes = Collections.synchronizedList(new ArrayList<Long>());
    running = false;
    startTime = 0;
    lock = new Object();
  }

  @Override
  public boolean equals(Object obj) {
    Timer timer = (Timer) obj;
    if (timer.getId() == this.id) {
      return true;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    long newID = Long.parseLong(id.substring(2));
    int result = (int) (newID ^ (newID >>> 32));
    return result;
  }
  
  @Override
  public String getId() {
    return id;
  }

  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning()) {
        throw new IllegalStateException("Stopwatch is already running");
      } else {
        long current = System.currentTimeMillis();
        setStartTime(current);
        setRunning(true);
      }
    }
  }

  @Override
  public void lap() {
    synchronized (lock) {
      if (isRunning()) {
        long current = System.currentTimeMillis();
        lapTimes.add(current - getStartTime());
        setStartTime(current);
      } else {
        throw new IllegalStateException("Stopwatch isn't running currently");
      }
    }
  }

  @Override
  public void stop() {
    synchronized (lock) {
      if (isRunning()) {
        long current = System.currentTimeMillis();
        lapTimes.add(current - getStartTime());
        setRunning(false);
      } else {
        throw new IllegalStateException("Stopwatch isn't running currently");
      }
    }
  }

  @Override
  public void reset() {
    synchronized (lock) {
      if (isRunning()) {
        setStartTime(0);
        setRunning(false);
      }
      lapTimes.clear();
    }
  }

  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return lapTimes;
    }
  }
  
  /**
   * A method to get the start time of the current Timer object
   * @return startTime
   */
  public long getStartTime() { 
    synchronized (lock) {
      return startTime;
    }
  }
  
  /**
   * A method to set the start time as not to modify the fields directly
   * @param time - the time to set startTime to
   */
  private void setStartTime(long time) {
    synchronized (lock) {
      startTime = time;
    }
  }
  
  /**
   * To be called to check if a timer is running when in start, lap, reset or stop methods
   * @return running - a field that is true if the timer is running, false otherwise
   */
  private boolean isRunning() {
    return running;
  }
  
  /**
   * Set the boolean field for if a timer is running
   * @param running - the boolean to set field to
   */
  private void setRunning(boolean running) {
    synchronized (lock) {
      this.running = running;
    }
  }
  
  @Override
  public String toString() {
    return "Timer ID: " + this.getId() + ", Timer start time: " + this.getStartTime();
  }

}
