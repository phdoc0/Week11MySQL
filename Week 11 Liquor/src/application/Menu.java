package application;

import dao.LiquorDao;
import entity.Liquor;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
	

    private Scanner scanner = new Scanner(System.in);
    private List<String> options = Arrays.asList(
            "Display current liquor items",
            "Display a specific liquor item",
            "Create a liquor item",
            "Update a liquor item",
            "Delete a liquor item",
            "Exit"
    );

    LiquorDao liquorDao = new LiquorDao();

    public void start() {

        String selection = "";

        do {
            printMenu();
            selection = scanner.nextLine();

            try {
                switch (selection) {
                    case "1": displayAllLiquor();
                        break;
                    case "2": displayLiquor();
                        break;
                    case "3": createLiquor();
                        break;
                    case "4": updateLiquor();
                        break;
                    case "5": deleteLiquor();
                        break;

                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("There was an error retrieving the selected information.");
            }

            System.out.println("Press enter to continue...");
            scanner.nextLine();

        } while (!selection.equals("6"));

    }

    private void printMenu() {
    	System.out.println("Select an Option below:");
        for (int i = 0; i < options.size(); i++) {
        	System.out.println(i + 1 + ") " + options.get(i));
        }
    }

    private void displayAllLiquor() throws SQLException {

        List<Liquor> allLiquor = liquorDao.getAllLiquor();

        System.out.println("All Liquor Items");

        if (allLiquor.size() == 0) System.out.println("There are no liquors to display...");

        for (Liquor liquor : allLiquor) {
            printLiquorItem(liquor);
        }

      
    }

    private void displayLiquor() throws SQLException {
    	System.out.println("Liquor Item");
        printLiquorItem(getLiquorById());
    
    }

    private void printLiquorItem(Liquor liquorItem) throws SQLException {
    	System.out.println("#" + liquorItem.getId() + " " + liquorItem.getName() + " $" + liquorItem.getPrice().doubleValue() + " per " + liquorItem.getQuantity());
    }

    private void createLiquor() throws SQLException {
    	System.out.println("New Liquor Item");
    	System.out.println("Enter the new liquor name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter the quantity: ");
        String quantity = scanner.nextLine().trim();

        if (quantity.equals("") || quantity.equals("1")) quantity = "1 item";

        System.out.println("Enter the price: ");
        Double price = scanner.nextDouble();

        liquorDao.createNewLiquor(name, quantity, price);

        
    }

    private void updateLiquor() throws SQLException {
    	System.out.println("Update Liquor Item");

        Liquor liquorItem = getLiquorById();

        printLiquorItem(liquorItem);
        System.out.println("Select what you want to update: ");
        System.out.printf("Name", 1);
        System.out.printf("Price", 2);
        System.out.printf("Quantity", 3);
        System.out.printf("Cancel", 0);

        int selection = Integer.parseInt(scanner.nextLine());

        switch (selection) {
            case 1: setLiquorName(liquorItem);
                break;
            case 2: setLiquorPrice(liquorItem);
                break;
            case 3: setLiquorQuantity(liquorItem);
                break;
            default: // ANY OTHER KEY TO CANCEL
            	System.out.println("Cancelling update...\n");
                break;
        }

    
    }

    private void setLiquorName(Liquor liquorItem) throws SQLException {
    	System.out.println("Enter the new name: ");
        liquorItem.setName(scanner.nextLine());

        liquorDao.updateLiquor(liquorItem);
    }

    private void setLiquorPrice(Liquor liquorItem) throws SQLException {
    	System.out.println("Enter the new price: ");
        liquorItem.setPrice(Double.parseDouble(scanner.nextLine()));

        liquorDao.updateLiquor(liquorItem);
    }

    private void setLiquorQuantity(Liquor liquorItem) throws SQLException {
    	System.out.println("Enter the new quantity: ");
        liquorItem.setQuantity(scanner.nextLine());

        liquorDao.updateLiquor(liquorItem);
    }

    private void deleteLiquor() throws SQLException {
    	System.out.println("Delete Liquor Item");

        Liquor liquorItem = getLiquorById();
        printLiquorItem(liquorItem);
        System.out.println("Are you sure you want to delete this item? Y or N? ");

        if (scanner.nextLine().toUpperCase().charAt(0) == 'Y') {
            liquorDao.deleteLiquor(liquorItem.getId());
        } else {
        	System.out.println("Cancelling delete...");
        }

       
    }

    private Liquor getLiquorById() throws SQLException {
    	System.out.println("Enter the liquor id: ");
        int id = Integer.parseInt(scanner.nextLine());
        return liquorDao.getLiquorById(id);
    }

}
