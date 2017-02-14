package com.moblin.groovyh2.db

import groovy.sql.Sql
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic(TypeCheckingMode.SKIP)
public class DatabaseHelper {
    Sql sql;

    DatabaseHelper(String dbNameWithPath) {
        def url = "jdbc:h2:${dbNameWithPath};FILE_LOCK=FS;PAGE_SIZE=1024;CACHE_SIZE=8192"
        def user = ''
        def password = ''
        def driver = 'org.h2.Driver'
        sql = Sql.newInstance(url, user, password, driver)
        createDatabase()
    }

    /**
     * Creates the database tables
     */
    void createDatabase() {
        sql.execute '''
            CREATE TABLE IF NOT EXISTS products (
                id IDENTITY PRIMARY KEY,
                name VARCHAR(50),
                price DECIMAL(20, 2)
            );
            CREATE TABLE IF NOT EXISTS order_lines (
                id IDENTITY PRIMARY KEY,
                product_id BIGINT,
                order_id BIGINT,
                quantity INTEGER
            );
            CREATE TABLE IF NOT EXISTS orders (
                id IDENTITY PRIMARY KEY,
                num VARCHAR(50),
                date_created DATE,
                customer_id BIGINT
            );
            CREATE TABLE IF NOT EXISTS customers (
                id IDENTITY PRIMARY KEY,
                name VARCHAR(50),
            );
        '''
    }

    /**
     * Writes records to the database and returns a message
     */
    String write() {
        new DataProvider().initSampleData().each { customer ->
            long customerKey = insertCustomer(customer)
            customer.orders.each { order ->
                long orderKey = insertOrder(order, customerKey)
                order.orderLines.each { line ->
                    long productKey = insertProduct(line.product)
                    insertOrderLine(line, orderKey, productKey)
                }
            }
        }
        "Records written to the database"
    }

    /**
     * Reads and returns all of the records in the database
     */
    String read() {
        getAllCustomers().collect({ customer ->
            def orders = customer.orders.collect { order ->
                def orderLines = order.orderLines.collect { line ->
                    "        ${line.product.name} x ${line.quantity} = ${line.price}"
                }.join('\n')
                "    number=${order.number}, date=${order.dateCreated}, lines:\n${orderLines}"
            }.join('\n')
            "customer=${customer.name}, orders:\n${orders}"
        }).join('\n')  ?: 'There are no records'
    }

    /**
     * Removes all of the records from the database
     */
    String delete() {
        sql.execute '''
            DELETE FROM products;
            DELETE FROM orders;
            DELETE FROM customers;
            DELETE FROM order_lines;
        '''
        "Records deleted"
    }

    /**
     * Returns the list of the stored customers
     */
    List<Customer> getAllCustomers() {
        sql.rows('SELECT * FROM customers').collect {
            def row = it.collectEntries { k,v -> [k.toLowerCase(), v] }
            new Customer(
                    name: row.name,
                    orders: findOrders(row.id)
            )
        }
    }

    /**
     * Returns the list of orders for a customer with the provided ID
     */
    List<Order> findOrders(long customerId) {
        sql.rows("SELECT * FROM orders WHERE customer_id=${customerId}").collect {
            def row = it.collectEntries { k,v -> [k.toLowerCase(), v] }
            new Order(
                    number: row.num,
                    dateCreated: row.date_created,
                    orderLines: findOrderLines(row.id)
            )
        }
    }

    /**
     * Returns a list of order lines for an order with the provided ID
     */
    List<OrderLine> findOrderLines(long orderId) {
        sql.rows("SELECT * FROM order_lines WHERE order_id=${orderId}").collect {
            def row = it.collectEntries { k,v -> [k.toLowerCase(), v] }
            new OrderLine(
                    product: findProduct(row.product_id),
                    quantity: row.quantity
            )
        }
    }

    /**
     * Returns the stored product corresponding to the provided ID
     */
    Product findProduct(long id) {
        def row = sql.firstRow('SELECT name, price FROM products WHERE id=?', id)
        return new Product( row.collectEntries { k,v -> [k.toLowerCase(), v] } );
    }

    /**
     * Maps a product to a database row and inserts it
     */
    long insertProduct(Product p) {
        String insertSql = 'INSERT INTO products (name, price) values (?, ?)'
        List params = [p.name, p.price]
        def productKeys = sql.executeInsert insertSql, params
        (long)productKeys[0][0]
    }

    /**
     * Maps an order to a database row and inserts it
     */
    long insertOrder(Order o, customerKey) {
        String insertSql = 'INSERT INTO orders (num, date_created, customer_id) VALUES (?, ?, ?)'
        List params = [o.number, o.dateCreated, customerKey]
        def orderKeys = sql.executeInsert insertSql, params
        (long)orderKeys[0][0]
    }

    /**
     * Maps a customer to a database row and inserts it
     */
    long insertCustomer(Customer c) {
        String insertSql = 'INSERT INTO customers (name) VALUES (?)'
        List params = [c.name]
        def customerKeys = sql.executeInsert insertSql, params
        (long)customerKeys[0][0]
    }

    /**
     * Maps an order line to a database row and inserts it
     */
    long insertOrderLine(OrderLine l, long orderKey, long productKey) {
        String insertSql = 'INSERT INTO order_lines (order_id, product_id, quantity) VALUES (?, ?, ?)'
        List params = [orderKey, productKey, l.quantity]
        def lineKeys = sql.executeInsert insertSql, params
        (long)lineKeys[0][0]
    }

}
