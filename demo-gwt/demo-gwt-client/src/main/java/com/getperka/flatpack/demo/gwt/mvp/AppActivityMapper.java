package com.getperka.flatpack.demo.gwt.mvp;

import com.getperka.flatpack.demo.gwt.screens.error.ErrorPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.ConsultProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.CreateProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.edit.EditProductPlace;
import com.getperka.flatpack.demo.gwt.screens.product.list.ListProductPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

/**
 * @author Nicolas Morel
 */
public class AppActivityMapper
    implements ActivityMapper
{
    private static class ActivityMapperVisitor
        implements BasePlace.Visitor
    {

        private Activity activity;

        public Activity getActivity()
        {
            return activity;
        }

        @Override
        public void visitPlace( ErrorPlace place )
        {
            this.activity = AppFactory.get().getErrorActivity().withPlace( place );
        }

        @Override
        public void visitPlace( ListProductPlace place )
        {
            this.activity = AppFactory.get().getListProductActivity().withPlace( place );
        }

        @Override
        public void visitPlace( ConsultProductPlace place )
        {
            this.activity = AppFactory.get().getConsultProductActivity().withPlace( place );
        }

        @Override
        public void visitPlace( CreateProductPlace place )
        {
            this.activity = AppFactory.get().getCreateProductActivity().withPlace( place );
        }

        @Override
        public void visitPlace( EditProductPlace place )
        {
            this.activity = AppFactory.get().getEditProductActivity().withPlace( place );
        }

    }

    @Override
    public Activity getActivity( Place place )
    {
        assert place instanceof BasePlace : "Only BasePlace are managed";
        ActivityMapperVisitor visitor = new ActivityMapperVisitor();
        ( (BasePlace) place ).accept( visitor );
        return visitor.getActivity();
    }
}
