/* The MIT License (MIT)
 * Copyright (c) 2014 Nicholas Wright
 * http://opensource.org/licenses/MIT
 */

package com.github.dozedoff.db;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dozedoff.media.MediaDefinition;
import com.github.dozedoff.sources.Webpage;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;

public class Persistence {
	private static Persistence instance = null;
	private static Logger logger = LoggerFactory.getLogger(Persistence.class);

	private final String dbUrl = "jdbc:sqlite:mmut.db";
	private Dao<Webpage, Integer> webPageDao;
	private Dao<MediaDefinition, Integer> definitionDao;
	ConnectionSource cs;

	private Persistence() {
		try {
			cs = new JdbcConnectionSource(dbUrl);
			setupDatabase(cs);
			setupDAO(cs);
		} catch (SQLException e) {
			logger.error("Failed to setup database {}", dbUrl, e);
			System.exit(1);
		}
	}

	public static Persistence getInstance() {
		if (instance == null) {
			instance = new Persistence();
		}

		return instance;
	}

	public void saveWebpage(Webpage page) throws SQLException {
		webPageDao.createOrUpdate(page);
	}

	public List<Webpage> loadWebpage() {
		List<Webpage> pages = new LinkedList<Webpage>();
		try {
			pages = webPageDao.queryForAll();
			logger.info("Loaded {} source entries", pages.size());
		} catch (SQLException e) {
			logger.warn("Failed to load sources {}", e);
		}

		return pages;
	}

	public void deleteWebpage(Webpage page) {
		try {
			webPageDao.delete(page);
		} catch (SQLException e) {
			logger.warn("Failed to delete entry {}", page.getName(), e);
		}
	}

	public void saveDefinition(MediaDefinition definition) {
		try {
			definitionDao.createOrUpdate(definition);
		} catch (SQLException e) {
			logger.warn("Failed to save definition for {}", definition.getParent().getName(), e);
		}
	}

	public void deleteDefinition(MediaDefinition definition) {
		try {
			definitionDao.delete(definition);
		} catch (SQLException e) {
			logger.warn("Failed to delete defintion for {}", definition.getParent().getName(), e);
		}
	}

	public void vacuumDb() {
		if (cs == null) {
			return;
		}

		try {
			DatabaseConnection dbc = cs.getReadWriteConnection();
			dbc.executeStatement("VACUUM", DatabaseConnection.DEFAULT_RESULT_FLAGS);
			cs.releaseConnection(dbc);
		} catch (SQLException e) {
			logger.warn("Vacuum operation failed.", e);
		}
	}

	private void setupDatabase(ConnectionSource cs) throws SQLException {
		logger.info("Setting up database tables...");
		TableUtils.createTableIfNotExists(cs, Webpage.class);
		TableUtils.createTableIfNotExists(cs, MediaDefinition.class);
	}

	private void setupDAO(ConnectionSource cs) throws SQLException {
		logger.info("Setting up DAO...");
		definitionDao = DaoManager.createDao(cs, MediaDefinition.class);
		webPageDao = DaoManager.createDao(cs, Webpage.class);
	}
}
