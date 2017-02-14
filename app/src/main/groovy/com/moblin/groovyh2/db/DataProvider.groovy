package com.moblin.groovyh2.db

import groovy.transform.CompileStatic

@CompileStatic
public class DataProvider {
    List<Customer> initSampleData() {
        def product1 = [name: 'Stainless Steel Pole', price: 99.99] as Product
        def product2 = [name: 'Willow Bar Stool', price: 49.99] as Product
        def product3 = [name: 'Plastic Speed Rails', price: 29.99] as Product
        def product4 = [name: 'Rubber Bar Mat', price: 19.99] as Product
        def product5 = [name: 'Flair Bottles Set', price: 69.99] as Product

        def calendar = Calendar.instance

        calendar.set(2015, 0, 31)
        def order1 = [number: '123', dateCreated: calendar.time, orderLines: [
                [product: product1, quantity: 1] as OrderLine,
                [product: product2, quantity: 5] as OrderLine,
                [product: product3, quantity: 2] as OrderLine,
                [product: product4, quantity: 8] as OrderLine,
                [product: product5, quantity: 3] as OrderLine
        ]] as Order

        calendar.set(2015, 1, 23)
        def order2 = [number: '456', dateCreated: calendar.time, orderLines: [
                [product: product1, quantity: 2] as OrderLine,
                [product: product2, quantity: 3] as OrderLine,
                [product: product3, quantity: 4] as OrderLine,
                [product: product4, quantity: 5] as OrderLine,
                [product: product5, quantity: 6] as OrderLine
        ]] as Order

        calendar.set(2015, 2, 8)
        def order3 = [number: '789', dateCreated: calendar.time, orderLines: [
                [product: product1, quantity: 3] as OrderLine,
                [product: product2, quantity: 3] as OrderLine,
                [product: product3, quantity: 3] as OrderLine,
                [product: product4, quantity: 3] as OrderLine,
                [product: product5, quantity: 3] as OrderLine
        ]] as Order

        calendar.set(2015, 3, 17)
        def order4 = [number: '999', dateCreated: calendar.time, orderLines: [
                [product: product1, quantity: 1] as OrderLine,
                [product: product2, quantity: 9] as OrderLine,
                [product: product3, quantity: 3] as OrderLine,
                [product: product4, quantity: 9] as OrderLine,
                [product: product5, quantity: 1] as OrderLine
        ]] as Order

        def customer1 = [name: 'Roadhouse Bar', orders: [order1, order2]] as Customer
        def customer2 = [name: 'Coyote Ugly Saloon', orders: [order3, order4]] as Customer

        [customer1, customer2]
    }
}