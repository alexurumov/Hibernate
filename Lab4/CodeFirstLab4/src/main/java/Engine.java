import entities.Diagnose;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.util.Scanner;

public class Engine implements Runnable {
    private static final String INTRO_TEXT = "Please, choose one of the following commands: \n" +
            "Add \n" +
            "   - Patient, {first name, last name, address(without empty spaces), email, date of birth(YYYY-MM-DD), picture(url), isInsured(true/false)}\n" +
            "   - Visitation, {date, comments}\n" +
            "   - Diagnose, {name, comments}\n" +
            "   - Medicament, {name}\n" +
            "Update \n" +
            "   - Patient, {patientId} {patient properties, as described above}\n" +
            "   - Visitation, {visitationId} {visitation properties, as described above}\n" +
            "   - Diagnose, {diagnoseId} {diagnose properties, as described above}\n" +
            "   - Medicament, {medicamentId} {medicament properties, as described above}\n" +
            "Receive, {Patient, Visitation, Diagnose, Medicament} {id/All}\n" +
            "Remove, {Patient, Visitation, Diagnose, Medicament} {id}\n" +
            "End - to exit the program!\n";

    private Scanner in = new Scanner(System.in);
    private EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        this.receiveCommand();
    }

    private void receiveCommand() {
        System.out.println(INTRO_TEXT);

        String[] inputArgs = in.nextLine().split(", ");

        while (!inputArgs[0].equals("End")) {

            switch (inputArgs[0]) {
                case "Add":
                    this.add(inputArgs);
                    break;
                case "Update":
                    this.update(inputArgs);
                    break;
                case "Receive":
                this.receive(inputArgs);
                    break;
                case "Remove":
                this.remove(inputArgs);
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }

            System.out.println(INTRO_TEXT);

            inputArgs = in.nextLine().split(", ");
        }

    }

    private void remove(String[] inputArgs) {
        switch (inputArgs[1]) {
            case "Patient":
                this.removePatient(inputArgs);
                break;
            case "Visitation":
                this.removeVisitation(inputArgs);
                break;
            case "Diagnose":
                this.removeDiagnose(inputArgs);
                break;
            case "Medicament":
                this.removeMedicament(inputArgs);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    private void removeMedicament(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        int medicamentId = Integer.parseInt(inputArgs[2]);

        Medicament medicament = this.entityManager.createQuery("FROM Medicament WHERE id = :medicamentId", Medicament.class)
                .setParameter("medicamentId", medicamentId)
                .getSingleResult();

        this.entityManager.remove(medicament);

        this.entityManager.getTransaction().commit();
    }

    private void removeDiagnose(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        int diagnoseId = Integer.parseInt(inputArgs[2]);

        Diagnose diagnose = this.entityManager.createQuery("FROM Diagnose WHERE id = :diagnoseId", Diagnose.class)
                .setParameter("diagnoseId", diagnoseId)
                .getSingleResult();

        this.entityManager.remove(diagnose);

        this.entityManager.getTransaction().commit();
    }

    private void removeVisitation(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        int visitationId = Integer.parseInt(inputArgs[2]);

        Visitation visitation = this.entityManager.createQuery("FROM Visitation WHERE id = :visitationId", Visitation.class)
                .setParameter("visitationId", visitationId)
                .getSingleResult();

        this.entityManager.remove(visitation);

        this.entityManager.getTransaction().commit();
    }

    private void removePatient(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        int patientId = Integer.parseInt(inputArgs[2]);

        Patient patient = this.entityManager.createQuery("FROM Patient WHERE id = :patientId", Patient.class)
                .setParameter("patientId", patientId)
                .getSingleResult();

        this.entityManager.remove(patient);

        this.entityManager.getTransaction().commit();

    }

    private void receive(String[] inputArgs) {
        switch (inputArgs[1]) {
            case "Patient":
                this.receivePatient(inputArgs);
                break;
            case "Visitation":
                this.receiveVisitation(inputArgs);
                break;
            case "Diagnose":
                this.receiveDiagnose(inputArgs);
                break;
            case "Medicament":
                this.receiveMedicament(inputArgs);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    private void receiveMedicament(String[] inputArgs) {
        if (inputArgs[2].equals("All")) {
            this.entityManager.getTransaction().begin();

            this.entityManager.createQuery("FROM Medicament", Medicament.class)
                    .getResultList()
                    .forEach(medicament -> System.out.println(medicament.toString()));

            this.entityManager.getTransaction().commit();
        } else {
            try {
                this.entityManager.getTransaction().begin();

                int medicamentId = Integer.parseInt(inputArgs[2]);
                Medicament medicament = this.entityManager.createQuery("FROM Medicament WHERE id = :medicamentId", Medicament.class)
                        .setParameter("medicamentId", medicamentId)
                        .getSingleResult();

                System.out.println(medicament.toString());

                this.entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                this.entityManager.getTransaction().rollback();
            }

        }
    }

    private void receiveDiagnose(String[] inputArgs) {
        if (inputArgs[2].equals("All")) {
            this.entityManager.getTransaction().begin();

            this.entityManager.createQuery("FROM Diagnose", Diagnose.class)
                    .getResultList()
                    .forEach(diagnose -> System.out.println(diagnose.toString()));

            this.entityManager.getTransaction().commit();
        } else {
            try {
                this.entityManager.getTransaction().begin();

                int diagnoseId = Integer.parseInt(inputArgs[2]);
                Diagnose diagnose = this.entityManager.createQuery("FROM Diagnose WHERE id = :diagnoseId", Diagnose.class)
                        .setParameter("diagnoseId", diagnoseId)
                        .getSingleResult();

                System.out.println(diagnose.toString());

                this.entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                this.entityManager.getTransaction().rollback();
            }

        }
    }

    private void receiveVisitation(String[] inputArgs) {
        if (inputArgs[2].equals("All")) {
            this.entityManager.getTransaction().begin();

            this.entityManager.createQuery("FROM Visitation", Visitation.class)
                    .getResultList()
                    .forEach(visitation -> System.out.println(visitation.toString()));

            this.entityManager.getTransaction().commit();
        } else {
            try {
                this.entityManager.getTransaction().begin();

                int visitationId = Integer.parseInt(inputArgs[2]);
                Visitation visitation = this.entityManager.createQuery("FROM Visitation WHERE id = :visitationId", Visitation.class)
                        .setParameter("visitationId", visitationId)
                        .getSingleResult();

                System.out.println(visitation.toString());

                this.entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                this.entityManager.getTransaction().rollback();
            }

        }
    }

    private void receivePatient(String[] inputArgs) {

        if (inputArgs[2].equals("All")) {
            this.entityManager.getTransaction().begin();

            this.entityManager.createQuery("FROM Patient", Patient.class)
                    .getResultList()
                    .forEach(patient -> System.out.println(patient.toString()));

            this.entityManager.getTransaction().commit();
        } else {
            try {
                this.entityManager.getTransaction().begin();

                int patientId = Integer.parseInt(inputArgs[2]);
                Patient patient = this.entityManager.createQuery("FROM Patient WHERE id = :patientId", Patient.class)
                        .setParameter("patientId", patientId)
                        .getSingleResult();

                System.out.println(patient.toString());

                this.entityManager.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("Invalid input!");
                this.entityManager.getTransaction().rollback();
            }

        }


    }

    private void update(String[] inputArgs) {
        switch (inputArgs[1]) {
            case "Patient":
                this.updatePatient(inputArgs);
                break;
            case "Visitation":
                this.updateVisitation(inputArgs);
                break;
            case "Diagnose":
                this.updateDiagnose(inputArgs);
                break;
            case "Medicament":
                this.updateMedicament(inputArgs);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    private void updateMedicament(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        try {
            int medicamentId = Integer.parseInt(inputArgs[2]);
            Medicament medicament = this.entityManager.createQuery("FROM Medicament WHERE id = :medicamentId", Medicament.class)
                    .setParameter("medicamentId", medicamentId)
                    .getSingleResult();

            medicament.setName(inputArgs[3]);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            this.entityManager.getTransaction().rollback();
        }

    }

    private void updateDiagnose(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        try {
            int diagnoseId = Integer.parseInt(inputArgs[2]);
            Diagnose diagnose = this.entityManager.createQuery("FROM Diagnose WHERE id = :diagnoseId", Diagnose.class)
                    .setParameter("diagnoseId", diagnoseId)
                    .getSingleResult();

            diagnose.setName(inputArgs[3]);
            diagnose.setComments(inputArgs[4]);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            this.entityManager.getTransaction().rollback();
        }

    }

    private void updateVisitation(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        try {
            int visitationId = Integer.parseInt(inputArgs[2]);
            Visitation visitation = this.entityManager.createQuery("FROM Visitation WHERE id = :visitationId", Visitation.class)
                    .setParameter("visitationId", visitationId)
                    .getSingleResult();

            visitation.setDate(Date.valueOf(inputArgs[3]));
            visitation.setComments(inputArgs[4]);

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Invalid input!");
            this.entityManager.getTransaction().rollback();
        }

    }

    private void updatePatient(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        try {
            int patientId = Integer.parseInt(inputArgs[2]);
            Patient patient = this.entityManager.createQuery("FROM Patient WHERE id = :patientId", Patient.class)
                    .setParameter("patientId", patientId)
                    .getSingleResult();

            patient.setFirstName(inputArgs[3]);
            patient.setLastName(inputArgs[4]);
            patient.setAddress(inputArgs[5]);
            patient.setEmail(inputArgs[6]);
            patient.setDateOfBirth(Date.valueOf(inputArgs[7]));
            patient.setPicture(inputArgs[8]);
            patient.setInsured(inputArgs[9].equals("true"));

            this.entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Invalid input!");
            this.entityManager.getTransaction().rollback();
        }

    }

    private void add(String[] inputArgs) {
        switch (inputArgs[1]) {
            case "Patient":
                this.addPatient(inputArgs);
                break;
            case "Visitation":
                this.addVisitation(inputArgs);
                break;
            case "Diagnose":
                this.addDiagnose(inputArgs);
                break;
            case "Medicament":
                this.addMedicament(inputArgs);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    private void addMedicament(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        Medicament medicament = new Medicament();
        medicament.setName(inputArgs[2]);

        this.entityManager.persist(medicament);

        this.entityManager.getTransaction().commit();
    }

    private void addDiagnose(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        Diagnose diagnose = new Diagnose();
        diagnose.setName(inputArgs[2]);
        diagnose.setComments(inputArgs[3]);

        this.entityManager.persist(diagnose);

        this.entityManager.getTransaction().commit();
    }

    private void addVisitation(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        Visitation visitation = new Visitation();
        visitation.setDate(Date.valueOf(inputArgs[2]));
        visitation.setComments(inputArgs[3]);

        this.entityManager.persist(visitation);

        this.entityManager.getTransaction().commit();
    }

    private void addPatient(String[] inputArgs) {
        this.entityManager.getTransaction().begin();

        Patient patient = new Patient();
        patient.setFirstName(inputArgs[2]);
        patient.setLastName(inputArgs[3]);
        patient.setAddress(inputArgs[4]);
        patient.setEmail(inputArgs[5]);
        patient.setDateOfBirth(Date.valueOf(inputArgs[6]));
        patient.setPicture(inputArgs[7]);
        patient.setInsured(inputArgs[8].equals("true"));

        this.entityManager.persist(patient);

        this.entityManager.getTransaction().commit();
    }

}
