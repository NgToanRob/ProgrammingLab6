package server.commands;

import java.util.Objects;

public abstract class AbstractCommand implements Command{

    private String name;
    private String usage;
    private String description;

    public AbstractCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }

    /**
     * This function returns the name of the command
     *
     * @return The name of the person.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This function returns the usage of the command.
     *
     * @return The usage of the command.
     */
    @Override
    public String getUsage() {
        return usage;
    }

    /**
     * The function returns the description of the command
     *
     * @return The description of the command.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * If the name, usage, and description of the command are the same, then the commands are equal
     *
     * @param o The object to be compared for equality with this object.
     * @return The hashcode of the object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractCommand)) return false;
        AbstractCommand that = (AbstractCommand) o;
        return Objects.equals(getName(),
                that.getName()) && Objects.equals(getUsage(),
                that.getUsage()) && Objects.equals(getDescription(),
                that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUsage(), getDescription());
    }

    @Override
    public String toString() {
        return "AbstractCommand{" +
                "name='" + name + '\'' +
                ", usage='" + usage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
