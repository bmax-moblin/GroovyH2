package com.moblin.groovyh2.db

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
public class Customer {
    String name
    List<Order> orders
    String toSting() {"Customer: $name"}
}