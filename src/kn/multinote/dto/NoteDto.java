package kn.multinote.dto;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = NoteDto.TABLE_NAME)
public class NoteDto implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 4358714220268009118L;

	static final String TABLE_NAME = "Note";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private String nameNote;

	@DatabaseField
	private byte typeNote;

	@DatabaseField
	private String params;

	@DatabaseField
	private String value;

	@DatabaseField
	private Date date;

	public NoteDto() {
	}

	public NoteDto(String nameNote, byte typeNote, String params, String value,
			Date date) {
		this.nameNote = nameNote;
		this.typeNote = typeNote;
		this.params = params;
		this.value = value;
		this.date = date;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameNote() {
		return nameNote;
	}

	public void setNameNote(String nameNote) {
		this.nameNote = nameNote;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(byte typeNote) {
		this.typeNote = typeNote;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String tableName() {
		return TABLE_NAME;
	}
}
