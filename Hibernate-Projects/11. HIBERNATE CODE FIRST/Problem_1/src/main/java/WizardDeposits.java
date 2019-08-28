import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposits {
    private int id;
    private String firstName;
    private String lastName;
    private String notes;
    private int age;
    private String magicWandCreator;
    private int magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private float depositAmount;
    private float depositInterest;
    private float depositCharge;
    private LocalDateTime depositExpirationDate;
    private boolean isDepositExpired;

    public WizardDeposits() {
    }

    public WizardDeposits(String firstName, String lastName, String notes, int age, String magicWandCreator, @Range(min = 1, max = 32768) int magicWandSize, String depositGroup, LocalDateTime depositStartDate, float depositAmount, float depositInterest, float depositCharge, LocalDateTime depositExpirationDate, boolean isDepositExpired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.notes = notes;
        this.setAge(age);
        this.magicWandCreator = magicWandCreator;
        this.setMagicWandSize(magicWandSize);
        this.depositGroup = depositGroup;
        this.depositStartDate = depositStartDate;
        this.depositAmount = depositAmount;
        this.depositInterest = depositInterest;
        this.depositCharge = depositCharge;
        this.depositExpirationDate = depositExpirationDate;
        this.isDepositExpired = isDepositExpired;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    @NotNull
    @Column(name = "last_name", length = 60)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    @Column(name = "notes", length = 1000)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @NotNull
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException(
                "Value of age" + age + " must be a positive number!");
        }
    }

    @Column(name = "magic_wand_creator", length = 100)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magic_wand_creator) {
        this.magicWandCreator = magic_wand_creator;
    }

    @Column(name = "magic_wand_size")
    public int getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(int magicWandSize) {
        if (magicWandSize >= 1 && magicWandSize <= 37268) {
            this.magicWandSize = magicWandSize;
        } else {
            throw new IllegalArgumentException(
                "Value of magic wand size" + magicWandSize + " must be in range 1 < value < 37268");
        }
    }

    @Column(name = "deposit_group", length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String deposit_group) {
        this.depositGroup = deposit_group;
    }

    @Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime deposit_start_date) {
        this.depositStartDate = deposit_start_date;
    }

    public float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(float deposit_amount) {
        this.depositAmount = deposit_amount;
    }

    @Column(name = "deposit_interest")
    public float getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(float deposit_interest) {
        this.depositInterest = deposit_interest;
    }

    @Column(name = "deposit_charge")
    public float getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(float deposit_charge) {
        this.depositCharge = deposit_charge;
    }

    @Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime deposit_expiration_date) {
        this.depositExpirationDate = deposit_expiration_date;
    }

    @Column(name = "is_deposit_expired")
    public boolean isIsDepositExpired() {
        return isDepositExpired;
    }

    public void setIsDepositExpired(boolean is_deposit_exipred) {
        this.isDepositExpired = is_deposit_exipred;
    }
}
