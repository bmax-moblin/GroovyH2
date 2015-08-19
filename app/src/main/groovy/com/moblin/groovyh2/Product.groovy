package com.moblin.groovyh2

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
public class Product {
    String name
    BigDecimal price
    String toString() {"Product: $name ($price)"}
}
