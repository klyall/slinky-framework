package org.slinkyframework.common.logging.test.example;

import org.slinkyframework.common.logging.Loggable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ExampleClass {

    public void hasNoParameters() {
    }

    public void hasNoLoggableParameter(String title) {
    }

    public void hasAnonymousLoggableParameters(@Loggable String stringParam, @Loggable int intParam) {
    }

    public String hasAnonymousLoggableParameter(@Loggable String param1, @Loggable String param2) {
        return param1;
    }

    public Short hasAnonymousLoggableParameter(@Loggable Short param1, @Loggable Short param2) {
        return param1;
    }

    public Integer hasAnonymousLoggableParameter(@Loggable Integer param1, @Loggable Integer param2) {
        return param1;
    }

    public Long hasAnonymousLoggableParameter(@Loggable Long param1, @Loggable Long param2) {
        return param1;
    }

    public Float hasAnonymousLoggableParameter(@Loggable Float param1, @Loggable Float param2) {
        return param1;
    }

    public Double hasAnonymousLoggableParameter(@Loggable Double param1, @Loggable Double param2) {
        return param1;
    }

    public Boolean hasAnonymousLoggableParameter(@Loggable Boolean param1, @Loggable Boolean param2) {
        return param1;
    }

    public ExampleEnum hasAnonymousLoggableParameter(@Loggable ExampleEnum param1, @Loggable ExampleEnum param2) {
        return param1;
    }

    public Byte hasAnonymousLoggableParameter(@Loggable Byte param1, @Loggable Byte param2) {
        return param1;
    }

    public Character hasAnonymousLoggableParameter(@Loggable Character param1, @Loggable Character param2) {
        return param1;
    }

    public Date hasAnonymousLoggableParameter(@Loggable Date param1, @Loggable Date param2) {
        return param1;
    }

    public LocalDate hasAnonymousLoggableParameter(@Loggable LocalDate param1, @Loggable LocalDate param2) {
        return param1;
    }

    public LocalDateTime hasAnonymousLoggableParameter(@Loggable LocalDateTime param1, @Loggable LocalDateTime param2) {
        return param1;
    }

    public LocalTime hasAnonymousLoggableParameter(@Loggable LocalTime param1, @Loggable LocalTime param2) {
        return param1;
    }

    public void hasNamedLoggableParameters(@Loggable("stringParam") String stringParam, @Loggable("intParam") int intParam, @Loggable("booleanParam") boolean booleanParam) {
    }

    public void hasAnonymousLoggableClassParameters(@Loggable ExamplePerson person) {
    }

    public void hasNamedLoggableClassParameters(@Loggable("person") ExamplePerson person) {
    }

    public void hasNamedLoggableCollectionParameters(@Loggable("items") List<String> items) {
    }

    public void hasAnonymouLoggableClassWithAllTypes(@Loggable() AllTypes allTypes) {
    }

    public void hasAnonymouLoggableClassWithoutAccessors(@Loggable() ClassWithoutAccessor classWithoutAccessor) {
    }

    public void hasAnonymousOptionalLoggableClassParameters(@Loggable Optional<ExamplePerson> person) {
    }

    public void hasNamedOptionalLoggableClassParameters(@Loggable("person") Optional<ExamplePerson> person) {
    }

    public @Loggable String hasAnonymousLogableResponse(String string) {
        return string;
    }

    public @Loggable("Ping") String hasNamedLoggableResponse(String string) {
        return string;
    }

    public @Loggable String hasNullLoggableResponse() {
        return null;
    }

    public @Loggable("Name") ExampleName hasNamedClassLoggableResponse() {
        return new ExampleName("Joe", "Bloggs");
    }

    public @Loggable String hasException() {
        throw new IllegalArgumentException("Forced exception");
    }
}
