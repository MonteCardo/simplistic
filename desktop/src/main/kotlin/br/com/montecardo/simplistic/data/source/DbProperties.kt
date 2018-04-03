package br.com.montecardo.simplistic.data.source

import java.util.*

/**
 * A singleton that read database properties from a resources file
 * 
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 03, 2018 gabryel Exp $
 */ 
object DbProperties {
    private val dbProperties = Properties().apply { load(javaClass.getResourceAsStream("/db.properties")) }
    
    val host: String = dbProperties.getProperty("host")
    val user: String = dbProperties.getProperty("user")
    val password: String = dbProperties.getProperty("password")
    val database: String = dbProperties.getProperty("database")
}