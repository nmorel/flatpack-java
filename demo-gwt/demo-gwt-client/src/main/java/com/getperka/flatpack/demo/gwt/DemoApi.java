package com.getperka.flatpack.demo.gwt;

import com.getperka.flatpack.demo.gwt.gen.EntityCodexFactory;
import com.getperka.flatpack.demo.gwt.gen.GenTypeContext;
import com.getperka.flatpack.demo.gwt.gen.Product;
import com.getperka.flatpack.gwt.client.FlatPackRequest;
import com.getperka.flatpack.gwt.client.impl.ApiBase;
import com.getperka.flatpack.gwt.client.impl.FlatPackRequestBase;
import com.getperka.flatpack.gwt.codexes.ListCodex;
import com.getperka.flatpack.gwt.codexes.VoidCodex;
import com.getperka.flatpack.gwt.ext.TypeContext;
import com.google.gwt.http.client.RequestBuilder;

public class DemoApi
    extends ApiBase
{

    public DemoApi()
    {
        super( new GenTypeContext() );
    }

    public DemoApi( TypeContext typeContext )
    {
        super( typeContext );
    }

    public interface ProductsGet
        extends FlatPackRequest<ProductsGet, Void, java.util.List<Product>>
    {
    }

    private class ProductsGetImpl
        extends FlatPackRequestBase<ProductsGet, Void, java.util.List<Product>>
        implements ProductsGet
    {
        public ProductsGetImpl( Object... args )
        {
            super( DemoApi.this, RequestBuilder.GET, "/products", false, new VoidCodex(), new ListCodex<Product>(
                EntityCodexFactory.get().getProductCodex() ) );
        }
    }

    /**
     * Return the list of products.
     */
    public ProductsGet productsGet()
    {
        ProductsGetImpl toReturn = new ProductsGetImpl();
        return toReturn;
    }

}
