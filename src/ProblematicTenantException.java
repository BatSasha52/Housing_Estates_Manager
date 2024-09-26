public class ProblematicTenantException extends Exception {
    public ProblematicTenantException(Person person, int rentedRoomsNumber) {
        super("Person "+ person.getName() +" had already renting rooms: "+ rentedRoomsNumber);
    }
}