package br.com.montecardo.simplistic.data.source.postgresql

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 02, 2018 gabryel Exp $
 */
class PostgresDatabase(host: String, database: String, user: String, password: String) {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    val connection: Connection

    init {
        val hostUrl = "jdbc:postgresql://$host"

        getConnection("$hostUrl/postgres", user, password).use { hostConnection ->
            if (!hostConnection.hasDatabase(database)) {
                hostConnection.create(database)
            }
        }

        connection = getConnection("$hostUrl/$database", user, password)
        val version = connection.getVersion()

        connection.updateFrom(version)
    }

    private fun Connection.updateFrom(version: DbVersion) {
        when (version) {
            DbVersion.V0 -> {
                prepareStatement("CREATE SEQUENCE node_seq;").use {
                    it.execute()
                }

                // Based on https://stackoverflow.com/a/25393923/7394603
                prepareStatement("""CREATE TABLE version (
                   key bool PRIMARY KEY DEFAULT TRUE,
                   version text,
                   CONSTRAINT key CHECK (key));""").use {
                    it.execute()
                }

                prepareStatement("INSERT INTO version(key, version) VALUES (true, '');").use {
                    it.execute()
                }

                prepareStatement("""CREATE TABLE node (
                    id bigint PRIMARY KEY NOT NULL DEFAULT nextval('node_seq'),
                    description text NOT NULL,
                    parent_id bigint REFERENCES node);""").use { it.execute() }

                updateFrom(DbVersion.V1)
            }
            DbVersion.V1 -> {
                prepareStatement("UPDATE version SET version = ?;").use {
                    it.setString(1, version.name)
                    it.execute()
                }
            }
        }
    }

    private fun Connection.getVersion(): DbVersion {
        prepareStatement("SELECT to_regclass('version') as exists;").use {
            val set = it.executeQuery()

            set.next()
            set.getString("exists") ?: return DbVersion.V0
        }

        val version = prepareStatement("SELECT * FROM version;").use {
            val set = it.executeQuery()

            set.next()
            set.getString("version")
        }

        return DbVersion.valueOf(version)
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
            prepareStatement("CREATE DATABASE $database;").use {
                it.execute()
            }
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
     * Checks for existence of database on host
     *
     * @receiver Connection to host
     * @param database Database name
     * @return Existence of database
     */
    private fun Connection.hasDatabase(database: String) =
        prepareStatement("SELECT EXISTS(SELECT datname FROM pg_catalog.pg_database WHERE datname = ?);").use {
            it.setString(1, database)
            it.executeQuery().run {
                next()
                getBoolean(1)
            }
        }
}