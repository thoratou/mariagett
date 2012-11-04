package com.tt.mariage.client;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.tt.mariage.client.data.Person;

public class PersonCellTable {
	
	public interface GetValue<C> {
		C getValue(Person contact);
	}
	
	protected <C> Column<Person, C> addColumn(	AbstractCellTable<Person> cellTable,
												Cell<C> cell,
												String headerText,
												final GetValue<C> getter,
												FieldUpdater<Person, C> fieldUpdater) {
		Column<Person, C> column = new Column<Person, C>(cell) {
			@Override
			public C getValue(Person person) {
				return getter.getValue(person);
			}
		};

		column.setFieldUpdater(fieldUpdater);

		cellTable.addColumn(column, headerText);
		return column;
	}
}
