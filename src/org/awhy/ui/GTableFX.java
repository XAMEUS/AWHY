package org.awhy.ui;

import java.sql.ResultSet;

import org.awhy.utils.Debugger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class GTableFX extends TableView<ObservableList<String>> {

	private ObservableList<ObservableList<String>> data;

	public void buildData(ResultSet res) {
		data = FXCollections.observableArrayList();
		try {
			if (Debugger.isEnabled())
				Debugger.print("Columns: ");
			for (int i = 0; i < res.getMetaData().getColumnCount(); i++) {
				final int j = i;
				System.out.println(res.getMetaData().getColumnName(i + 1) + ":");
				System.out.println("    " + res.getMetaData().getColumnType(i + 1) + ":" + res.getMetaData().getColumnLabel(i + 1));
				TableColumn<ObservableList<String>, String> col = new TableColumn<>(
						res.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(
									CellDataFeatures<ObservableList<String>, String> param) {
								return new SimpleStringProperty(param.getValue().get(j));
							}
						});
				this.getColumns().add(col);
				if (Debugger.isEnabled())
					Debugger.print(res.getMetaData().getColumnName(i + 1) + " ");
			}
			if (Debugger.isEnabled())
				Debugger.println("");

			while (res.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
					row.add(res.getString(i));
				}
				if (Debugger.isEnabled())
					Debugger.println(row.toString());
				data.add(row);

			}

			this.setItems(data);

		} catch (Exception e) {
			e.printStackTrace();

			if (Debugger.isEnabled())
				System.out.println("Error on Building Data");
		}
	}
}
