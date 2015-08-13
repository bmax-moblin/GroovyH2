package com.moblin.groovyh2

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TypeCheckingMode;

@CompileStatic(TypeCheckingMode.SKIP)
@EqualsAndHashCode
@ToString(includePackage = false, includeNames = true)
public class Product {
    int id
    String name
    double price
}
