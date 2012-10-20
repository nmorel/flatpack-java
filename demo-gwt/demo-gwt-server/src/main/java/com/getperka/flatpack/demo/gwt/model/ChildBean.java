package com.getperka.flatpack.demo.gwt.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity( name = "Child" )
public class ChildBean
    extends BaseEntity
{
    private String child;

    private List<Product> associatedProducts;

    @Column( name = "child" )
    @NotNull
    @Size( min = 1 )
    public String getChild()
    {
        return child;
    }

    @OneToMany( mappedBy = "child" )
    public List<Product> getAssociatedProducts()
    {
        return associatedProducts;
    }

    public void setChild( String child )
    {
        this.child = child;
    }

    public void setAssociatedProducts( List<Product> associatedProducts )
    {
        this.associatedProducts = associatedProducts;
    }
}
