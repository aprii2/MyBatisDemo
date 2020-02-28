package tk.mybatis.simple.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import tk.mybatis.simple.model.Country;

public class CountryMapperTest {
	private static SqlSessionFactory sqlSessionFactory;
	
	@BeforeClass
	public static void init() {
		try {
			// 通过Resources工具类将mybatis-config.xml配置文件读入 Reader
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			// 通过SqlSessionFactoryBuilder建造类使用reader创建SqlSessionFactory
			// 读取mybatis-config.xml中的mappers配置读取全部的Mapper.xml进行具体方法的解析
			// 解析完成后，sqlSessionFactory就包含了所有的属性配置和执行SQL的信息
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			reader.close();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelectAll() {
		// 通过sqlSessionFactory对象获取一个sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			// 通过sqlSession的selectList方法找到id为selectAll的方法，执行SQL查询
			// MyBatis底层使用JDBC执行SQL，获得查询结果集ResultSet后，
			// 根据resultType的配置将结果映射为Country类型的集合，返回查询结果。
			List<Country> countryList = sqlSession.selectList("selectAll");
			printCountryList(countryList);
			
		}finally {
			//不要忘记关闭sqlSession，否则会导致数据库连接数过多，造成系统崩溃。
			sqlSession.close();
		}
	}
	
	private void printCountryList(List<Country> countryList) {
		for(Country country : countryList) {
			System.out.printf("%-4d%4s%4s\n",
					country.getId(),country.getCountryname(),country.getCountrycode());
		}
	}

}
