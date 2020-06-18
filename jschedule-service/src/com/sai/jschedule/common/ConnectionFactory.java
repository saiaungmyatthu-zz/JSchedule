package com.sai.jschedule.common;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ConnectionFactory {
	private static SqlSessionFactory factory;

	static{
		try(Reader reader = Resources.getResourceAsReader("com/sai/jschedule/db/sql/SqlMapConfig.xml")){
			if(factory == null)
				factory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getInstance(){
		return factory;
	}
}
