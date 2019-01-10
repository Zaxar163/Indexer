package ru.zaxar163.indexer.mysql;

import java.util.ArrayList;
import java.util.List;

public class Row {
	private SelectResult parent;
	List<Object> values;

	Row(SelectResult parent) {
		this.parent = parent;
		values = new ArrayList<>();
	}

	public boolean getBoolean(int index) {
		Object val = values.get(index);
		if (val instanceof Number)
			return ((Number) val).intValue() != 0;
		return (boolean) val;
	}

	public boolean getBoolean(String column) {
		return getBoolean(parent.getColumnIndex(column));
	}

	public byte[] getBytes(int index) {
		return (byte[]) values.get(index);
	}

	public byte[] getBytes(String column) {
		return getBytes(parent.getColumnIndex(column));
	}

	public int getInt(int index) {
		Object val = values.get(index);
		if (val instanceof Long)
			return ((Long) val).intValue();
		return (int) val;
	}

	public int getInt(String column) {
		return getInt(parent.getColumnIndex(column));
	}

	public long getLong(int index) {
		return (long) values.get(index);
	}

	public long getLong(String column) {
		return getLong(parent.getColumnIndex(column));
	}

	public Object getObject(int index) {
		return values.get(index);
	}

	public Object getObject(String column) {
		return getObject(parent.getColumnIndex(column));
	}

	public String getString(int index) {
		return (String) values.get(index);
	}

	public String getString(String column) {
		return getString(parent.getColumnIndex(column));
	}
}