package Controller;

import Controller.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author John C. Costa Sr.
 */

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max){
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
    this.min = min;
    this.max = max;
    }

    /** @param id the id to set. */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the id. */
    public int getId() {
        return id;
    }

    /** @param name the name to set. */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the name. */
    public String getName() {
        return name;
    }

    /** @param price the price to set. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return the price. */
    public double getPrice() {
        return price;
    }

    /** @param stock the stock to set. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @return the stock. */
    public int getStock() {
        return stock;
    }

    /** @param max the max to set. */
    public void setMax(int max) {
        this.max = max;
    }

    /** @return the max. */
    public int getMax() {
        return max;
    }

    /** @param min the min to set */
    public void setMin(int min) {
        this.min = min;
    }

    /** @return the min. */
    public int getMin() {
        return min;
    }
    /** This method adds associated parts. */
    public void addAssociatedPart(Part part){
        associatedParts.addAll(part);

    }
    /** This method deletes associated parts. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }
    /** @return the associated parts. */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
