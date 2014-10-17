package org.knime.knip.sandbox.cellcogreader;

import org.knime.core.data.IntValue;
import org.knime.core.data.StringValue;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.knip.base.data.img.ImgPlusValue;

/**
 * 
 * @author dietzc, wildnerm University Konstanz
 */
public class CellCognitionReaderNodeDialog<L extends Comparable<L>> extends
		DefaultNodeSettingsPane {

	/**
     * 
     */
	@SuppressWarnings("unchecked")
	public CellCognitionReaderNodeDialog() {

		addDialogComponent(new DialogComponentColumnNameSelection(
				CellCognitionReaderNodeModel.createSMSourcesColImg(), "Img column: ", 0,
				true, ImgPlusValue.class));
		addDialogComponent(new DialogComponentColumnNameSelection(
				CellCognitionReaderNodeModel.createSMSourcesColX(), "x-values: ", 1,
				true, IntValue.class));
		addDialogComponent(new DialogComponentColumnNameSelection(
				CellCognitionReaderNodeModel.createSMSourcesColY(), "y-values: ", 1,
				true, IntValue.class));
		addDialogComponent(new DialogComponentColumnNameSelection(
				CellCognitionReaderNodeModel.createSMSourcesColT(), "t-values: ", 1,
				true, IntValue.class));
		addDialogComponent(new DialogComponentColumnNameSelection(
				CellCognitionReaderNodeModel.createSMSourcesColClass(),
				"class column: ", 1, true, StringValue.class));

	}
}