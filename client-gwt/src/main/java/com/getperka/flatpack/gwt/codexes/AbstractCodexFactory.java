package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.getperka.flatpack.HasUuid;
import com.getperka.flatpack.ext.TypeHint;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Base codex factory to create all the codex in one place. It allows to define a custom factory via differed binding.
 *
 * @author Nicolas Morel
 */
public class AbstractCodexFactory
{

    /**
     * @return a {@link BigDecimal} codex
     */
    public Codex<BigDecimal> bigDecimalCodex()
    {
        return new BigDecimalCodex();
    }

    /**
     * @return a {@link BigInteger} codex
     */
    public Codex<BigInteger> bigIntegerCodex()
    {
        return new BigIntegerCodex();
    }

    /**
     * @return a {@link Boolean} codex
     */
    public Codex<Boolean> booleanCodex()
    {
        return new BooleanCodex();
    }

    /**
     * @return a {@link Byte} codex
     */
    public Codex<Byte> byteCodex()
    {
        return new ByteCodex();
    }

    /**
     * @return a {@link Character} codex
     */
    public Codex<Character> characterCodex()
    {
        return new CharacterCodex();
    }

    /**
     * @return a {@link java.util.Date} codex
     */
    public Codex<java.util.Date> dateCodex()
    {
        return new DateCodex();
    }

    /**
     * @return a {@link Double} codex
     */
    public Codex<Double> doubleCodex()
    {
        return new DoubleCodex();
    }

    /**
     * @param keyCodex key's codex
     * @param valueCodex value's codex
     * @return an entity {@link Map} codex
     */
    public <K extends HasUuid, V> Codex<Map<K, V>> entityMapCodex( EntityCodex<K> keyCodex, Codex<V> valueCodex )
    {
        return new EntityMapCodex<K, V>( keyCodex, valueCodex );
    }

    /**
     * @param clazz {@link Enum} class
     * @return an {@link Enum} codex
     */
    public <E extends Enum<E>> Codex<E> enumCodex( Class<E> clazz )
    {
        return new EnumCodex<E>( clazz );
    }

    /**
     * @return a {@link Float} codex
     */
    public Codex<Float> floatCodex()
    {
        return new FloatCodex();
    }

    /**
     * @return an {@link Integer} codex
     */
    public Codex<Integer> integerCodex()
    {
        return new IntegerCodex();
    }

    /**
     * @return a {@link JavaScriptObject} codex
     */
    public Codex<JavaScriptObject> jsonCodex()
    {
        return new JsonElementCodex();
    }

    /**
     * @param valueCodex value's codex
     * @return a {@link List} codex
     */
    public <V> Codex<List<V>> listCodex( Codex<V> valueCodex )
    {
        return new ListCodex<V>( valueCodex );
    }

    /**
     * @return a {@link Long} codex
     */
    public Codex<Long> longCodex()
    {
        return new LongCodex();
    }

    /**
     * @param valueCodex value's codex
     * @return a {@link Set} codex
     */
    public <V> Codex<Set<V>> setCodex( Codex<V> valueCodex )
    {
        return new SetCodex<V>( valueCodex );
    }

    /**
     * @return a {@link Short} codex
     */
    public Codex<Short> shortCodex()
    {
        return new ShortCodex();
    }

    /**
     * @return a {@link java.sql.Date} codex
     */
    public Codex<java.sql.Date> sqlDateCodex()
    {
        return new SqlDateCodex();
    }

    /**
     * @return a {@link Time} codex
     */
    public Codex<Time> sqlTimeCodex()
    {
        return new SqlTimeCodex();
    }

    /**
     * @return a {@link Timestamp} codex
     */
    public Codex<Timestamp> sqlTimestampCodex()
    {
        return new SqlTimestampCodex();
    }

    /**
     * @return a {@link String} codex
     */
    public Codex<String> stringCodex()
    {
        return new StringCodex();
    }

    /**
     * @param valueCodex value's codex
     * @return a {@link Map} codex
     */
    public <V> Codex<Map<String, V>> stringMapCodex( Codex<V> valueCodex )
    {
        return new StringMapCodex<V>( valueCodex );
    }

    /**
     * @return a {@link TypeHint} codex
     */
    public Codex<TypeHint> typeHintCodex()
    {
        return new TypeHintCodex();
    }

    /**
     * @return an {@link UUID} codex
     */
    public Codex<UUID> uuidCodex()
    {
        return new UUIDCodex();
    }

    /**
     * @return a {@link Void} codex
     */
    public Codex<Void> voidCodex()
    {
        return new VoidCodex();
    }

}
