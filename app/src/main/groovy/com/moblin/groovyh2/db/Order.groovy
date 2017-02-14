package com.moblin.groovyh2.db

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
public class Order {
    String number
    Date dateCreated
    List<OrderLine> orderLines
    String toSting() {"Order: $number"}
}