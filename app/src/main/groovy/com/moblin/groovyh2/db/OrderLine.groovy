package com.moblin.groovyh2.db

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

@CompileStatic
@EqualsAndHashCode
public class OrderLine {
    Product product
    int quantity
    double getPrice() {quantity * product?.price}
    String toSting() {"$product x $quantity"}
}