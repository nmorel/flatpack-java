package com.getperka.flatpack.demo.gwt.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.getperka.flatpack.HasUuid;
import com.google.common.base.Objects;

@MappedSuperclass
public abstract class BaseEntity
    implements HasUuid
{
    private UUID uuid;
    private Date creationDate;
    private Date modificationDate;

    @Id
    @Column( name = "id", unique = true, nullable = false )
    public UUID getUuid()
    {
        if ( null == uuid )
        {
            uuid = UUID.randomUUID();
        }
        return uuid;
    }

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "creationDate", nullable = false, updatable = false )
    public Date getCreationDate()
    {
        return creationDate;
    }

    @Version
    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "modificationDate", nullable = false )
    public Date getModificationDate()
    {
        return modificationDate;
    }

    public void setUuid( UUID uuid )
    {
        this.uuid = uuid;
    }

    public void setCreationDate( Date creationDate )
    {
        this.creationDate = creationDate;
    }

    public void setModificationDate( Date modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    @PrePersist
    public void prePersist()
    {
        creationDate = new Date();
    }

    /**
     * Equal to any {@link HasUuid} type with the same UUID and where one type is assignable to another.
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( !( obj instanceof HasUuid ) )
        {
            return false;
        }
        return getUuid().equals( ( (HasUuid) obj ).getUuid() )
            && ( getClass().isAssignableFrom( obj.getClass() ) || obj.getClass().isAssignableFrom( getClass() ) );
    }

    /**
     * Calls {@code getUuid().hashCode()}.
     */
    @Override
    public int hashCode()
    {
        return getUuid().hashCode();
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper( this ).add( "uuid", getUuid() ).add( "creationDate", getCreationDate() )
            .add( "modificationDate", getModificationDate() ).toString();
    }

}