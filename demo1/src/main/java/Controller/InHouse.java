package Controller;

/**
 * @author John C. Costa Sr.
 */

public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /** @param machineId sets machine Id. */
        public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** @return machine id. */
        public int getMachineId() {
        return machineId;
    }
}
