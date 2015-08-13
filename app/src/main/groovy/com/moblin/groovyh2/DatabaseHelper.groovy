package com.moblin.groovyh2

import groovy.sql.Sql
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode;

@CompileStatic(TypeCheckingMode.SKIP)
public class DatabaseHelper {
    Sql sql;

    DatabaseHelper() {
        def url = 'jdbc:h2:/data/data/com.moblin.groovyh2/data/test.db;FILE_LOCK=FS;PAGE_SIZE=1024;CACHE_SIZE=8192'
        def user = ''
        def password = ''
        def driver = 'org.h2.Driver'
        sql = Sql.newInstance(url, user, password, driver)
        createDatabase()
    }

    /**
     * Deletes the lonely database table
     */
    void destroyDatabase() {
        sql.execute '''
            drop table if exists product
        '''
    }

    /**
     * Creates the lonely database table
     */
    void createDatabase() {
        sql.execute '''
            create table if not exists product (
                id identity primary key,
                name varchar(25),
                price double
            )
        '''
    }

    /**
     * Writes records to the database and returns a message
     */
    String write() {
        sql.execute """
            insert into product (name, price) values
                ('baseball',4.99),('football',14.95),('basketball',14.99)
        """
        "Records written to the database"
    }

    /**
     * Reads and returns all of the records in the database
     */
    String read() {
        getAllProducts().toString()
    }

    /**
     * Deletes products from the database one by one
     */
    String delete() {
        sql.execute 'delete from product'
        "Records deleted"
    }

    /**
     * Returns the list of the stored objects
     */
    List<Product> getAllProducts() {
        sql.rows('select * from product').collect { row ->
            new Product(
                    row.collectEntries { k,v -> [k.toLowerCase(), v] }
            )
        }
    }

    /**
     * Returns the stored object corresponding to the provided id
     */
    Product findProductById(int id) {
        def row = sql.firstRow('select * from product where id=?', id)
        return new Product( row.collectEntries { k,v -> [k.toLowerCase(), v] } );
    }

    /**
     * Maps an object to a database row and inserts it
     */
    void insertProduct(Product p) {
        def params = [p.id, p.name, p.price]
        sql.execute 'insert into product(id,name,price) values(?,?,?)', params
    }

    /**
     * Deletes the stored object from the database
     */
    void deleteProduct(int id) {
        sql.execute 'delete from product where id=?', id
    }

}
