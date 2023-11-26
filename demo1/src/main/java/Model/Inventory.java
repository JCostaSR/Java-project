package Model;

import Controller.Part;
import Controller.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author John C. Costa Sr.
 */

public class Inventory
{
    /** This method is the observable list for allParts. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** This method is the observable list for allProducts. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This initializes uniquePartID with 0. */
    private static int uniquePartId = 1;

    /** @return Unique part Id. */
    public static int getUniquePartId() {
        int counter = 1;
        for(Part ignored : Inventory.getAllParts()){
            counter++;
            uniquePartId = counter;
        }
        return uniquePartId;
    }

    /** This initializes uniqueProductId with 0. */
    private static int uniqueProductId = 1;

    /** @return Unique product Id.  Using Initialization in add product instead of uniqueProductId. */
    public static int getUniqueProductId() {
        int counter = 1;
        for(Product ignored : Inventory.getAllProducts()){
            counter++;
            uniqueProductId = counter;
        }

        return uniqueProductId;
    }

    /** @param newPart add new Part. */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
   }

    /** @param newProduct add new Product. */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    }

    /** @return temp for part Id. */
   public static Part lookupPart(int partId)
    {

            Part temp = null;
        for (Part part : allParts){
            if (partId == part.getId()){
                temp = part;
            }
        }
        return temp;
    }

    /** @return temp for product Id.*/
    public static Product lookupProduct(int productId)
    {
        Product temp = null;
        for (Product products : allProducts){
            if (productId == products.getId()){
                temp = products;
            }
        }
        return temp;
    }

    /** @return found Part name in search part. */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> foundPartName = FXCollections.observableArrayList();
        ObservableList<Part> part = Inventory.getAllParts();
        for(Part pt : part){
            if(pt.getName().toLowerCase().contains((partName).toLowerCase())){
                foundPartName.add(pt);
            }
        }

        return foundPartName;
    }
    /** @return found Product name from search product. */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> foundProductName = FXCollections.observableArrayList();
        ObservableList<Product> product = Inventory.getAllProducts();
        for(Product pd : product){
            if(pd.getName().toLowerCase().contains(productName.toLowerCase())){
                foundProductName.add(pd);
            }

        }

        return foundProductName;
    }

    /** @param index, selectedPart to update Part.  */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /** @param index, newProduct to update Product. */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }
    /** @return remove selectedPart from allParts. */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }
    /** @return remove selectedProduct from allProducts. */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /** @return allParts from Part. */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /** @return allProducts from Product. */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

}
