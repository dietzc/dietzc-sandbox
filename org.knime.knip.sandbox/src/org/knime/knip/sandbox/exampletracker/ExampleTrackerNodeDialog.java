package org.knime.knip.sandbox.exampletracker;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.knip.base.data.img.ImgPlusValue;
import org.knime.knip.base.node.dialog.DialogComponentDimSelection;

/**
 * 
 * @author dietzc
 */
public class ExampleTrackerNodeDialog<L extends Comparable<L>> extends
		DefaultNodeSettingsPane {

	/**
     * 
     */
	@SuppressWarnings("unchecked")
	public ExampleTrackerNodeDialog() {

		addDialogComponent(new DialogComponentColumnNameSelection(
				ExampleTrackerNodeModel.createSMSourcesColImg(),
				"Select Image Column: ", 0, true, ImgPlusValue.class));

		addDialogComponent(new DialogComponentDimSelection(
				ExampleTrackerNodeModel.createSMDimSelection(),
				"Select Time Dimension", 1, 1));

	}
}