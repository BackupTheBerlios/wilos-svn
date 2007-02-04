package test ;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class TestManager {

    private ArrayList inventoryGroupItemBeans;

    private boolean isInit;

    // css style related constants
    public static final String GROUP_INDENT_STYLE_CLASS = "groupRowIndentStyle";
    public static final String GROUP_ROW_STYLE_CLASS = "groupRowStyle";
    public static final String CHILD_INDENT_STYLE_CLASS = "childRowIndentStyle";
    public static final String CHILD_ROW_STYLE_CLASS = "childRowStyle";
    // toggle for expand contract
    public static final String CONTRACT_IMAGE =
            "images/xpwilos/arrow-previous.gif";
    public static final String EXPAND_IMAGE =
            "images/xpwilos/arrow-next.gif";

    public TestManager() {
        init();
    }

    private void init() {

        // check if manager has been initiated
        if (isInit) {
            return;
        }
        isInit = true;

        // initiate the list
        if (inventoryGroupItemBeans != null) {
            inventoryGroupItemBeans.clear();
        } else {
            inventoryGroupItemBeans = new ArrayList(10);
        }

        /**
         * Build the array list group items.  Currently static but could be easily
         * bound to a database.
         */

        // Tuesday's group
        Test salesRecordGroup =
                new Test(EXPAND_IMAGE, CONTRACT_IMAGE,
                                         inventoryGroupItemBeans, false);
        salesRecordGroup.setDescription("Tuesday's Items");

        // add Tuesday's children
        Test childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("2mm Torx screws");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);
        childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("5mm Torx screws");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);

        // Wednesday's group
        salesRecordGroup =
                new Test(EXPAND_IMAGE, CONTRACT_IMAGE,
                                         inventoryGroupItemBeans, true);
        salesRecordGroup.setDescription("Wednesday's Items");

        // add Wednesday's children
        childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("Steel Hammer");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);
        childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("Bag of 10# nails");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);
        childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("Bag of 15# nails");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);

        // Thursday's group
        salesRecordGroup =
                new Test(EXPAND_IMAGE, CONTRACT_IMAGE,
                                         inventoryGroupItemBeans, false);
        salesRecordGroup.setDescription("Thursday's Items");

        // add Thursday's children
        childSalesGroup =
                new Test(CHILD_INDENT_STYLE_CLASS,
                                         CHILD_ROW_STYLE_CLASS);
        childSalesGroup.setDescription("B&D Table Saw");
        salesRecordGroup.addChildSalesGroupRecord(childSalesGroup);

    }

    /**
     * Gets the list of SalesGroupRecordBean which will be used by the
     * ice:dataTable component.
     *
     * @return array list of parent SalesGroupRecordBeans
     */
    public ArrayList getSalesGroupRecordBeans() {
        return inventoryGroupItemBeans;
    }
}
