package tool.dto;

public class DbInfoDto {
	private int id;
	private String env;
	private String dbIp;
	private int dbPort;
	private String dbDriver;
	private String dbUser;
	private String dbPassword;
	private String memo;
	
	public DbInfoDto() {
		
	}
	
	public DbInfoDto(String env, String dbIp, int dbPort, String dbDriver, String dbUser, String dbPassword) {
		this.env = env;
		this.dbIp = dbIp;
		this.dbPort = dbPort;
		this.dbDriver = dbDriver;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}
	
	public String getSql(String sql, String tbSchema) {
		sql = sql.replaceAll("\\{env\\}", this.env);
		sql = sql.replaceAll("\\{tableSchema\\}", tbSchema);
		return sql;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
