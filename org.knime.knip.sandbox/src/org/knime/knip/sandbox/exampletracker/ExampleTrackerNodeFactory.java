package org.knime.knip.sandbox.exampletracker;

import net.imglib2.type.numeric.RealType;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * 
 * @author dietzc, University of Konstanz
 */
public class ExampleTrackerNodeFactory<T extends RealType<T>> extends
		NodeFactory<ExampleTrackerNodeModel<T>> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected NodeDialogPane createNodeDialogPane() {
		return new ExampleTrackerNodeDialog<T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExampleTrackerNodeModel<T> createNodeModel() {
		return new ExampleTrackerNodeModel<T>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NodeView<ExampleTrackerNodeModel<T>> createNodeView(
			final int viewIndex, final ExampleTrackerNodeModel<T> nodeModel) {
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
