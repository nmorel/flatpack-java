package com.getperka.flatpack.gwt.codexes;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.getperka.flatpack.BaseHasUuid;
import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.ext.DeserializationContext;
import com.getperka.flatpack.gwt.ext.Property;

public class ImpliedPropertySetterTestGwt
    extends FlatPackTestCase
{
    private static class A
        extends BaseHasUuid
    {
        private B b;

        public B getB()
        {
            return b;
        }

        public void setB( B b )
        {
            this.b = b;
        }

    }

    private static class B
        extends BaseHasUuid
    {
        private List<A> aList;
        
        private Set<A> aSet;
        
        private A a;

        public List<A> getaList()
        {
            return aList;
        }

        public void setaList( List<A> aList )
        {
            this.aList = aList;
        }

        public Set<A> getaSet()
        {
            return aSet;
        }

        public void setaSet( Set<A> aSet )
        {
            this.aSet = aSet;
        }

        public A getA()
        {
            return a;
        }

        public void setA( A a )
        {
            this.a = a;
        }
    }

    private static class CodexA
        extends EntityCodex<A>
    {

        @Override
        protected void initProperties( List<Property<A, ?>> properties )
        {
        }

        @Override
        public String getName()
        {
            return "A";
        }

        @Override
        protected A createInstance()
        {
            return new A();
        }

    }

    private static class CodexB
        extends EntityCodex<B>
    {

        @Override
        protected void initProperties( List<Property<B, ?>> properties )
        {
        }

        @Override
        public String getName()
        {
            return "B";
        }

        @Override
        protected B createInstance()
        {
            return new B();
        }

    }

    private static class PropertyBInA
        extends Property<A, B>
    {

        public PropertyBInA()
        {
            super( "b", new CodexB() );
        }

        @Override
        public B getValue( A object )
        {
            return object.getB();
        }

        @Override
        public void setValue( A object, B value )
        {
            object.setB( value );
        }

    }

    private static class PropertyAListInB
        extends Property<B, List<A>>
    {

        public PropertyAListInB()
        {
            super( "aList", new ListCodex<A>( new CodexA() ) );
        }

        @Override
        public List<A> getValue( B object )
        {
            return object.getaList();
        }

        @Override
        public void setValue( B object, List<A> value )
        {
            object.setaList( value );
        }
    }

    private static class PropertyASetInB
        extends Property<B, Set<A>>
    {

        public PropertyASetInB()
        {
            super( "aSet", new SetCodex<A>( new CodexA() ) );
        }

        @Override
        public Set<A> getValue( B object )
        {
            return object.getaSet();
        }

        @Override
        public void setValue( B object, Set<A> value )
        {
            object.setaSet( value );
        }
    }

    private static class PropertyAInB
        extends Property<B, A>
    {

        public PropertyAInB()
        {
            super( "a", new CodexA() );
        }

        @Override
        public A getValue( B object )
        {
            return object.getA();
        }

        @Override
        public void setValue( B object, A value )
        {
            object.setA( value );
        }
    }

    public void test()
        throws Exception
    {
        A a = new A();
        B b = new B();

        PropertyBInA propBInA = new PropertyBInA();
        PropertyAListInB propAListInB = new PropertyAListInB();
        PropertyASetInB propASetInB = new PropertyASetInB();
        PropertyAInB propAInB = new PropertyAInB();

        propBInA.setImpliedProperty( propAListInB );
        propAListInB.setImpliedProperty( propBInA );
        propASetInB.setImpliedProperty( propBInA );
        propAInB.setImpliedProperty( propBInA );

        // test that the setter can create a list
        ImpliedPropertySetter setter = new ImpliedPropertySetter( new DeserializationContext(), propAListInB, b, a );
        setter.call();
        assertEquals( 1, b.getaList().size() );
        assertSame( a, b.getaList().get( 0 ) );

        // test that the setter doesn't add twice the same object and doesn't recreate the list
        A a2 = new A();
        b.getaList().add( a2 );
        setter.call();
        assertEquals( 2, b.getaList().size() );
        assertSame( a, b.getaList().get( 0 ) );
        assertSame( a2, b.getaList().get( 1 ) );
        
        // test that the setter can set the implied value to each element of a list
        setter = new ImpliedPropertySetter( new DeserializationContext(), propBInA, Arrays.asList( a, a2 ), b );
        setter.call();
        assertSame(b, a.getB());
        assertSame(b, a2.getB());
        
        // test with a Set
        setter =  new ImpliedPropertySetter( new DeserializationContext(), propASetInB, b, a );
        setter.call();
        assertEquals( 1, b.getaSet().size() );
        assertSame( a, b.getaSet().iterator().next());
        
        // test one to one
        setter =  new ImpliedPropertySetter( new DeserializationContext(), propAInB, b, a );
        setter.call();
        assertSame( a, b.getA());
    }
}
