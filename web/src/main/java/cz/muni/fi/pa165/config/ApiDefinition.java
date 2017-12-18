package cz.muni.fi.pa165.config;

public class ApiDefinition {
    public static final String REST = "/rest";

    public static final class Car {
        public static final String BASE = "/car";
        public static final String ID = "/{id}";
        public static final String PATH_ID = "id";
    }

    public static final class Reservation {
        public static final String BASE = "/reservation";
        public static final String ID = "/{id}";
        public static final String PATH_ID = "id";
    }

    public static final class Branch {
        public static final String BASE = "/branch";
        public static final String ID = "/{id}";
        public static final String PATH_ID = "id";
        public static final String ASSIGN_CAR = "/{id}/assignCar";
        public static final String ASSIGN_USER = "/{id}/assignUser";
        public static final String FIND_AVAILABLE = "/{id}/findAvailable";
    }

    public static final class User {
        public static final String BASE = "/user";
    }
}
