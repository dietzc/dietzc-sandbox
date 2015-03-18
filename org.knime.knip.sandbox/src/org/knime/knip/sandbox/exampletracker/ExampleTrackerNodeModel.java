package org.knime.knip.sandbox.exampletracker;

import java.io.File;
import java.io.IOException;

import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imglib2.type.numeric.RealType;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.container.CloseableRowIterator;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.knip.base.data.img.ImgPlusCell;
import org.knime.knip.base.data.img.ImgPlusCellFactory;
import org.knime.knip.base.data.img.ImgPlusValue;
import org.knime.knip.base.node.NodeUtils;
import org.knime.knip.base.node.nodesettings.SettingsModelDimSelection;

/**
 * 
 * @author dietzc, University of Konstanz
 * @param <T>
 * @param <V>
 */
public class ExampleTrackerNodeModel<T extends RealType<T>> extends NodeModel {

	private SettingsModelString m_smImgCol = createSMSourcesColImg();

	private SettingsModelDimSelection m_smDimSelection = createSMDimSelection();

	static SettingsModelString createSMSourcesColImg() {
		return new SettingsModelString("sourceColImg", "");
	}

	static SettingsModelDimSelection createSMDimSelection() {
		return new SettingsModelDimSelection("dimSelection", Axes.TIME);
	}

	protected ExampleTrackerNodeModel() {
		// one inport one outport
		super(1, 1);
	}

	/**
	 * DataTableSpec
	 */
	private int m_imgColIdx;

	@Override
	protected DataTableSpec[] configure(DataTableSpec[] inSpecs)
			throws InvalidSettingsException {
		return new DataTableSpec[] { createOutSpec() };
	}

	private DataTableSpec createOutSpec() {
		// here you define the layout of your output table
		DataColumnSpec[] colSpecs = new DataColumnSpec[4];

		colSpecs[0] = new DataColumnSpecCreator("RGB Image", ImgPlusCell.TYPE)
				.createSpec();

		colSpecs[1] = new DataColumnSpecCreator("Z", DoubleCell.TYPE)
				.createSpec();

		colSpecs[2] = new DataColumnSpecCreator("Feature", DoubleCell.TYPE)
				.createSpec();

		colSpecs[3] = new DataColumnSpecCreator("Some String", StringCell.TYPE)
				.createSpec();

		return new DataTableSpec(colSpecs);
	}

	@Override
	protected BufferedDataTable[] execute(BufferedDataTable[] inData,
			ExecutionContext exec) throws Exception {

		// index of image in input colum
		m_imgColIdx = inData[0].getDataTableSpec().findColumnIndex(
				m_smImgCol.getStringValue());

		if (m_imgColIdx == -1) {

			if ((m_imgColIdx = NodeUtils.silentOptionalAutoColumnSelection(
					inData[0].getDataTableSpec(), m_smImgCol,
					ImgPlusValue.class)) >= 0) {
				setWarningMessage("Auto-configure Column: "
						+ m_smImgCol.getStringValue());
			} else {
				throw new InvalidSettingsException(
						"No column for img selected!");
			}
		}

		final ImgPlusCellFactory factory = new ImgPlusCellFactory(exec);

		// iterate over table and do something
		final BufferedDataContainer container = exec
				.createDataContainer(createOutSpec());

		final CloseableRowIterator it = inData[0].iterator();
		int i = 0;
		while (it.hasNext()) {
			DataRow row = it.next();

			// only use ImgPlus from the package net.imagej.*
			// always cast on values. a cell may implement many values
			ImgPlus<T> imgPlus = ((ImgPlusValue<T>) row.getCell(m_imgColIdx))
					.getImgPlus();

			int[] selectedDimIndices = m_smDimSelection
					.getSelectedDimIndices(imgPlus);

			if (selectedDimIndices.length == 0) {
				throw new InvalidSettingsException(
						"Time Dimension does not exist in input image!");
			}
			// we only allow to select one dimension here
			int tIndex = selectedDimIndices[0];

			ImgPlus<T> res = doSomething(imgPlus, tIndex);

			ImgPlusCell<T> rgbCell = factory.createCell(res);

			// for example for each track
			for (int k = 0; k < 10; k++) {
				// write results
				container.addRowToTable(new DefaultRow("Row" + (i * 10 + k),
						rgbCell, new DoubleCell(k), new DoubleCell(i),
						new StringCell("Somerandomstring")));
			}

			i++;
		}

		container.close();

		return new BufferedDataTable[] { container.getTable() };
	}

	private ImgPlus<T> doSomething(ImgPlus<T> imgPlus, int tIndex) {
		return imgPlus;
	}

	@Override
	protected void saveSettingsTo(NodeSettingsWO settings) {
		m_smImgCol.saveSettingsTo(settings);
		m_smDimSelection.saveSettingsTo(settings);
	}

	@Override
	protected void validateSettings(NodeSettingsRO settings)
			throws InvalidSettingsException {
		m_smImgCol.validateSettings(settings);
		m_smDimSelection.validateSettings(settings);
	}

	@Override
	protected void loadValidatedSettingsFrom(NodeSettingsRO settings)
			throws InvalidSettingsException {
		m_smImgCol.loadSettingsFrom(settings);
		m_smDimSelection.loadSettingsFrom(settings);
	}

	@Override
	protected void reset() {
		// Nothing to do here, yet
	}

	@Override
	protected void loadInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

	}

	@Override
	protected void saveInternals(File nodeInternDir, ExecutionMonitor exec)
			throws IOException, CanceledExecutionException {

	}
}
