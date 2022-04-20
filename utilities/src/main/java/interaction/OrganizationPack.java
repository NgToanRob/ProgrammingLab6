package interaction;

import data.Address;
import data.Coordinates;
import data.OrganizationType;

import java.io.Serializable;
import java.util.Objects;


/**
 * The OrganizationPack class is a class that contains all the information about the organization
 */
public class OrganizationPack implements Serializable {
    private String name;
    private Coordinates coordinates;
    private long annualTurnover;
    private OrganizationType organizationType;
    private Address officialAddress;

    public OrganizationPack(String name, Coordinates coordinates, long annualTurnover,
                        OrganizationType organizationType, Address officialAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.organizationType = organizationType;
        this.officialAddress = officialAddress;
    }


    /**
     * Returns the name of the organization
     *
     * @return The name of the organization.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the coordinates of the organization
     *
     * @return The coordinates of the organization.
     */

    public Coordinates getCoordinates() {
        return coordinates;
    }



    /**
     * Returns the annual turnover of the organization
     *
     * @return The annual turnover of the organization.
     */
    public Long getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * Returns the organizationType of the organization
     *
     * @return The organizationType of the organization.
     */
    public OrganizationType getType() {
        return organizationType;
    }

    /**
     *  Returns the organization type of the organization
     *
     * @return The organization type of the organization.
     */
    public OrganizationType getOrganizationType() {
        return organizationType;
    }

    /**
     * Returns the official address of the organization
     *
     * @return The official address of the organization.
     */
    public Address getOfficialAddress() {
        return officialAddress;
    }



//    @Override
//    public String toString() {
//        String info = "";
//        info += "Organization ID: " + id;
//        info += " (added " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
//        info += "\n Name: " + name;
//        info += "\n Coordinates: " + coordinates;
//        info += "\n Annual turnover: " + annualTurnover;
//        info += "\n Organization Type: " + organizationType;
//        info += "\n Official address: " + officialAddress;
//        return info;
//    }


    @Override
    public String toString() {
        return "Organization{\n" +
                ", \n\tname='" + name + '\'' +
                ", \n\tcoordinates=" + coordinates +
                ", \n\tannualTurnover=" + annualTurnover +
                ", \n\torganizationType=" + organizationType +
                ", \n\tofficialAddress=" + officialAddress + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPack)) return false;
        OrganizationPack that = (OrganizationPack) o;
        return getAnnualTurnover() == that.getAnnualTurnover() && Objects.equals(getName(), that.getName()) && Objects.equals(getCoordinates(), that.getCoordinates()) && getOrganizationType() == that.getOrganizationType() && Objects.equals(getOfficialAddress(), that.getOfficialAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoordinates(), getAnnualTurnover(), getOrganizationType(), getOfficialAddress());
    }
}
