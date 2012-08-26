/*
 * #%L
 * FlatPack Demonstration Server
 * %%
 * Copyright (C) 2012 Perka Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.getperka.flatpack.gwt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.getperka.flatpack.BaseHasUuid;

/**
 * An entity with multiple properties type to test the codex
 */
public class MultiplePropertiesBean
    extends BaseHasUuid
{
    private String string;
    private byte bytePrimitive;
    private Byte byteBoxed;
    private short shortPrimitive;
    private Short shortBoxed;
    private int intPrimitive;
    private Integer intBoxed;
    private long longPrimitive;
    private Long longBoxed;
    private double doublePrimitive;
    private Double doubleBoxed;
    private float floatPrimitive;
    private Float floatBoxed;
    private boolean booleanPrimitive;
    private Boolean booleanBoxed;
    private char charPrimitive;
    private Character charBoxed;
    private BigInteger bigInteger;
    private BigDecimal bigDecimal;
    private TestEnum enumProperty;
    private UUID uuidProperty;
    private ChildBean singleEntity;
    private List<ChildBean> listEntity;
    private Set<ChildBean> setEntity;
    private Map<String, ChildBean> mapStringToEntity;
    private Map<ChildBean, String> mapEntityToString;
    private ChildBean[] arrayEntity;
    private Date dateJdk;
    private Date dateJoda;

    public String getString()
    {
        return string;
    }

    public void setString( String string )
    {
        this.string = string;
    }

    public byte getBytePrimitive()
    {
        return bytePrimitive;
    }

    public void setBytePrimitive( byte bytePrimitive )
    {
        this.bytePrimitive = bytePrimitive;
    }

    public Byte getByteBoxed()
    {
        return byteBoxed;
    }

    public void setByteBoxed( Byte byteBoxed )
    {
        this.byteBoxed = byteBoxed;
    }

    public short getShortPrimitive()
    {
        return shortPrimitive;
    }

    public void setShortPrimitive( short shortPrimitive )
    {
        this.shortPrimitive = shortPrimitive;
    }

    public Short getShortBoxed()
    {
        return shortBoxed;
    }

    public void setShortBoxed( Short shortBoxed )
    {
        this.shortBoxed = shortBoxed;
    }

    public int getIntPrimitive()
    {
        return intPrimitive;
    }

    public void setIntPrimitive( int intPrimitive )
    {
        this.intPrimitive = intPrimitive;
    }

    public Integer getIntBoxed()
    {
        return intBoxed;
    }

    public void setIntBoxed( Integer intBoxed )
    {
        this.intBoxed = intBoxed;
    }

    public long getLongPrimitive()
    {
        return longPrimitive;
    }

    public void setLongPrimitive( long longPrimitive )
    {
        this.longPrimitive = longPrimitive;
    }

    public Long getLongBoxed()
    {
        return longBoxed;
    }

    public void setLongBoxed( Long longBoxed )
    {
        this.longBoxed = longBoxed;
    }

    public double getDoublePrimitive()
    {
        return doublePrimitive;
    }

    public void setDoublePrimitive( double doublePrimitive )
    {
        this.doublePrimitive = doublePrimitive;
    }

    public Double getDoubleBoxed()
    {
        return doubleBoxed;
    }

    public void setDoubleBoxed( Double doubleBoxed )
    {
        this.doubleBoxed = doubleBoxed;
    }

    public float getFloatPrimitive()
    {
        return floatPrimitive;
    }

    public void setFloatPrimitive( float floatPrimitive )
    {
        this.floatPrimitive = floatPrimitive;
    }

    public Float getFloatBoxed()
    {
        return floatBoxed;
    }

    public void setFloatBoxed( Float floatBoxed )
    {
        this.floatBoxed = floatBoxed;
    }

    public boolean isBooleanPrimitive()
    {
        return booleanPrimitive;
    }

    public void setBooleanPrimitive( boolean booleanPrimitive )
    {
        this.booleanPrimitive = booleanPrimitive;
    }

    public Boolean getBooleanBoxed()
    {
        return booleanBoxed;
    }

    public void setBooleanBoxed( Boolean booleanBoxed )
    {
        this.booleanBoxed = booleanBoxed;
    }

    public char getCharPrimitive()
    {
        return charPrimitive;
    }

    public void setCharPrimitive( char charPrimitive )
    {
        this.charPrimitive = charPrimitive;
    }

    public Character getCharBoxed()
    {
        return charBoxed;
    }

    public void setCharBoxed( Character charBoxed )
    {
        this.charBoxed = charBoxed;
    }

    public BigInteger getBigInteger()
    {
        return bigInteger;
    }

    public void setBigInteger( BigInteger bigInteger )
    {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal()
    {
        return bigDecimal;
    }

    public void setBigDecimal( BigDecimal bigDecimal )
    {
        this.bigDecimal = bigDecimal;
    }

    public TestEnum getEnumProperty()
    {
        return enumProperty;
    }

    public void setEnumProperty( TestEnum enumProperty )
    {
        this.enumProperty = enumProperty;
    }

    public UUID getUuidProperty()
    {
        return uuidProperty;
    }

    public void setUuidProperty( UUID uuidProperty )
    {
        this.uuidProperty = uuidProperty;
    }

    public ChildBean getSingleEntity()
    {
        return singleEntity;
    }

    public void setSingleEntity( ChildBean singleEntity )
    {
        this.singleEntity = singleEntity;
    }

    public List<ChildBean> getListEntity()
    {
        return listEntity;
    }

    public void setListEntity( List<ChildBean> listEntity )
    {
        this.listEntity = listEntity;
    }

    public Set<ChildBean> getSetEntity()
    {
        return setEntity;
    }

    public void setSetEntity( Set<ChildBean> setEntity )
    {
        this.setEntity = setEntity;
    }

    public Map<String, ChildBean> getMapStringToEntity()
    {
        return mapStringToEntity;
    }

    public void setMapStringToEntity( Map<String, ChildBean> mapStringToEntity )
    {
        this.mapStringToEntity = mapStringToEntity;
    }

    public Map<ChildBean, String> getMapEntityToString()
    {
        return mapEntityToString;
    }

    public void setMapEntityToString( Map<ChildBean, String> mapEntityToString )
    {
        this.mapEntityToString = mapEntityToString;
    }

    public ChildBean[] getArrayEntity()
    {
        return arrayEntity;
    }

    public void setArrayEntity( ChildBean[] arrayEntity )
    {
        this.arrayEntity = arrayEntity;
    }

    public Date getDateJdk()
    {
        return dateJdk;
    }

    public void setDateJdk( Date dateJdk )
    {
        this.dateJdk = dateJdk;
    }

    public Date getDateJoda()
    {
        return dateJoda;
    }

    public void setDateJoda( Date dateJoda )
    {
        this.dateJoda = dateJoda;
    }

}
