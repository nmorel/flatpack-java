package com.getperka.flatpack.demo.gwt.persistence;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.PersistenceMapper;
import com.getperka.flatpack.demo.gwt.model.BaseEntity;

public class DemoPersistenceMapper
    implements PersistenceMapper
{

    @Override
    public boolean canPersist( Class<? extends HasUuid> entityType )
    {
        return BaseEntity.class.isAssignableFrom( entityType );
    }

    @Override
    public boolean isPersisted( HasUuid entity )
    {
        if ( canPersist( entity.getClass() ) )
        {
            // can't use the id since it is auto-generated and never null
            return null != ( (BaseEntity) entity ).getCreationDate();
        }
        else
        {
            return false;
        }
    }

}
