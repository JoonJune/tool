package tool.dto;

public class DiffTableInfoDto {
	private int id;
	private String tbSchema;
	private String firstDbIp;
	private int firstDbPort;
	private String firstDbDriver;
	private String firstDbUser;
	private String firstDbPassword;
	private String secondDbIp;
	private int secondDbPort;
	private String secondDbDriver;
	private String secondDbUser;
	private String secondDbPassword;
	private String memo;
	
	private int firstDbId;
	private int secondDbId;
	
	public DiffTableInfoDto() {
		
	}
	
	public DiffTableInfoDto(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTbSchema() {
		return tbSchema;
	}

	public void setTbSchema(String tbSchema) {
		this.tbSchema = tbSchema;
	}

	public String getFirstDbIp() {
		return firstDbIp;
	}

	public void setFirstDbIp(String firstDbIp) {
		this.firstDbIp = firstDbIp;
	}

	public int getFirstDbPort() {
		return firstDbPort;
	}

	public void setFirstDbPort(int firstDbPort) {
		this.firstDbPort = firstDbPort;
	}

	public String getFirstDbDriver() {
		return firstDbDriver;
	}

	public void setFirstDbDriver(String firstDbDriver) {
		this.firstDbDriver = firstDbDriver;
	}

	public String getFirstDbUser() {
		return firstDbUser;
	}

	public void setFirstDbUser(String firstDbUser) {
		this.firstDbUser = firstDbUser;
	}

	public String getFirstDbPassword() {
		return firstDbPassword;
	}

	public void setFirstDbPassword(String firstDbPassword) {
		this.firstDbPassword = firstDbPassword;
	}

	public String getSecondDbIp() {
		return secondDbIp;
	}

	public void setSecondDbIp(String secondDbIp) {
		this.secondDbIp = secondDbIp;
	}

	public int getSecondDbPort() {
		return secondDbPort;
	}

	public void setSecondDbPort(int secondDbPort) {
		this.secondDbPort = secondDbPort;
	}

	public String getSecondDbDriver() {
		return secondDbDriver;
	}

	public void setSecondDbDriver(String secondDbDriver) {
		this.secondDbDriver = secondDbDriver;
	}

	public String getSecondDbUser() {
		return secondDbUser;
	}

	public void setSecondDbUser(String secondDbUser) {
		this.secondDbUser = secondDbUser;
	}

	public String getSecondDbPassword() {
		return secondDbPassword;
	}

	public void setSecondDbPassword(String secondDbPassword) {
		this.secondDbPassword = secondDbPassword;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getFirstDbId() {
		return firstDbId;
	}

	public void setFirstDbId(int firstDbId) {
		this.firstDbId = firstDbId;
	}

	public int getSecondDbId() {
		return secondDbId;
	}

	public void setSecondDbId(int secondDbId) {
		this.secondDbId = secondDbId;
	}


}
