package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.source.DbProperties.database
import br.com.montecardo.simplistic.data.source.DbProperties.host
import br.com.montecardo.simplistic.data.source.DbProperties.password
import br.com.montecardo.simplistic.data.source.DbProperties.user
import kdbc.KDBC
import kdbc.Query
import org.postgresql.ds.PGSimpleDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * Object that ensures that the database setup is updated
 * 
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 02, 2018 gabryel Exp $
 */
object PostgresDatabaseSetup {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun setup() {
        ensureDatabaseExists()
        
        val dataSource = PGSimpleDataSource().apply {
            databaseName = database
            serverName = host
            user = DbProperties.user
            password = DbProperties.password
        }

        KDBC.setDataSource(dataSource)
        
        dataSource.connection.use {
            val version = it.getVersion()
            it.updateFrom(version)
        }    
    }

    private fun ensureDatabaseExists() {
        val hostUrl = "jdbc:postgresql://$host/postgres"

        getConnection(hostUrl, user, password).use { hostConnection ->
            if (!hostConnection.hasDatabase(database)) {
                hostConnection.create(database)
            }
        }
    }
    
    private fun Connection.getVersion(): DbVersion {
        if (!hasTable("version")) return DbVersion.V0
        
        return GetVersion().firstOrNull()?: DbVersion.V0
    }

    private fun Connection.updateFrom(version: DbVersion) {
        if (version != DbVersion.V0) execute("UPDATE version set version = '$version'")
        
        when (version) {
            DbVersion.V0 -> {
                execute("CREATE SEQUENCE node_seq;")

                execute("""CREATE TABLE node (
                    id bigint PRIMARY KEY NOT NULL DEFAULT nextval('node_seq'),
                    description text NOT NULL,
                    parent_id bigint REFERENCES node);""")

                // Based on https://stackoverflow.com/a/25393923/7394603
                execute("""CREATE TABLE version (
                   key bool PRIMARY KEY DEFAULT TRUE,
                   version text,
                   CONSTRAINT key CHECK (key));""")
                
                execute("INSERT INTO version(key, version) values(true, 'V0')")

                updateFrom(DbVersion.V1)
            }
            DbVersion.V1 -> { }
        }
    }

    /**
     * Generates the Database on the host, logging and throwing the SQLException if not possible
     *
     * @receiver Connection to host
     * @param database Database name
     */
    @Throws(SQLException::class)
    private fun Connection.create(database: String) {
        try {
            execute("CREATE DATABASE $database;")
        } catch (e: SQLException) {
            logger.trace("Couldn't create database $database on host: ", e)
            throw e
        }
    }

    /**
     * Generates the Connection, logging and throwing the SQLException if not possible
     *
     * @param hostUrl Url to the host
     * @param user User name to login
     * @param password Password to login
     * @return Connection for data source
     */
    @Throws(SQLException::class)
    private fun getConnection(hostUrl: String, user: String, password: String): Connection {
        return try {
            DriverManager.getConnection(hostUrl, user, password)
        } catch (e: SQLException) {
            logger.trace("Couldn't create connection to database $hostUrl: ", e)
            throw e
        }
    }

    /**
     * Checks for existence of a table on database
     *
     * @receiver Connection to database
     * @param table Table name
     * @return Existence of table
     */
    private fun Connection.hasTable(table: String) =
        exists("FROM pg_tables WHERE schemaname = 'public' AND tablename = ?") {
            setString(1, table)
        }

    /**
     * Checks for existence of database on host
     *
     * @receiver Connection to host
     * @param database Database name
     * @return Existence of database
     */
    private fun Connection.hasDatabase(database: String) =
        exists("FROM pg_catalog.pg_database WHERE datname = ?") {
            setString(1, database)
        }
    
    private fun Connection.execute(statement: String, prepare: PreparedStatement.() -> Unit = {}) {
        query(statement) {
            prepare()
            execute()
        }
    }

    private fun <T> Connection.query(statement: String, getResult: PreparedStatement.() -> T): T =
        prepareStatement(statement).use { it.getResult() }

    private fun Connection.exists(statement: String, prepare: PreparedStatement.() -> Unit): Boolean =
        query("SELECT COUNT(*) $statement;") {
            prepare()
            executeQuery().run { 
                next()
                getInt(1) > 0
            }
        }
}

class GetVersion : Query<DbVersion>() {
    init {
        select(VersionTable.version)
        from(VersionTable)
    }

    override fun get() = VersionTable.version()?.let { DbVersion.valueOf(it) }?: DbVersion.V0
}