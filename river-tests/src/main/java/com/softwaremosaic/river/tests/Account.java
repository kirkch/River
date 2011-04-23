package com.softwaremosaic.river.tests;

import com.mosaic.gis.LatLon;
import com.softwaremosaic.river.annotations.RiverIndex;
import com.softwaremosaic.river.annotations.RiverIndexes;
import com.softwaremosaic.river.meta.IndexType;

import java.io.Serializable;

/**
 *
 */
@RiverIndexes({
    @RiverIndex( name="nameTypeUniqueIndex", keys="name,type", type=IndexType.UNIQUE_INDEX ),
    @RiverIndex( name="locationGISIndex", keys="location", type=IndexType.GIS_INDEX )
})
public class Account implements Serializable {
    private String id;
    private String name;
    private String type;

    private LatLon location;

    public Account() {}
    public Account( String name, String type  ) {
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public LatLon getLocation() {
        return location;
    }

    public void setLocation( LatLon location ) {
        this.location = location;
    }
}
