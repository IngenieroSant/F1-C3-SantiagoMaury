package com.example.apiservices;

import java.util.List;

public class TeamDetail {
    private Area area;
    private int id;
    private String name;
    private String shortName;
    private String tla;
    private String crest;
    private String address;
    private String website;
    private int founded;
    private String clubColors;
    private String venue;
    private List<Competition> runningCompetitions;
    private Coach coach;
    private List<Player> squad;

    public static class Area {
        private int id;
        private String name;
        private String code;
        private String flag;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCode() { return code; }
        public String getFlag() { return flag; }
    }

    public static class Competition {
        int id;
        String name;
        String code;
        String type;
        String emblem;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCode() { return code; }
        public String getType() { return type; }
        public String getEmblem() { return emblem; }
    }

    public static class Coach {
        private int id;
        private String firstName;
        private String lastName;
        private String name;
        private String dateOfBirth;
        private String nationality;
        private Contract contract;

        public static class Contract {
            private String start;
            private String until;

            public String getStart() { return start; }
            public String getUntil() { return until; }
        }

        public int getId() { return id; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getName() { return name; }
        public String getDateOfBirth() { return dateOfBirth; }
        public String getNationality() { return nationality; }
        public Contract getContract() { return contract; }
    }

    public static class Player {
        int id;
        String name;
        String position;
        String dateOfBirth;
        String nationality;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getPosition() { return position; }
        public String getDateOfBirth() { return dateOfBirth; }
        public String getNationality() { return nationality; }
    }

    public Area getArea() { return area; }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public String getTla() { return tla; }
    public String getCrest() { return crest; }
    public String getAddress() { return address; }
    public String getWebsite() { return website; }
    public int getFounded() { return founded; }
    public String getClubColors() { return clubColors; }
    public String getVenue() { return venue; }
    public List<Competition> getRunningCompetitions() { return runningCompetitions; }
    public Coach getCoach() { return coach; }
    public List<Player> getSquad() { return squad; }
} 