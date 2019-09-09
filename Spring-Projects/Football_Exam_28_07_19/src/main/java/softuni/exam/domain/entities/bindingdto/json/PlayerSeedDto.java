package softuni.exam.domain.entities.bindingdto.json;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import softuni.exam.domain.entities.Position;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PlayerSeedDto {
  
  @Expose
  private String firstName;
  
  @Expose
  private String lastName;
  
  @Expose
  private Integer number;
  
  @Expose
  private BigDecimal salary;
  
  @Expose
  private Position position;
  
  @Expose
  private PictureOfPlayerSeedDto picture;
  
  @Expose
  private TeamOfPlayerSeedDto team;
  
  public PlayerSeedDto() {
  }
  
  @NotNull
  public String getFirstName() {
    return firstName;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  @NotNull
  @Length(min = 3, max = 15)
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  @Range(min = 1, max = 99)
  public Integer getNumber() {
    return number;
  }
  
  public void setNumber(Integer number) {
    this.number = number;
  }
  
  @Min(value = 0)
  public BigDecimal getSalary() {
    return salary;
  }
  
  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }
  
  @Enumerated(value = EnumType.STRING)
  @NotNull
  public Position getPosition() {
    return position;
  }
  
  public void setPosition(Position position) {
    this.position = position;
  }
  
  @NotNull
  public PictureOfPlayerSeedDto getPicture() {
    return picture;
  }
  
  public void setPicture(PictureOfPlayerSeedDto picture) {
    this.picture = picture;
  }
  
  @NotNull
  public TeamOfPlayerSeedDto getTeam() {
    return team;
  }
  
  public void setTeam(TeamOfPlayerSeedDto team) {
    this.team = team;
  }
}
