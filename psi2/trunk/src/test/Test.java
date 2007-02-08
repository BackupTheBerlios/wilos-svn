package test ;
import javax.faces.event.ActionEvent;
import java.util.ArrayList;

/**
 * <p>The <code>SalesGroupRecordBean</code> class is responsible for storing
 * view specific information for the salesGroupRecord Bean. Such things as
 * expand/contract images and css attributes are stored in this Bean. Model can
 * be found in the sub class <code>SalesGroupRecord</code>. </p>
 * <p/>
 * <p>This class is also responsible for handling all events that control how
 * the view (jspx) page behaves.</p>
 */
public class Test{

	protected ArrayList childSalesRecords = new ArrayList(5);
	
    public static final String SPACER_IMAGE =
            "images/tableExpandable/spacer.gif";

    // style for column that holds expand/contract image toggle, in the sales
    // record row.
    protected String indentStyleClass = "";

    // style for all other columns in the sales record row.
    protected String rowStyleClass = "";

    // Images used to represent expand/contract, spacer by default
    protected String expandImage = SPACER_IMAGE;  // arrow points right
    protected String contractImage = SPACER_IMAGE; // arrow point down

    // image which will be drawn to screen
    protected String expandContractImage = SPACER_IMAGE;

    // callback to list which contains all data in the dataTable.  This callback
    // is needed so that a node can be set in the expanded state at construction time.
    protected ArrayList parentInventoryList;

    // indicates if node is in expanded state.
    protected boolean isExpanded;
    
    private String description;

    /**
	 * Getter of description.
	 *
	 * @return the description.
	 */
	public String getDescription() {
		return this.description ;
	}

	/**
	 * Setter of description.
	 *
	 * @param _description The description to set.
	 */
	public void setDescription(String _description) {
		this.description = _description ;
	}

	/**
     * <p>Creates a new <code>SalesGroupRecordBean</code>.  This constructor
     * should be use when creating SalesGroupRecordBeans which will contain
     * children</p>
     *
     * @param isExpanded true, indicates that the specified node will be
     *                   expanded by default; otherwise, false.
     */
    public Test(String expandImage, String contractImage, ArrayList parentInventoryList, boolean isExpanded) {

        this.indentStyleClass = indentStyleClass;
        this.rowStyleClass = rowStyleClass;
        this.expandImage = expandImage;
        this.contractImage = contractImage;
        this.parentInventoryList = parentInventoryList;
        this.parentInventoryList.add(this);
        this.isExpanded = isExpanded;
        // update the default state of the node.
        if (this.isExpanded) {
            expandContractImage = contractImage;
            expandNodeAction();
        } else {
            expandContractImage = expandImage;
        }
    }

    /**
     * <p>Creates a new <code>SalesGroupRecordBean</code>.  This constructor
     * should be used when creating a SalesGroupRecordBean which will be a child
     * of some other SalesGroupRecordBean.</p>
     * <p/>
     * <p>The created SalesGroupRecordBean has no image states defined.</p>
     *
     * @param indentStyleClass
     * @param rowStyleClass
     */
    public Test(String indentStyleClass,
                                String rowStyleClass) {

        this.indentStyleClass = indentStyleClass;
        this.rowStyleClass = rowStyleClass;
    }

    /**
     * Gets the renderable state of the contract/expand image toggle.
     *
     * @return true if images should be drawn; otherwise, false.
     */
    public boolean isRenderImage() {
        return childSalesRecords != null && childSalesRecords.size() > 0;
    }

    /**
     * Toggles the expanded state of this ConcreteBreakDownElement.
     *
     * @param event
     */
    public void toggleSubGroupAction(ActionEvent event) {
        // toggle expanded state
        isExpanded = !isExpanded;

        // add sub elements to list
        if (isExpanded) {
            expandContractImage = contractImage;
            expandNodeAction();
        }
        // remove items from list
        else {
            expandContractImage = expandImage;
            contractNodeAction();
        }
    }

    /**
     * Adds a child sales record to this sales group.
     *
     * @param salesGroupRecord child sales record to add to this record.
     */
    public void addChildSalesGroupRecord(
            Test salesGroupRecord) {
        if (this.childSalesRecords != null && salesGroupRecord != null) {
            this.childSalesRecords.add(salesGroupRecord);
            if (isExpanded) {
                // to keep elements in order, remove all
                contractNodeAction();
                // then add them again.
                expandNodeAction();
            }
        }
    }

    /**
     * Removes the specified child sales record from this sales group.
     *
     * @param salesGroupRecord child sales record to remove.
     */
    public void removeChildSalesGroupRecord(
            Test salesGroupRecord) {
        if (this.childSalesRecords != null && salesGroupRecord != null) {
            if (isExpanded) {
                // remove all, make sure we are removing the specified one too.
                contractNodeAction();
            }
            // remove the current node
            this.childSalesRecords.remove(salesGroupRecord);
            // update the list if needed.
            if (isExpanded) {
                // to keep elements in order, remove all
                contractNodeAction();
                // then add them again.
                expandNodeAction();
            }
        }
    }

    /**
     * Utility method to add all child nodes to the parent dataTable list.
     */
    private void expandNodeAction() {
        if (childSalesRecords != null && childSalesRecords.size() > 0) {
            // get index of current node
            int index = parentInventoryList.indexOf(this);

            // add all items in childSalesRecords to the parent list
            parentInventoryList.addAll(index + 1, childSalesRecords);
        }

    }

    /**
     * Utility method to remove all child nodes from the parent dataTable list.
     */
    private void contractNodeAction() {
        if (childSalesRecords != null && childSalesRecords.size() > 0) {
            // add all items in childSalesRecords to the parent list
            parentInventoryList.removeAll(childSalesRecords);
        }
    }

    /**
     * Gets the style class name used to define the first column of a sales
     * record row.  This first column is where a expand/contract image is
     * placed.
     *
     * @return indent style class as defined in css file
     */
    public String getIndentStyleClass() {
        return indentStyleClass;
    }

    /**
     * Gets the style class name used to define all other columns in the sales
     * record row, except the first column.
     *
     * @return style class as defined in css file
     */
    public String getRowStyleClass() {
        return rowStyleClass;
    }

    /**
     * Gets the image which will represent either the expanded or contracted
     * state of the <code>SalesGroupRecordBean</code>.
     *
     * @return name of image to draw
     */
    public String getExpandContractImage() {
        return expandContractImage;
    }
}