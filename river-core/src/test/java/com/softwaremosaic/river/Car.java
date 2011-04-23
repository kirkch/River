package com.softwaremosaic.river;

import java.io.Serializable;

/**
 *
 */
public class Car implements Serializable {
    private static final long serialVersionUID = 1301555580062L;

    private String id;
    private Engine engine;
    private int    numberOfWheels;

    private transient String driverName;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine( Engine engine ) {
        this.engine = engine;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels( int numberOfWheels ) {
        this.numberOfWheels = numberOfWheels;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName( String driverName ) {
        this.driverName = driverName;
    }
}
