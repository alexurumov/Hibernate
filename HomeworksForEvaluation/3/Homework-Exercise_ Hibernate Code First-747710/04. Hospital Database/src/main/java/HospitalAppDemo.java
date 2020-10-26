import entities.Diagnosis;
import entities.Medicament;
import entities.Patient;
import entities.Visitation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.HashSet;

public class HospitalAppDemo {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("code_first_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Patient patient = new Patient();
//        patient.setFirstName("First");
//        patient.setLastName("Last");
//        patient.setDateOfBirth(LocalDate.now());
//        patient.setMedicalInsured(true);
//        patient.setVisitations(new HashSet<>());
//        patient.setDiagnoses(new HashSet<>());
//        patient.setMedicaments(new HashSet<>());
//
//        Diagnosis diagnosis = new Diagnosis();
//        diagnosis.setName("ICD 10");
//        diagnosis.setComments("Commends");
//        diagnosis.setPatients(new HashSet<>());
//
//        Medicament medicament = new Medicament();
//        medicament.setName("Paracetamol");
//        medicament.setPatients(new HashSet<>());
//
//        Visitation visitation = new Visitation();
//        visitation.setComments("Comments");
//        visitation.setDate(LocalDate.now());
//
//        patient.getDiagnoses().add(diagnosis);
//        patient.getMedicaments().add(medicament);
//        patient.getVisitations().add(visitation);
//
//        visitation.setPatient(patient);
//        medicament.getPatients().add(patient);
//
//        entityManager.getTransaction()
//                .begin();
//        entityManager.persist(patient);
//        entityManager.getTransaction()
//                .commit();
    }
}
