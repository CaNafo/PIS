package model;

public class TableColumn {
	private String name;
	private String dataType;
	private Boolean isNullable;
	private Boolean isAutoincrement;
	private Boolean isName;
	private Integer length = null;

	private String parrentName = null;

	public TableColumn(String name, String dataType, Boolean isNull, Boolean isAuteincrement, Boolean isName) {
		this.name = name;
		this.dataType = dataType;
		this.isNullable = isNull;
		this.isAutoincrement = isAuteincrement;
		this.isName = isName;
	}

	@Override
	public String toString() {
		return "Column: [Name: " + name + "\tData Type: " + dataType + "\tLength: " + length + "\t|Parrent Name: "
				+ parrentName + ", isName: " + isName +", Is Autoincrement: "+isAutoincrement+ "]";
	}

	public String getName() {
		return name;
	}

	public String getDataType() {
		return dataType;
	}

	public Boolean getIsNull() {
		return isNullable;
	}

	public Boolean getIsName() {
		return isName;
	}

	public Boolean getIsAutoincrement() {
		return isAutoincrement;
	}

	public String getParrentName() {
		return parrentName;
	}

	public void setParrentName(String parrentName) {
		this.parrentName = parrentName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
	
	public void setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
	}
}
