package kn.multinote.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = SystemSettingDto.TABLE_NAME)
public class SystemSettingDto {

	/**
     * 
     */
	private static final long serialVersionUID = 4358714220268009117L;

	static final String TABLE_NAME = "SystemSetting";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private int widhTruct;

	@DatabaseField
	private int heightTruct;

	public SystemSettingDto() {
	}

	public SystemSettingDto(int widhTruct, int heightTruct) {
		this.widhTruct = widhTruct;
		this.heightTruct = heightTruct;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidthTruct() {
		return widhTruct;
	}

	public void setWidthTruct(int widthTruct) {
		this.widhTruct = widthTruct;
	}

	public int getHeightTruct() {
		return heightTruct;
	}

	public void setHeightTruct(int heightTruct) {
		this.heightTruct = heightTruct;
	}

	public String tableName() {
		return TABLE_NAME;
	}
}
