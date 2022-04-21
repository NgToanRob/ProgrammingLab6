package server.utility;


import data.Address;
import data.Organization;
import data.OrganizationType;
import exceptions.CollectionIsEmptyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private ArrayList<Organization> organizationCollection;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private CollectionFileManager collectionFileManager;

    public CollectionManager(CollectionFileManager collectionFileManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.collectionFileManager = collectionFileManager;

        loadCollection();
        regenerateID();
    }

    /**
     * `regenerateID()`: Regenerates the ID for each organization in the collection
     */
    private void regenerateID(){
        ArrayList<Organization> collection = getCollection();
        long id = 1;
        for(Organization organization : collection){
            organization.setId(id++);
        }
    }

    /**
     * Get the collection of organizations
     *
     * @return An ArrayList of Organization objects.
     */
    public ArrayList<Organization> getCollection() {
        return organizationCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return organizationCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return organizationCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Organization getFirst() {
        return organizationCollection.stream().findFirst().orElse(null);
    }

    public Organization getMinimum() {
        return Collections.min(organizationCollection);
    }

    /**
     * @param id ID of the organization.
     * @return An organization by his ID or null if organization isn't found.
     */
    public Organization getById(Long id) {
        return organizationCollection.stream().filter(organization
                -> ((Long) organization.getId()).equals(id)).findFirst().orElse(null);
    }


    /**
     * @return Collection content or corresponding string if collection is empty.
     */
    public String showCollection() {
        if (organizationCollection.isEmpty()) return "Collection is empty!";
        return organizationCollection.stream().reduce(
                "",
                (sum, m) -> sum += m + "\n\n",
                (sum1, sum2) -> sum1 + sum2
        ).trim();
    }



    /**
     * @param organizationToFilter Organization to filter by.
     * @return Information about valid organizations or empty string, if there's no such organizations.
     */
    public String organizationTypeFilteredInfo(OrganizationType organizationToFilter) {
        return organizationCollection.stream()
                .filter(organization -> organization.getOrganizationType().compareTo(organizationToFilter) > 0)
                .reduce(
                    "", 
                    (sum, m) -> sum += m + "\n\n", 
                    (sum1, sum2) -> sum1 + sum2)
                .trim();
    }


    /**
     * Remove organizations lower than the selected one.
     *
     * @param organizationToCompare A organization to compare with.
     */
    public void removeLower(Organization organizationToCompare) {
        organizationCollection.removeIf(organization -> organization.compareTo(organizationToCompare) < 0);
    }

    /**
     * Adds a new organization to collection.
     *
     * @param organization A organization to add.
     */
    public void addToCollection(Organization organization) {
        organizationCollection.add(organization);
    }

    /**
     * Removes a new organization to collection.
     *
     * @param organization A organization to remove.
     */
    public void removeFromCollection(Organization organization) {
        organizationCollection.remove(organization);
    }
    
    /**
     * Clears the collection.
     */
    public void clearCollection() {
        organizationCollection.clear();
    }
    
    /**
     * Generates next ID. It will be (the bigger one + 1).
     *
     * @return Next ID.
     */
    public Long generateNextId() {
        if (organizationCollection.isEmpty()) return 1L;
        return organizationCollection.get(collectionSize() - 1).getId() + 1;
    }

    /**
     * Sort the organizationCollection by the id field.
     */
    public void sortById() {
        Collections.sort(organizationCollection, new Comparator<Organization>(){
            public int compare(Organization o1, Organization o2){
                return ((Long) o1.getId()).compareTo((Long) o2.getId());
            }
        });
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        collectionFileManager.writeCollection(organizationCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        organizationCollection = collectionFileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Calculate the average annual turnover of all organizations in the collection
     *
     * @return The average of the annual turnover of all the organizations in the
     *         collection.
     */
    public double getAverageOfAnnualTurnover() throws CollectionIsEmptyException {
        // add exception div by 0
        int totalOrganizations = collectionSize();
        if (totalOrganizations == 0)
            throw new CollectionIsEmptyException();

        double sumOfAnnualTurnover = 0;
        for (Organization organization : organizationCollection) {
            sumOfAnnualTurnover += organization.getAnnualTurnover();
        }
        double average = sumOfAnnualTurnover / totalOrganizations;
        return average;
    }

    /**
     * Count the number of organizations whose official address is greater than the
     * given address
     *
     * @param address The address to compare against.
     * @return The number of organizations with an official address that is greater
     *         than the given
     *         address.
     */
    public int getCountGreaterThanOffAddress(Address address) throws CollectionIsEmptyException {
        if (organizationCollection.isEmpty())
            throw new CollectionIsEmptyException();
        int count = 0;
        for (Organization organization : organizationCollection) {
            if (organization.getOfficialAddress().getStreet() == null)
                continue;
            if (organization.getOfficialAddress().compareTo(address) > 0) {
                count += 1;
            }
        }
        return count;
    }


//    /**
//     * Get all the organizations contains given type
//     *
//     * @param typeToFilter The type of organization to filter.
//     * @return The organization type filtered info.
//     */
//    public String organizationTypeFilteredInfo(OrganizationType typeToFilter) {
//        String info = "";
//        for (Organization organization : organizationCollection) {
//            if (organization == null)
//                continue;
//            if (organization.getType().equals(typeToFilter)) {
//                info += organization + "\n\n";
//            }
//        }
//        return info.trim();
//    }
}

