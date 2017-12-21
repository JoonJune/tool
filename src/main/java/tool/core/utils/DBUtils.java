package tool.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import tool.dto.DbInfoDto;
import tool.dto.TableInfoDto;

public class DBUtils {
	
	public static List<TableInfoDto> getList(DbInfoDto dbInfoDto, String sql) {
		List<TableInfoDto> result = new ArrayList<TableInfoDto>();
		
		Connection conn = null;
		try {
			String username = dbInfoDto.getDbUser();
			String password = dbInfoDto.getDbPassword();
			String url = "jdbc:mysql://" + dbInfoDto.getDbIp() + ":" + dbInfoDto.getDbPort() + "/information_schema?&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8";
			Class.forName(dbInfoDto.getDbDriver());
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PreparedStatement pst = null;
		try {
			pst = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				result.add(new TableInfoDto(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
