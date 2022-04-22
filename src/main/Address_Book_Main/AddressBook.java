import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AddressBook {
    private final Map<String, List<Contacts>> map = new LinkedHashMap<>();
    private final Map<String, List<Contacts>> cityMap = new LinkedHashMap<>();
    private final Scanner in = new Scanner(System.in);
    private final List<String> cityName = new ArrayList<>();

    public void setUpInfo() {

        System.out.println("How many Address Book you want to create");
        int bookCount = in.nextInt();
        for (int b = 1; b <= bookCount; b++) {
            System.out.println("Name an Address Book");
            String bookName = in.next();
            map.put(bookName, new LinkedList<>());
            System.out.println("How many contacts you want to create");
            int contactCount = in.nextInt();
            for (int i = 1; i <= contactCount; i++) {
                System.out.println("Enter the First Name:");
                String firstname = in.next();
                System.out.println("Enter the Last Name:");
                String lastname = in.next();
                System.out.println("Enter the Address in words:");
                String address = in.next();
                System.out.println("Enter the City:");
                String city = in.next();
                System.out.println("Enter the State Name:");
                String state = in.next();
                System.out.println("Enter the ZIP code:");
                int zip = in.nextInt();
                System.out.println("Enter the Phone No");
                long phone = in.nextLong();
                System.out.println("Enter email id:");
                String email = in.next();
                Contacts contact = new Contacts(firstname, lastname, address, city, state, zip, phone, email);
                int match = 0;
                if (map.get(bookName).isEmpty()) {
                    map.get(bookName).add(contact);
                } else if (map.get(bookName).size() >= 1) {
                    for (Contacts info : map.get(bookName)) {
                        if (info.getFirstName().equals(firstname)) {
                            match = 1;
                            System.out.println("Contact already exists in the Address Book");
                        }
                    }
                    if (match == 0) {
                        map.get(bookName).add(contact);
                    }
                }
                if (!cityName.contains(city)) {
                    cityName.add(city);
                    cityMap.put(city, new LinkedList<>());
                    cityMap.get(city).add(contact);
                } else {
                    cityMap.get(city).add(contact);
                }
            }
        }
    }

    public void displayListItems() {
        int i = 0;
        for (String key : map.keySet()) {
            i++;
            System.out.println("Address Book #" + i + ": " + key);
            List<Contacts> sortedList = map.get(key).stream().sorted(Comparator.comparing(Contacts::getFirstName)).toList();
            for (Contacts info : sortedList) {
                System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
            }
            System.out.println();
        }
    }

    public void editContact() {
        int i = 0;
        while (i < 3) {
            boolean isPresent = false;
            System.out.println("Enter the name of the Address Book from which contact is to be edited");
            String name = in.next();
            if (map.containsKey(name)) {
                System.out.println("Enter the first name of the contact to be edited");
                String newName = in.next();
                for (Contacts info : map.get(name)) {
                    if (Objects.equals(info.getFirstName(), newName)) {
                        System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
                        System.out.println("Enter the First Name:");
                        String firstname = in.next();
                        info.setFirstName(firstname);
                        System.out.println("Enter the Last Name:");
                        String lastname = in.next();
                        info.setLastName(lastname);
                        System.out.println("Enter the Address in words:");
                        String address = in.next();
                        info.setAddress(address);
                        System.out.println("Enter the City:");
                        String city = in.next();
                        String oldCity = info.getCity();
                        info.setCity(city);
                        System.out.println("Enter the State Name:");
                        String state = in.next();
                        info.setState(state);
                        System.out.println("Enter the ZIP code:");
                        int zip = in.nextInt();
                        info.setZip(zip);
                        System.out.println("Enter the Phone No");
                        long phone = in.nextLong();
                        info.setPhoneNo(phone);
                        System.out.println("Enter email id:");
                        String email = in.next();
                        info.setEmail(email);
                        Contacts contact = new Contacts(firstname, lastname, address, city, state, zip, phone, email);
                        i = 3;
                        isPresent = true;
                        if (!cityName.contains(city)) {
                            cityName.add(city);
                            cityMap.put(city, new LinkedList<>());
                            cityMap.get(oldCity).removeIf(person -> Objects.equals(person.getFirstName(), newName));
                            cityMap.get(city).add(contact);
                        } else {
                            for (Contacts person : cityMap.get(info.getCity())) {
                                if (Objects.equals(person.getFirstName(), newName)) {
                                    cityMap.get(city).remove(person);
                                    cityMap.get(city).add(contact);
                                }
                            }
                        }
                        break;
                    }
                }
                if (!isPresent) {
                    System.out.println("No Such Name found, please re-enter the Name");
                    i++;
                }
            } else {
                System.out.println("No Such Address Book found");
                i++;
            }
        }
        displayListItems();
    }

    public void deleteContact() {
        int i = 0;
        while (i < 3) {
            boolean isPresent = false;
            System.out.println("Enter the name of the Address Book from which contact is to be deleted");
            String name = in.next();
            if (map.containsKey(name)) {
                System.out.println("Enter the first name of the contact to be edited");
                String newName = in.next();
                for (Contacts info : map.get(name)) {
                    if (Objects.equals(info.getFirstName(), newName)) {
                        System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
                        map.get(name).remove(info);
                        isPresent = true;
                        i = 3;
                        if (cityName.contains(info.getCity())) {
                            cityMap.get(info.getCity()).remove(info);
                        }
                        break;
                    }
                }
                if (!isPresent) {
                    System.out.println("No Such value found");
                    i++;
                }
            } else {
                System.out.println("No Such Address Book");
                i++;
            }
        }
        displayListItems();
    }

    public void add() {
        int j = 0;
        while (j < 3) {
            boolean isPresent = false;
            System.out.println("Enter the AddressBook Name to which Contact is to be added");
            String accountName = in.next();
            if (map.containsKey(accountName)) {
                System.out.println("Enter the first name of the Contact to be added to AddressBook");
                String name = in.next();
                Contacts contact1 = new Contacts();
                contact1.setFirstName(name);
                for (Contacts info : map.get(accountName)) {
                    if (info.getFirstName().equals(contact1.getFirstName())) {
                        System.out.println();
                        System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
                        System.out.println("Contact already exists in Account, please use option 2");
                        j = 3;
                        isPresent = true;
                        break;
                    }
                }
                if (!isPresent) {
                    System.out.println("Enter the Last Name:");
                    String lastname = in.next();
                    System.out.println("Enter the Address in words:");
                    String address = in.next();
                    System.out.println("Enter the City:");
                    String city = in.next();
                    System.out.println("Enter the State Name:");
                    String state = in.next();
                    System.out.println("Enter the ZIP code:");
                    int zip = in.nextInt();
                    System.out.println("Enter the Phone No");
                    long phone = in.nextLong();
                    System.out.println("Enter email id:");
                    String email = in.next();
                    Contacts contact = new Contacts(name, lastname, address, city, state, zip, phone, email);
                    map.get(accountName).add(contact);
                    if (!cityName.contains(city)) {
                        cityName.add(city);
                        cityMap.put(city, new LinkedList<>());
                        cityMap.get(city).add(contact);
                    } else {
                        cityMap.get(city).add(contact);
                    }

                    j = 3;
                }
            } else {
                System.out.println();
                System.out.println("No Such Address Book");
            }
        }
        displayListItems();
    }

    public void search() {
        System.out.println("Enter 1 to Search a Contact by City/Press 2 to Search a Contact by State");
        int input = in.nextInt();
        switch (input) {
            case 1 -> {
                int count1 = 0;
                System.out.println("Enter the City Name");
                String city = in.next();
                for (String key : map.keySet()) {
                    for (Contacts info : map.get(key)) {
                        if (info.getCity().equals(city)) {
                            count1++;
                            System.out.println(key + " " + info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
                        }
                    }
                }
                if (count1 == 0) {
                    System.out.println("No such Contact with city " + city + " in any of the Address Books");
                }
                System.out.println(count1 + " Contacts found with " + city + " city");
            }
            case 2 -> {
                int count2 = 0;
                System.out.println("Enter the State Name");
                String state = in.next();
                for (String key : map.keySet()) {
                    for (Contacts info : map.get(key)) {
                        if (info.getState().equals(state)) {
                            count2++;
                            System.out.println(key + " " + info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
                        }
                    }
                }
                if (count2 == 0) {
                    System.out.println("No such Contact with state " + state + " in any of the Address Books");
                }
                System.out.println(count2 + " Contacts found with " + state + " state");
            }
            default -> {
            }
        }
    }

    public void displayCityMap() {
        int i = 0;
        for (String key : cityMap.keySet()) {
            i++;
            System.out.println("City #" + i + ": " + key);
            for (Contacts info : cityMap.get(key)) {
                System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
            }
            System.out.println();
        }
    }

    public void sortByCity() {
        int i = 0;
        for (String key : map.keySet()) {
            i++;
            System.out.println("Address Book #" + i + ": " + key);
            List<Contacts> sortedList = map.get(key).stream().sorted(Comparator.comparing(Contacts::getCity)).toList();
            for (Contacts info : sortedList) {
                System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
            }
            System.out.println();
        }
    }

    public void sortByState() {
        int i = 0;
        for (String key : map.keySet()) {
            i++;
            System.out.println("Address Book #" + i + ": " + key);
            List<Contacts> sortedList = map.get(key).stream().sorted(Comparator.comparing(Contacts::getState)).toList();
            for (Contacts info : sortedList) {
                System.out.println(info.getFirstName() + info.getLastName() + "  " + info.getAddress() + "  " + info.getCity() + "  " + info.getState() + "  " + info.getZip() + "  " + info.getPhoneNo() + "  " + info.getEmail());
            }
            System.out.println();
        }
    }

}
