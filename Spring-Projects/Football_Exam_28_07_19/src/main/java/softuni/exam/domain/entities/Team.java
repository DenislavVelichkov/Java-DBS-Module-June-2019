package softuni.exam.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
  private String name;
  private Picture picture;
  
  public Team() {
  }
  
  @Column(length = 20)
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  @ManyToOne(targetEntity = Picture.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "picture_id", referencedColumnName = "id")
  public Picture getPicture() {
    return picture;
  }
  
  public void setPicture(Picture picture) {
    this.picture = picture;
  }
}
