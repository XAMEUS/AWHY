package org.awhy.ui.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.awhy.core.objects.Object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public abstract class Table<E extends Object> extends TableView<E>{
	
	protected final ObservableList<E> data;
	protected final E typeParameterClass;
	
	public Table(E obj, ResultSet res) throws SQLException {
		this.typeParameterClass = obj;
		this.data = FXCollections.observableArrayList();
		while (res.next()) {
			this.data.add((E) (obj.createFromSQL(res)));
		}
		
	}
	
}
