package org.knime.knip.sandbox.cellcogreader;

import net.imglib2.type.numeric.RealType;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * 
 * @author dietzc, University of Konstanz
 */
public class CellCognitionReaderNodeFactory<T extends RealType<T>> extends NodeFactory<CellCognitionReaderNodeModel<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NodeDialogPane createNodeDialogPane() {
		return new CellCognitionReaderNodeDialog<T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CellCognitionReaderNodeModel<T> createNodeModel() {
		return new CellCognitionReaderNodeModel<T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeView<CellCognitionReaderNodeModel<T>> createNodeView(final int viewIndex,
			final CellCognitionReaderNodeModel<T> nodeModel) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getNrNodeViews() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean hasDialog() {
		return true;
	}

}
