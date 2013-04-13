/*  Copyright (C) 2013  Nicholas Wright
    
    This file is part of mmut - Multi-media update tracker
    
    mmut is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import com.j256.ormlite.table.TableUtils;

public class Persistence {
	private static Persistence instance = null;
	private static Logger logger = LoggerFactory.getLogger(Persistence.class);
	
	private final String dbUrl = "jdbc:sqlite:mmut.db";
	
	private Dao<Webpage, Integer> webPageDao;
	private Dao<MediaDefinition, Integer> definitionDao;
	
	private Persistence() {
		try {
			ConnectionSource cs = new JdbcConnectionSource(dbUrl);
			setupDatabase(cs);
			setupDAO(cs);
		} catch (SQLException e) {
			logger.error("Failed to setup database {}", dbUrl, e);
			System.exit(1);
		}
	}
	
	public static Persistence getInstance() {
		if(instance == null) {
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
